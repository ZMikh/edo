CREATE TABLE employee (
    id              BIGINT PRIMARY KEY,
    last_name       VARCHAR,
    first_name      VARCHAR,
    patronymic_name VARCHAR,
    job             VARCHAR,
    department_id   BIGINT,

    CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department (id)
);

CREATE SEQUENCE IF NOT EXISTS employee_sequence;