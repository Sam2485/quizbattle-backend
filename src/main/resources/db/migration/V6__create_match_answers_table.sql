CREATE TABLE match_answers (

                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                               match_id UUID NOT NULL,

                               user_id UUID NOT NULL,

                               question_id UUID NOT NULL,

                               submitted_answer VARCHAR(500) NOT NULL,

                               is_correct BOOLEAN NOT NULL,

                               time_taken_ms BIGINT,

                               answered_at TIMESTAMP WITH TIME ZONE NOT NULL,


                               CONSTRAINT fk_match_answers_match
                                   FOREIGN KEY (match_id)
                                       REFERENCES matches(id)
                                       ON DELETE CASCADE,


                               CONSTRAINT fk_match_answers_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE,


                               CONSTRAINT fk_match_answers_question
                                   FOREIGN KEY (question_id)
                                       REFERENCES questions(id)
                                       ON DELETE CASCADE

);



CREATE INDEX idx_match_answers_match
    ON match_answers(match_id);


CREATE INDEX idx_match_answers_user
    ON match_answers(user_id);


CREATE INDEX idx_match_answers_question
    ON match_answers(question_id);