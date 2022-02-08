CREATE TABLE IF NOT EXISTS [] (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT "primary key",

    ...

    create_time DATETIME NOT NULL DEFAULT NOW() COMMENT 'when the record is created',
    create_by VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'who created this record',
    update_time DATETIME NOT NULL DEFAULT NOW() COMMENT 'when the record is updated',
    update_by VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'who updated this record',
    is_del TINYINT NOT NULL DEFAULT '0' COMMENT '0-normal, 1-deleted'
) ENGINE=InnoDB COMMENT '[]';
