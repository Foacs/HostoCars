-- Creating Interventions table
CREATE TABLE IF NOT EXISTS Interventions (
    id            INTEGER UNIQUE PRIMARY KEY,
    carId         INTEGER NOT NULL,
    creationYear  INTEGER NOT NULL,
    number        INTEGER NOT NULL,
    status        TEXT    NOT NULL,
    description   TEXT,
    mileage       INTEGER,
    estimatedTime REAL,
    realTime      REAL,
    amount        REAL,
    paidAmount    REAL,
    comments      TEXT,
    FOREIGN KEY (carId)
        REFERENCES Cars (id),
    UNIQUE (creationYear, number)
);
