package com.curtisnewbie.common.domain;

import com.curtisnewbie.common.async.EventPool;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

/**
 * Result of a domain operation
 *
 * @author yongj.zhuang
 */
@Data
@Builder
public class OpResult<T> {

    private final T value;
    private final List<?> events;

    /**
     * Publish events returned by the domain operation sequentially
     */
    public void publish(ApplicationEventPublisher publisher) {
        if (publisher != null && events != null)
            events.forEach(publisher::publishEvent);
    }

    /**
     * Publish events returned by the domain operation to the event pool asynchronously
     *
     * @see EventPool
     */
    public void publishAsync(EventPool eventPool) {
        if (eventPool != null && events != null)
            events.forEach(eventPool::publish);
    }

    /**
     * Check if there is any event
     */
    public boolean hasEvents() {
        return events != null && !events.isEmpty();
    }

}
