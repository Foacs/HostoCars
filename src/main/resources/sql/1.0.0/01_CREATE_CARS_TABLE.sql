-- Creating Cars table
CREATE TABLE IF NOT EXISTS Cars (
    id           INTEGER UNIQUE PRIMARY KEY,
    owner        TEXT        NOT NULL,
    registration TEXT UNIQUE NOT NULL,
    brand        TEXT,
    model        TEXT,
    motorization TEXT,
    releaseDate  DATE,
    certificate  BLOB,
    comments     TEXT,
    picture      BLOB
);
