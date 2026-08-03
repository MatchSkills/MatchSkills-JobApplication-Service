CREATE TABLE jobapplication(

    id BIGSERIAL PRIMARY KEY NOT NULL,
    jobposting_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    hardskills varchar(255) ARRAY,
    softskills JSON,
    create_at DATE DEFAULT CURRENT_DATE

);