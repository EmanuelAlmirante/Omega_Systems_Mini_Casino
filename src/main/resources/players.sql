DROP TABLE IF EXISTS players;

CREATE TABLE players
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(250) PRIMARY KEY,
    name       VARCHAR(250) NOT NULL,
    birth_date DATE         NOT NULL,
    balance    DOUBLE
)
