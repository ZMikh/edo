CREATE TABLE organization (
    id              BIGINT   PRIMARY KEY,
    name            VARCHAR,
    actual_address  VARCHAR,
    legal_address   VARCHAR,
    ceo             VARCHAR
);

CREATE SEQUENCE IF NOT EXISTS organization_sequence;










