-- Update Database version to 0.0.2
UPDATE DatabaseInfo SET value = '0.0.2' WHERE key = 'version';
UPDATE DatabaseInfo SET value = date() WHERE key = 'lastUpdate';
