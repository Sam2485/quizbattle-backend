CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (

                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       firebase_uid VARCHAR(128) NOT NULL UNIQUE,

                       email VARCHAR(255) NOT NULL,

                       display_name VARCHAR(255),

                       created_at TIMESTAMP WITH TIME ZONE NOT NULL,

                       last_active TIMESTAMP WITH TIME ZONE

);


CREATE INDEX idx_users_firebase_uid
    ON users(firebase_uid);


CREATE INDEX idx_users_email
    ON users(email);