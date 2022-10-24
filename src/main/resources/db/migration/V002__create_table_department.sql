CREATE TABLE department (
    id              BIGINT PRIMARY KEY,
    organization_id BIGINT,
    name            VARCHAR,
    contact_info    VARCHAR,
    manager         VARCHAR,

    CONSTRAINT fk_organization FOREIGN KEY (organization_id) REFERENCES organization (id)
);

CREATE SEQUENCE IF NOT EXISTS department_sequence;