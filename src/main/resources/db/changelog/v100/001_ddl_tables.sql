--liquibase formatted sql
--changeset rejchev:ddl_tables

CREATE TABLE IF NOT EXISTS enrolls_sources
(
    id   VARCHAR(255) NOT NULL PRIMARY KEY,
    path VARCHAR(255) NOT NULL,
    year INT      NOT NULL,

    UNIQUE (path)
);

CREATE TABLE IF NOT EXISTS enrolls
(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    eid VARCHAR(255) NOT NULL,
    edu_group VARCHAR(255) NOT NULL,
    order_id VARCHAR(255) NOT NULL,
    total_points INT NOT NULL,
    source_id VARCHAR(255) NOT NULL,

    UNIQUE (eid, source_id),

    FOREIGN KEY (source_id) REFERENCES enrolls_sources (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS enrolls_metas
(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    admission VARCHAR(255) NOT NULL,
    branch VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    competition_group VARCHAR(512) NOT NULL,
    edu_format VARCHAR(255) NOT NULL,
    enrolled INT NOT NULL,
    last_modified VARCHAR(255) NOT NULL,
    level VARCHAR(255) NOT NULL,
    specialization VARCHAR(255) NOT NULL,
    total VARCHAR(255) NOT NULL,
    source_id VARCHAR(255) NOT NULL,

    UNIQUE (source_id),

    FOREIGN KEY (source_id) REFERENCES enrolls_sources (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

