-- Setting Database version to 2.0.0
UPDATE DatabaseInfo
SET
    value = '2.0.0'
WHERE
    key = 'version';

UPDATE DatabaseInfo
SET
    value = date()
WHERE
    key = 'lastUpdate';
