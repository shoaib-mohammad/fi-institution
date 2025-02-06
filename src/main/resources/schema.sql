-- Ensure sequence exists
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

-- Create the role table
CREATE TABLE IF NOT EXISTS role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status INTEGER NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT NOW(),
    role_type VARCHAR(50) NOT NULL UNIQUE
);

-- Create the permission table
CREATE TABLE IF NOT EXISTS permission (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status INTEGER NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT NOW(),
    role_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    UNIQUE(role_id, name),
    CONSTRAINT fk_permission_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Create the user table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status INTEGER NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT NOW(),
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT fk_users_role_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Create the user_role table
CREATE TABLE IF NOT EXISTS user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status INTEGER NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT NOW(),
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    UNIQUE(role_id, user_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Create the institution table
CREATE TABLE IF NOT EXISTS institution (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status INTEGER NOT NULL,
    date_created TIMESTAMP NOT NULL DEFAULT NOW(),
    name VARCHAR(50) NOT NULL,
    currency VARCHAR(255),
    code VARCHAR(5) NOT NULL UNIQUE
);