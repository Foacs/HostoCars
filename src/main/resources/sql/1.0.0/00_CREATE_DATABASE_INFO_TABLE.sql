-- Creating DatabaseInfo table
CREATE TABLE IF NOT EXISTS DatabaseInfo (
    id    INTEGER PRIMARY KEY,
    key   TEXT NOT NULL,
    value TEXT
);

-- Setting Database version to 1.0.0
INSERT INTO DatabaseInfo(key, value) VALUES ('version', '1.0.0');
INSERT INTO DatabaseInfo(key, value)
VALUES ('lastUpdate', date('YYYY-MM-DD'));
