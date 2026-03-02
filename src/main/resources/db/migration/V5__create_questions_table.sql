CREATE TABLE questions (

                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                           subject_id UUID NOT NULL,

                           question_title VARCHAR(1000) NOT NULL,

                           option1 VARCHAR(500) NOT NULL,

                           option2 VARCHAR(500) NOT NULL,

                           option3 VARCHAR(500) NOT NULL,

                           option4 VARCHAR(500) NOT NULL,

                           right_answer VARCHAR(500) NOT NULL,

                           difficulty_level VARCHAR(50) NOT NULL,

                           answer_description VARCHAR(2000),

                           created_at TIMESTAMP WITH TIME ZONE NOT NULL,


                           CONSTRAINT fk_question_subject
                               FOREIGN KEY (subject_id)
                                   REFERENCES subjects(id)
                                   ON DELETE CASCADE

);



CREATE INDEX idx_question_subject
    ON questions(subject_id);



CREATE INDEX idx_question_difficulty
    ON questions(difficulty_level);



CREATE INDEX idx_question_subject_difficulty
    ON questions(subject_id, difficulty_level);