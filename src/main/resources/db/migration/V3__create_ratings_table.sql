CREATE TABLE ratings (

                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                         user_id UUID NOT NULL,

                         subject_id UUID NOT NULL,

                         rating INTEGER NOT NULL,

                         matches_played INTEGER NOT NULL,

                         wins INTEGER NOT NULL,

                         losses INTEGER NOT NULL,

                         created_at TIMESTAMP WITH TIME ZONE NOT NULL,


                         CONSTRAINT fk_ratings_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT fk_ratings_subject
                             FOREIGN KEY (subject_id)
                                 REFERENCES subjects(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT uk_user_subject
                             UNIQUE (user_id, subject_id)

);


CREATE INDEX idx_ratings_user
    ON ratings(user_id);


CREATE INDEX idx_ratings_subject
    ON ratings(subject_id);


CREATE INDEX idx_ratings_rating
    ON ratings(rating DESC);