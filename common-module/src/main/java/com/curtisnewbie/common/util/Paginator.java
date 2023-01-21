package com.curtisnewbie.common.util;

import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.util.*;

import java.util.*;
import java.util.function.*;

/**
 * <p>
 * Paginator that takes care of the offset and limit
 * </p>
 * <p>
 * For example:
 * </p>
 * <pre>
 * {@code
 * Paginator<Record> paginator = new Paginator<Record>()
 *      .pageSize(500)
 *      .nextPageSupplier(p -> fetchNextPage(p.getOffset(), p.getLimit()));
 *      .onCurrentPageComplete(listSize -> logCurrentPageSize(listSize));
 *
 * paginator.loopEachTilEnd(record -> {
 *      processRecord(record);
 * });
 * }
 * </pre>
 *
 * @author yongj.zhuang
 */
public final class Paginator<T> {

    /** Default size of the page */
    public static final int DEFAULT_PAGE_SIZE = 500;

    /** Pagination Parameters, if not specified, the default one is used */
    private PagingParam pagingParam = PagingParam.builder()
            .limit(DEFAULT_PAGE_SIZE)
            .build();
    /** data list */
    private List<T> list;
    /** number of records iterated */
    private long count = 0;
    /** supplier of next page */
    private Function<PagingParam, List<T>> nextPageSupplier;
    /** whether current page is the first page */
    private boolean isFirstPage = true;
    /** consumer of page size after {@link #_doForEach(Consumer)} */
    private Consumer<Integer> onCurrentPageComplete = null;
    /** Timer (nullable) */
    private LDTTimer timer = null;

    public Paginator() {
    }

    public Paginator(Function<PagingParam, List<T>> nextPageSupplier) {
        this.nextPageSupplier = nextPageSupplier;
    }

    /** set whether the looping is timed */
    public Paginator<T> isTimed(boolean isTimed) {
        if (isTimed)
            timer = new LDTTimer();
        return this;
    }

    /** consumer of page size that is invoked after {@link #_doForEach(Consumer)} */
    public Paginator<T> onCurrentPageComplete(Consumer<Integer> onCurrentPageComplete) {
        this.onCurrentPageComplete = onCurrentPageComplete;
        return this;
    }

    /** set page size */
    public Paginator<T> pageSize(long limit) {
        this.pagingParam = PagingParam.builder()
                .limit(limit)
                .offset(0L)
                .build();
        return this;
    }

    /** set supplier for next page */
    public Paginator<T> nextPageSupplier(Function<PagingParam, List<T>> nextPageSupplier) {
        this.nextPageSupplier = nextPageSupplier;
        return this;
    }

    /** whether current page has data */
    public boolean hasContent() {
        if (isFirstPage) { // load the first page
            _nextPage();
            isFirstPage = false;
        }

        return list != null && !list.isEmpty();
    }

    /** Do something for each item in current page */
    public void _doForEach(final Consumer<T> doForEach) {
        Assert.notNull(doForEach, "doForEach == null");

        if (hasContent()) {
            list.forEach(doForEach::accept);
            doOnCurrentPageComplete();
        }
    }

    /**
     * Do something for current page
     */
    public void _doForPage(final Consumer<List<T>> doForPage) {
        Assert.notNull(doForPage, "doForPage == null");
        if (hasContent()) {
            doForPage.accept(list);
            doOnCurrentPageComplete();
        }
    }

    /** Load the next page */
    public void _nextPage() {
        Assert.notNull(nextPageSupplier, "nextPageSupplier == null");

        // next page
        list = nextPageSupplier.apply(pagingParam);

        // if list is not empty, we update offset and count
        if (list != null && !list.isEmpty()) {
            count += list.size();
            pagingParam.nextPage();
        }
    }

    /** total number of records being iterated by this paginator */
    public long getCount() {
        return count;
    }

    /**
     * <p>
     * Keeps looping until the last page
     * </p>
     * <p>
     * This is equivalent to following code
     * </p>
     * <pre>
     * {@code
     * while (hasContent()) {
     *      doForPage(doForPage);
     *      nextPage();
     * }
     * }
     * </pre>
     */
    public void loopPageTilEnd(final Consumer<List<T>> doForPage) {
        tryStartTimer();
        while (hasContent()) {
            _doForPage(doForPage);
            _nextPage();
        }
        tryStopTimer();
    }

    /**
     * <p>
     * Keeps looping until the last page
     * </p>
     * <p>
     * This is equivalent to following code
     * </p>
     * <pre>
     * {@code
     * while (hasContent()) {
     *      doForEach(consumer);
     *      nextPage();
     * }
     * }
     * </pre>
     */
    public void loopEachTilEnd(final Consumer<T> doForEach) {
        tryStartTimer();
        while (hasContent()) {
            _doForEach(doForEach);
            _nextPage();
        }
        tryStopTimer();
    }

    /**
     * Get timer
     *
     * @return timer or null if it's not "timed"
     */
    @Nullable
    public LDTTimer getTimer() {
        return timer;
    }

    private void doOnCurrentPageComplete() {
        if (onCurrentPageComplete != null)
            onCurrentPageComplete.accept(list.size());
    }

    private void tryStopTimer() {
        if (timer != null)
            timer.stop();
    }

    private void tryStartTimer() {
        if (timer != null)
            timer.start();
    }

    @Data
    @Builder
    public static class PagingParam {
        /** offset */
        private long offset = 0;
        /** page size */
        private long limit = 0;

        /** Update offset for next page */
        public void nextPage() {
            this.offset += limit;
        }

        public String toLimitStr() {
            return String.format("limit %d, %d", getOffset(), getLimit());
        }
    }
}
