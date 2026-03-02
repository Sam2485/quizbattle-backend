CREATE TABLE matches (

                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                         player1_id UUID NOT NULL,

                         player2_id UUID NOT NULL,

                         subject_id UUID NOT NULL,

                         winner_id UUID,

                         status VARCHAR(50) NOT NULL,

                         created_at TIMESTAMP WITH TIME ZONE NOT NULL,

                         started_at TIMESTAMP WITH TIME ZONE,

                         finished_at TIMESTAMP WITH TIME ZONE,


                         CONSTRAINT fk_match_player1
                             FOREIGN KEY (player1_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT fk_match_player2
                             FOREIGN KEY (player2_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT fk_match_subject
                             FOREIGN KEY (subject_id)
                                 REFERENCES subjects(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT fk_match_winner
                             FOREIGN KEY (winner_id)
                                 REFERENCES users(id)
                                 ON DELETE SET NULL

);



CREATE INDEX idx_match_player1
    ON matches(player1_id);


CREATE INDEX idx_match_player2
    ON matches(player2_id);


CREATE INDEX idx_match_subject
    ON matches(subject_id);


CREATE INDEX idx_match_status
    ON matches(status);