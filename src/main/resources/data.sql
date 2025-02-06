-- Insert defined roles (ADMIN & USER)
MERGE INTO role (status, date_created, role_type)
KEY (role_type)
VALUES (1, NOW(), 'ADMIN');

MERGE INTO role (status, date_created, role_type)
KEY (role_type)
VALUES (1, NOW(), 'USER');

-- Insert admin permission
MERGE INTO permission (status, date_created, role_id, name)
KEY (role_id, name)
VALUES (1, NOW(), (SELECT id FROM role WHERE role_type='ADMIN'), 'ROLE_D_INST');
-- Insert user permission
MERGE INTO permission (status, date_created, role_id, name)
KEY (role_id, name)
VALUES (1, NOW(), (SELECT id FROM role WHERE role_type='USER'), 'ROLE_CU_F_INST');

-- Insert admin user
MERGE INTO users (status, date_created, username, password, role_id)
KEY (username)
VALUES (1, NOW(), 'admin', '$2a$10$roHcU2mh.pCgpWKHWpA.AOQHMj/pQFxp.1BwPe70DNTG/BhbhA8uO', (SELECT id FROM role WHERE role_type= 'ADMIN'));
-- Insert admin roles
MERGE INTO user_role (status, date_created, role_id, user_id)
KEY (role_id, user_id)
VALUES (1, NOW(),
        (SELECT id FROM role WHERE role_type='ADMIN'),
        (SELECT id FROM users WHERE username='admin'));
MERGE INTO user_role (status, date_created, role_id, user_id)
KEY (role_id, user_id)
VALUES (1, NOW(),
        (SELECT id FROM role WHERE role_type='USER'),
        (SELECT id FROM users WHERE username='admin'));

-- Insert normal user
MERGE INTO users (status, date_created, username, password, role_id)
KEY (username)
VALUES (1, NOW(), 'user', '$2a$10$HFfYpZu9Mjw5MiHxvd0oVeTUwEdqL1eveLDEB8cp5LZf/kOJ6l8MS', (SELECT id FROM role WHERE role_type='USER'));
-- Insert user roles
MERGE INTO user_role (status, date_created, role_id, user_id)
KEY (role_id, user_id)
VALUES (1, NOW(),
        (SELECT id FROM role WHERE role_type='USER'),
        (SELECT id FROM users WHERE username='user'));
