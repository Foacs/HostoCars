-- Creating DatabaseInfo table
CREATE TABLE IF NOT EXISTS DatabaseInfo (
    id    INTEGER UNIQUE PRIMARY KEY,
    key   TEXT NOT NULL,
    value TEXT
);

-- Inserting Database version to 0.0.0
INSERT INTO DatabaseInfo(
    key,
    value
)
SELECT
    'version',
    '0.0.0'
WHERE
    NOT EXISTS(SELECT * FROM DatabaseInfo WHERE key = 'version');

INSERT INTO DatabaseInfo(
    key,
    value
)
SELECT
    'lastUpdate',
    date()
WHERE
    NOT EXISTS(SELECT * FROM DatabaseInfo WHERE key = 'lastUpdate');
