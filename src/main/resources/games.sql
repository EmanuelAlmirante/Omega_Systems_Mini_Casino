DROP TABLE IF EXISTS games;

CREATE TABLE games
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(250) NOT NULL,
    chance_of_winning  DECIMAL,
    winning_multiplier DOUBLE,
    max_bet            DOUBLE,
    min_bet            DOUBLE
)
