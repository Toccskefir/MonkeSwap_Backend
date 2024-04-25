--BASIC ADMIN USER
INSERT INTO users (email,
                   username,
                   password,
                   trades_completed,
                   role,
                   date_of_registration)
VALUES ('admin@email.com',
        'admin',
        '$2a$12$sgTc/OqACuFpcoOTFZT9POsNJWphzoVaYjGbZvKpyuCjOMTYiZ0TG',
        0,
        'ADMIN',
        CAST(CURRENT_DATE AS VARCHAR(255)));