CREATE TABLE jobapplication(

    id BIGSERIAL PRIMARY KEY NOT NULL,
    jobposting_id BIGINT NOT NULL,
    candidate_id BIGINT NOT NULL,
    candidate_name VARCHAR(100) NOT NULL,
    hardskills varchar(255) ARRAY,
    softskills JSON,
    curriculum_path VARCHAR(255),
    create_at DATE DEFAULT CURRENT_DATE

);