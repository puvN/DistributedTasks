-- Create the sequence for generating IDs
CREATE SEQUENCE task_id_seq
    START WITH 100200
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE CACHE 1;

-- Create the task table
CREATE TABLE task
(
    id       BIGINT PRIMARY KEY DEFAULT nextval('task_id_seq'),
    name     VARCHAR(255),
    duration BIGINT NOT NULL,
    status   VARCHAR(50),
    created  TIMESTAMP,
    updated  TIMESTAMP
);