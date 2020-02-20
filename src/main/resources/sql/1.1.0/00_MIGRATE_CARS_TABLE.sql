-- Creating the migration table for the Cars table
CREATE TABLE CarsTemp (
    id           INTEGER UNIQUE PRIMARY KEY,
    registration TEXT UNIQUE NOT NULL,
    serialNumber TEXT UNIQUE,
    owner        TEXT        NOT NULL,
    brand        TEXT,
    model        TEXT,
    motorization TEXT,
    engineCode   TEXT,
    releaseDate  DATE,
    comments     TEXT,
    certificate  BLOB,
    picture      BLOB
);

-- Copying all data from the Cars table to the migration table
INSERT INTO CarsTemp
SELECT
    id,
    registration,
    NULL,
    owner,
    brand,
    model,
    motorization,
    NULL,
    releaseDate,
    comments,
    certificate,
    picture
FROM
    Cars;

-- Dropping the Cars table
DROP TABLE Cars;

-- Renaming the migration table to the Cars table
ALTER TABLE CarsTemp RENAME TO Cars;
