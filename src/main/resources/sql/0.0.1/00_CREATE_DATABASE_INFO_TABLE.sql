-- Creating DatabaseInfo table
CREATE TABLE IF NOT EXISTS DatabaseInfo (
    id    INTEGER PRIMARY KEY,
    key   TEXT NOT NULL,
    value TEXT
);

-- Setting Database version to 0.0.1
INSERT INTO DatabaseInfo(key, value) VALUES ('version', '0.0.1');
INSERT INTO DatabaseInfo(key, value) VALUES ('lastUpdate', date());
