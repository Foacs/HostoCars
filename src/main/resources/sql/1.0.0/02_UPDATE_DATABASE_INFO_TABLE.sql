-- Setting Database version to 1.0.0
UPDATE DatabaseInfo
SET
    value = '1.0.0'
WHERE
    key = 'version';

UPDATE DatabaseInfo
SET
    value = date()
WHERE
    key = 'lastUpdate';
