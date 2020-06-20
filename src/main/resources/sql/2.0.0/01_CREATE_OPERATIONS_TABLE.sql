-- Creating Operations table
CREATE TABLE IF NOT EXISTS Operations (
    id             INTEGER UNIQUE PRIMARY KEY,
    interventionId INTEGER NOT NULL,
    label          TEXT    NOT NULL,
    FOREIGN KEY (interventionId)
        REFERENCES Interventions (id)
);
