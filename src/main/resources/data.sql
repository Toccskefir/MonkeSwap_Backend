INSERT INTO users (email,
                   username,
                   password,
                   trades_completed,
                   role,
                   date_of_registration)
VALUES ('admin@email.com',
        'admin',
        'admin123',
        0,
        'ADMIN',
        CAST(CURRENT_DATE AS VARCHAR(255)));