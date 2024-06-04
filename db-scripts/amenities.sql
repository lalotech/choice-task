CREATE TABLE `amenities`
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(100) NOT NULL,
    `active`      tinyint(1) NOT NULL DEFAULT 1,
    `created_at`  datetime     NOT NULL DEFAULT current_timestamp(),
    `last_updated` timestamp    NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx1_amenity` (`id`) USING BTREE,
    UNIQUE KEY `idx2_amenity` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `amenities` (name) VALUES ("pool");
INSERT INTO `amenities` (name) VALUES ("internet");
INSERT INTO `amenities` (name) VALUES ("wifi");
INSERT INTO `amenities` (name) VALUES ("parking");