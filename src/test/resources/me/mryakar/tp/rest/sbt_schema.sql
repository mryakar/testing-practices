CREATE SEQUENCE IF NOT EXISTS person_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE person
(
    id      BIGINT      NOT NULL,
    name    VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    age     INTEGER     NOT NULL,
    gender  SMALLINT    NOT NULL,
    nation  VARCHAR(50),
    CONSTRAINT pk_person PRIMARY KEY (id)
);