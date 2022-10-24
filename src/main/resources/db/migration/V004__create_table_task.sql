CREATE TABLE task (
    id                  BIGINT PRIMARY KEY,
    subject             VARCHAR,
    terms               TIMESTAMP,
    is_controlled       BOOLEAN DEFAULT FALSE NOT NULL,
    is_executed         BOOLEAN DEFAULT FALSE NOT NULL,
    text                VARCHAR,
    task_state          VARCHAR,
    employee_author_id  BIGINT,

    CONSTRAINT fk_employee FOREIGN KEY (employee_author_id) REFERENCES employee (id)
);

CREATE SEQUENCE IF NOT EXISTS task_sequence;

CREATE TABLE employee_executors_task (
    task_id BIGINT,
    employee_id BIGINT,

    CONSTRAINT fk_employee_executor FOREIGN KEY (employee_id) REFERENCES employee(id),
    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES task(id)
);