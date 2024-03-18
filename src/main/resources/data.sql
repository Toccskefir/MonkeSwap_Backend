--USERS
INSERT INTO users (email,
                   username,
                   password,
                   trades_completed,
                   role,
                   date_of_registration)
VALUES ('admin@email.com',
        'admin',
        '$2a$12$sgTc/OqACuFpcoOTFZT9POsNJWphzoVaYjGbZvKpyuCjOMTYiZ0TG', --admin123
        0,
        'ADMIN',
        CAST(CURRENT_DATE AS VARCHAR(255)));

INSERT INTO users (email,
                   username,
                   password,
                   trades_completed,
                   role,
                   date_of_registration)
VALUES ('test@email.com',
        'testuser',
        '$2a$12$X6.GB1TT4BglKwQNMHof.eBVPHr5Lwlu5JXwTwC/vVHQITg3HDtkm', --test123
        0,
        'USER',
        CAST(CURRENT_DATE AS VARCHAR(255)));

--ITEMS
INSERT INTO items (title,
                   item_picture,
                   description,
                   views,
                   reports,
                   state,
                   category,
                   price_tier,
                   user_id)
VALUES ('Test Disabled',
        'testpicture.com',
        'Created for testing when disabled',
        0,
        '',
        'DISABLED',
        'OTHER',
        3,
        2);

INSERT INTO items (title,
                   item_picture,
                   description,
                   views,
                   reports,
                   state,
                   category,
                   price_tier,
                   user_id)
VALUES ('Test Enabled',
        'testpicture.com',
        'Created for testing when enabled',
        0,
        '',
        'ENABLED',
        'OTHER',
        1,
        2);

INSERT INTO items (title,
                   item_picture,
                   description,
                   views,
                   reports,
                   state,
                   category,
                   price_tier,
                   user_id)
VALUES ('Test Reported',
        'testpicture.com',
        'Created for testing when reported',
        0,
        '1;2;3;4;5',
        'ENABLED',
        'OTHER',
        5,
        1);

--NOTIFICATIONS
INSERT INTO notifications (message, type, user_id) VALUES ('Test Notification', 'WARNING', 2);
INSERT INTO notifications (message, type, user_id) VALUES ('Test Notification2', 'NOTIFICATION', 2);

--TRADE OFFERS
INSERT INTO trade_offers (offered_item_id, incoming_item_id, comment) VALUES (1, 2, 'Comment');
INSERT INTO trade_offers (offered_item_id, incoming_item_id, comment) VALUES (1, 3, 'Comment');
INSERT INTO trade_offers (offered_item_id, incoming_item_id, comment) VALUES (2, 1, 'Comment');