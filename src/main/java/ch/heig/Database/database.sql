CREATE SCHEMA IF NOT EXISTS greenmix;

SET search_path TO greenmix;

--Functions
CREATE OR REPLACE FUNCTION unix_seconds()
    RETURNS BIGINT LANGUAGE SQL AS $$
SELECT EXTRACT(EPOCH FROM NOW())::BIGINT;
$$;

--Tables

CREATE TABLE customers
(
    id         SERIAL,
    username   varchar(255) NOT NULL UNIQUE,
    wallet     decimal  NOT NULL DEFAULT 0,
    email      varchar(255),
    hashcode   varchar(255) NOT NULL,
    plant_space integer NOT NULL DEFAULT 0,
    created_at bigint NOT NULL DEFAULT unix_seconds(),
    PRIMARY KEY (id)
);

CREATE TABLE plants
(
    id         SERIAL,
    owner      integer   NOT NULL,
    type       varchar(255)   NOT NULL,
    duration_factor decimal NOT NULL,
    quantity_factor decimal NOT NULL,
    planting_time bigint NOT NULL,
    havrested_time bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES customers (id)
);

--Inserts

INSERT INTO customers (username, email, hashcode, wallet) VALUES ('Jerry', 'jerry@greenmix.ch', '5f4dcc3b5aa765d61d8327deb882cf99', 78);
INSERT INTO customers (username, email, hashcode, wallet) VALUES ('Tom', 'tom@greenmix.ch', '5f4dcc3b5aa765d61d8327deb882cf99', 5);
