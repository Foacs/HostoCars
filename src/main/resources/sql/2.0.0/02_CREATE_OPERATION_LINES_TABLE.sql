-- Creating OperationLines table
CREATE TABLE IF NOT EXISTS OperationLines (
    id          INTEGER UNIQUE PRIMARY KEY,
    operationId INTEGER NOT NULL,
    type        TEXT    NOT NULL,
    description TEXT    NOT NULL,
    done        INTEGER NOT NULL,
    FOREIGN KEY (operationId)
        REFERENCES Operations (id)
);
