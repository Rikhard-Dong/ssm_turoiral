DROP TABLE IF EXISTS t_info;

CREATE TABLE t_info (
  `id`          INT                   AUTO_INCREMENT,
  `username`    VARCHAR(255) NOT NULL,
  `content`     VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  CHARSET utf8;