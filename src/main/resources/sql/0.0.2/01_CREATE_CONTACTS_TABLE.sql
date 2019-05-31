-- Creating Contacts table
CREATE TABLE IF NOT EXISTS Contacts (
    id       INTEGER PRIMARY KEY,
    name     TEXT NOT NULL,
    nickname TEXT,
    number   INTEGER,
    favorite INTEGER DEFAULT 0
);