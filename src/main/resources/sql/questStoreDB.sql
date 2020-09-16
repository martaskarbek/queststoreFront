CREATE TABLE roles
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE users
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role_id BIGINT REFERENCES roles (id),
    isActive BOOLEAN NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE categories
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    category VARCHAR(500)
);


CREATE TABLE modules
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE mentors
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id)
);

CREATE TABLE mentor_modules
(
    module_id BIGINT REFERENCES modules (id),
    mentor_id BIGINT REFERENCES mentors (id)
);


CREATE TABLE rewards
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price BIGINT,
    category_id BIGINT REFERENCES categories (id),
    mentor_id BIGINT REFERENCES users (id),
    isActive BOOLEAN NOT NULL
);



CREATE TABLE shared_wallets
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    reward_id BIGINT REFERENCES rewards (id),
    coins BIGINT
);


CREATE TABLE quest_statuses
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    status VARCHAR(500)
);

CREATE TABLE quests
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(500) NOT NULL,
    coins_to_earn BIGINT,
    module_id BIGINT REFERENCES modules (id),
    mentor_id BIGINT REFERENCES mentors (id),
    category_id BIGINT REFERENCES categories (id),
    isActive BOOLEAN NOT NULL
);


CREATE TABLE students
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id),
    module_id BIGINT REFERENCES modules (id),
    wallet BIGINT,
);

CREATE TABLE student_quests
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    student_id BIGINT REFERENCES students (id),
    quest_id BIGINT REFERENCES quests (id),
    quest_status_id BIGINT REFERENCES quest_statuses (id),
    quest_input_area VARCHAR(500)
);

CREATE TABLE order_statuses
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    status VARCHAR(500)
);


CREATE TABLE orders
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    student_id BIGINT REFERENCES students (id),
    reward_id BIGINT REFERENCES rewards (id),
    order_status_id BIGINT REFERENCES order_statuses (id)
);

CREATE TABLE sessions
(
    uuid VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGSERIAL REFERENCES users (id)
);

