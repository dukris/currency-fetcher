CREATE SEQUENCE IF NOT EXISTS version_no_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 9999999
    NO CYCLE;

SELECT nextval('version_no_seq');
SELECT nextval('version_no_seq');