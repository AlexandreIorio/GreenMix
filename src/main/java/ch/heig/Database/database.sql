CREATE SCHEMA IF NOT EXISTS greenmix;

SET search_path TO greenmix;

CREATE TABLE customers
(
    id         SERIAL,
    username   varchar(255) NOT NULL UNIQUE,
    wallet     decimal  NOT NULL DEFAULT 0,
    email      varchar(255),
    hashcode   varchar(255) NOT NULL,
    created_at timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE plants
(
    id         SERIAL,
    owner      integer   NOT NULL,
    type       integer   NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES customers (id)
);

INSERT INTO customers (username, email, hashcode, wallet) VALUES ('Jerry', 'jerry@greenmix.ch', '5f4dcc3b5aa765d61d8327deb882cf99', 78);
INSERT INTO customers (username, email, hashcode, wallet) VALUES ('Tom', 'tom@greenmix.ch', '5f4dcc3b5aa765d61d8327deb882cf99', 5);
