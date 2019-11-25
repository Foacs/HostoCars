package fr.vulture.hostocars.database.controller;

import static java.util.Objects.isNull;

import fr.vulture.hostocars.exception.TechnicalException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Manager component for the database backups.
 */
@Component("databaseBackupManager")
public class DatabaseBackupManager {

    private static final String BACKUP_FILE_PATH_SEPARATOR = "/";
    private static final String BACKUP_FILE_NAME_PREFIX = "data.backup.";
    private static final String BACKUP_FILE_NAME_UPDATE_PREFIX = "data.update.backup.";
    private static final String BACKUP_FILE_NAME_SUFFIX = ".db";
    private static final String BACKUP_FILE_NAME_SEPARATOR = ".";
    private static final String BACKUP_FILE_EXTENSION = ".gz";

    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_INDEX = "0";

    private static final String BACKUP_FILE_NAME_SEPARATOR_REGEX = "\\.";

    @NotNull
    @Value("${database.location}")
    private String databaseLocation;

    @NotNull
    @Value("${database.path}")
    private String databasePath;

    @NotNull
    @Value("${database.backup.number}")
    private Integer databaseBackupNumber;

    @NotNull
    @Value("${database.backup.delay}")
    private Integer databaseBackupDelay;

    @NotNull
    @Value("${database.update.backup.number}")
    private Integer databaseUpdateBackupNumber;

    /**
     * Creates a backup of the database.
     *
     * @param isUpdateBackup
     *     If the backup is for a database update
     *
     * @throws IOException
     *     if an error occurs while reading the database file or writing the backup file
     * @throws TechnicalException
     *     if no file is found in the database folder
     */
    final void backupDatabase(final boolean isUpdateBackup) throws IOException, TechnicalException {
        if (isUpdateBackup || this.isBackupNeeded()) {
            final String next = this.getNextBackupFileIndex(isUpdateBackup);

            final long maxCount = isUpdateBackup ? this.databaseUpdateBackupNumber : this.databaseBackupNumber;
            for (long count = this.countExistingBackups(isUpdateBackup); count >= maxCount; count--) {
                this.deleteOldestBackupFile(isUpdateBackup);
            }

            final String backupFilePath = this.databaseLocation
                .concat(BACKUP_FILE_PATH_SEPARATOR)
                .concat(isUpdateBackup ? BACKUP_FILE_NAME_UPDATE_PREFIX : BACKUP_FILE_NAME_PREFIX)
                .concat(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .concat(BACKUP_FILE_NAME_SEPARATOR)
                .concat(next)
                .concat(BACKUP_FILE_NAME_SUFFIX)
                .concat(BACKUP_FILE_EXTENSION);

            final File databaseFile = new File(this.databasePath);
            final File backupFile = new File(backupFilePath);

            try (final GzipCompressorOutputStream out = new GzipCompressorOutputStream(new FileOutputStream(backupFile))) {
                IOUtils.copy(new FileInputStream(databaseFile), out);
            }
        }
    }

    /**
     * Returns if a backup is needed depending on the existing backup files.
     *
     * @return if a backup is needed
     *
     * @throws TechnicalException
     *     if no file is found in the database folder
     */
    private boolean isBackupNeeded() throws TechnicalException {
        final File[] backupFiles = new File(this.databaseLocation).listFiles();

        if (isNull(backupFiles)) {
            throw new TechnicalException("No file found in database location");
        }

        final Optional<File> oldestBackupFile = Stream.of(backupFiles)
            .filter(file -> file.getName().startsWith(BACKUP_FILE_NAME_PREFIX))
            .min(Comparator.comparingLong(File::lastModified));

        if (!oldestBackupFile.isPresent()) {
            return true;
        }

        final LocalDate oldestBackupDate = Instant.ofEpochMilli(oldestBackupFile.get().lastModified()).atZone(ZoneId.systemDefault()).toLocalDate();
        return ChronoUnit.DAYS.between(oldestBackupDate, LocalDate.now()) >= this.databaseBackupDelay;
    }

    /**
     * Returns the index of the next backup file to be created.
     *
     * @param isUpdateBackup
     *     If the backup is for a database update
     *
     * @return the index of the next backup file to be created
     *
     * @throws TechnicalException
     *     if no file is found in the database folder
     */
    private String getNextBackupFileIndex(final boolean isUpdateBackup) throws TechnicalException {
        final String backupFileNamePrefix = (isUpdateBackup ? BACKUP_FILE_NAME_UPDATE_PREFIX : BACKUP_FILE_NAME_PREFIX)
            .concat(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .concat(BACKUP_FILE_NAME_SEPARATOR);

        final File[] backupFiles = new File(this.databaseLocation).listFiles();

        if (isNull(backupFiles)) {
            throw new TechnicalException("No file found in database location");
        }

        return Stream.of(backupFiles)
            .map(File::getName)
            .filter(fileName -> fileName.startsWith(backupFileNamePrefix))
            .map(fileName -> Integer.valueOf(fileName.replaceAll(backupFileNamePrefix, EMPTY_STRING).split(BACKUP_FILE_NAME_SEPARATOR_REGEX)[0]))
            .max(Comparator.comparingInt(Integer::valueOf))
            .map(maxIndex -> String.valueOf(maxIndex + 1)).orElse(DEFAULT_INDEX);
    }

    /**
     * Returns the number of existing backup files.
     *
     * @param isUpdateBackup
     *     If the backup is for a database update
     *
     * @return the number of existing backup files
     *
     * @throws TechnicalException
     *     if no file is found in the database folder
     */
    private long countExistingBackups(final boolean isUpdateBackup) throws TechnicalException {
        final File[] backupFiles = new File(this.databaseLocation).listFiles();

        if (isNull(backupFiles)) {
            throw new TechnicalException("No file found in database location");
        }

        return Stream.of(backupFiles)
            .map(File::getName)
            .filter(fileName -> fileName.startsWith(isUpdateBackup ? BACKUP_FILE_NAME_UPDATE_PREFIX : BACKUP_FILE_NAME_PREFIX))
            .count();
    }

    /**
     * Delete the oldest backup file.
     *
     * @param isUpdateBackup
     *     If the backup is for a database update
     *
     * @throws TechnicalException
     *     if no file is found in the database folder
     */
    private void deleteOldestBackupFile(final boolean isUpdateBackup) throws TechnicalException {
        final File[] backupFiles = new File(this.databaseLocation).listFiles();

        if (isNull(backupFiles)) {
            throw new TechnicalException("No file found in database location");
        }

        Stream.of(backupFiles)
            .filter(file -> file.getName().startsWith(isUpdateBackup ? BACKUP_FILE_NAME_UPDATE_PREFIX : BACKUP_FILE_NAME_PREFIX))
            .min(Comparator.comparingLong(File::lastModified))
            .ifPresent(File::delete);
    }

}
