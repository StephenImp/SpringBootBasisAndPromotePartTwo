DROP TABLE
  IF EXISTS t_order_0;

CREATE TABLE t_order_0 (
                         order_id BIGINT (20) NOT NULL,
                         user_id BIGINT (20) NOT NULL,
                         PRIMARY KEY (order_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;


IF EXISTS t_order_1;

CREATE TABLE t_order_1 (
                         order_id BIGINT (20) NOT NULL,
                         user_id BIGINT (20) NOT NULL,
                         PRIMARY KEY (order_id)
) ENGINE = INNODB DEFAULT CHARSET = utf8 COLLATE = utf8_bin;

