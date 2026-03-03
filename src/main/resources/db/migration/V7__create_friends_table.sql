CREATE TABLE friends (

                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                         sender_id UUID NOT NULL,

                         receiver_id UUID NOT NULL,

                         status VARCHAR(50) NOT NULL,

                         created_at TIMESTAMP WITH TIME ZONE NOT NULL,


                         CONSTRAINT fk_friend_sender
                             FOREIGN KEY (sender_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT fk_friend_receiver
                             FOREIGN KEY (receiver_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,


                         CONSTRAINT uk_friend_pair
                             UNIQUE (sender_id, receiver_id)

);



CREATE INDEX idx_friend_sender
    ON friends(sender_id);


CREATE INDEX idx_friend_receiver
    ON friends(receiver_id);


CREATE INDEX idx_friend_status
    ON friends(status);