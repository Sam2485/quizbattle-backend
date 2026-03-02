CREATE TABLE subjects (

                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                          code VARCHAR(100) NOT NULL UNIQUE,

                          name VARCHAR(255) NOT NULL,

                          created_at TIMESTAMP WITH TIME ZONE NOT NULL

);


CREATE INDEX idx_subjects_code
    ON subjects(code);



INSERT INTO subjects (code, name, created_at)
VALUES

    ('MATHEMATICS', 'Mathematics', NOW()),

    ('ENGLISH', 'English', NOW()),

    ('SCIENCE', 'Science', NOW()),

    ('LOGICAL_REASONING', 'Logical Reasoning', NOW());