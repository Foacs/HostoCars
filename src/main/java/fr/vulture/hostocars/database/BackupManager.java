package fr.vulture.hostocars.database;

import static java.util.Objects.isNull;

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
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Manager component for the database backups.
 */
@Slf4j
@Component("backupManager")
public class BackupManager {

    private static final String BACKUP_FILE_NAME_PREFIX = "data.backup.";
    private static final String BACKUP_FILE_NAME_UPDATE_PREFIX = "data.update.backup.";
    private static final String BACKUP_FILE_NAME_SUFFIX = ".db.gz";

    @NonNull
    @Value("${database.location}")
    private String databaseLocation;

    @NonNull
    @Value("${database.path}")
    private String databasePath;

    @NonNull
    @Value("${database.backup.number}")
    private Integer databaseBackupNumber;

    @NonNull
    @Value("${database.backup.update.number}")
    private Integer databaseUpdateBackupNumber;

    @NonNull
    @Value("${database.backup.delay}")
    private Integer databaseBackupDelay;

    /**
     * Backups the database if needed (depending on the existing backup files).
     *
     * @throws IOException
     *     if an error occurs while reading the database file or writing the backup file
     */
    final void backupDatabaseIfNeeded() throws IOException {
        if (this.isBackupNeeded()) {
            log.debug("Creating a backup of the database");
            this.backupDatabase(BACKUP_FILE_NAME_PREFIX, this.databaseBackupNumber);
        } else {
            log.debug("No backup needed for the database");
        }
    }

    /**
     * Returns if a backup is needed depending on the existing backup files. <br /> A backup is needed when :
     * <ul>
     * <li>No backup file exists</li>
     * <li>The latest backup file is too old (see {@code database.backup.delay} property)</li>
     * </ul>
     *
     * @return if a backup is needed
     */
    private boolean isBackupNeeded() {
        // Retrieves the array of files contained in the database folder
        final File[] backupFileArray = new File(this.databaseLocation).listFiles();

        // If there is no backup file, returns true
        if (isNull(backupFileArray)) {
            log.trace("No backup file found");
            return true;
        }

        log.trace("{} backup file(s) found", backupFileArray.length);

        // Retrieves the latest backup file
        final Optional<File> latestBackupFile = Stream.of(backupFileArray)
            .filter(file -> file.getName().startsWith(BACKUP_FILE_NAME_PREFIX))
            .min(Comparator.comparingLong(File::lastModified));

        // If there is no backup file, returns true (should not occur at this point)
        if (!latestBackupFile.isPresent()) {
            return true;
        }

        // Retrieves the creation date of the latest backup file
        final LocalDate oldestBackupDate = Instant.ofEpochMilli(latestBackupFile.get().lastModified()).atZone(ZoneId.systemDefault()).toLocalDate();

        log.trace("Latest backup file date found : {}", oldestBackupDate.format(DateTimeFormatter.ISO_LOCAL_DATE));

        // Returns if the latest backup file's date is too old or not
        return ChronoUnit.DAYS.between(oldestBackupDate, LocalDate.now()) >= this.databaseBackupDelay;
    }

    /**
     * Creates a backup copy of the database file with the specified prefix. <br /> Deletes older backup files with the same prefix so that the number
     * of backup files of that type doesn't exceed the given number.
     *
     * @param backupFileNamePrefix
     *     The prefix of the backup file to create
     * @param maxCount
     *     The maximum number of backup files allowed with the given prefix
     *
     * @throws IOException
     *     if an error occurs while reading the database file or writing the backup file
     */
    private void backupDatabase(final String backupFileNamePrefix, final long maxCount) throws IOException {
        // Retrieves the next backup file index (before deleting the older ones)
        final int next = this.getNextBackupFileIndex(backupFileNamePrefix);

        // Deletes as many older backups as needed in order not to exceed the given maximum number of backups
        for (long count = this.countExistingBackups(backupFileNamePrefix); count >= maxCount; count--) {
            // Searches and deletes the oldest backup file with the given prefix
            this.deleteOldestBackupFile(backupFileNamePrefix);
        }

        // Computes the path of the backup file being created
        final String backupFilePath = this.databaseLocation.concat("/")
            .concat(backupFileNamePrefix)
            .concat(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .concat(".")
            .concat(String.valueOf(next))
            .concat(BACKUP_FILE_NAME_SUFFIX);

        // Gets references to the existing database file and the to-be-created backup file
        final File databaseFile = new File(this.databasePath);

        log.trace("Creating backup at : {}", backupFilePath);

        // Copies the database file content into the backup file
        final File backupFile = new File(backupFilePath);
        try (final GzipCompressorOutputStream out = new GzipCompressorOutputStream(new FileOutputStream(backupFile))) {
            IOUtils.copy(new FileInputStream(databaseFile), out);
        }

        log.debug("Backup file created at : {}", backupFilePath);
    }

    /**
     * Returns the index of the next backup file to be created, depending on the existing backup files names with the given prefix.
     *
     * @param backupFileNamePrefix
     *     The prefix of the backup file to create
     *
     * @return the index of the next backup file to be created
     */
    private int getNextBackupFileIndex(final String backupFileNamePrefix) {
        // Retrieves the array of files contained in the database folder
        final File[] backupFileArray = new File(this.databaseLocation).listFiles();

        // If there is no backup file, returns 0
        if (isNull(backupFileArray)) {
            log.trace("No backup file found");
            return 0;
        }

        log.trace("{} backup file(s) found", backupFileArray.length);

        // Adds today's date to the given prefix
        final String todayBackupFileNamePrefix = backupFileNamePrefix.concat(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)).concat(".");

        // Returns the highest existing index incremented by one, or 0 if it can't be found (should not occur at this point)
        return Stream.of(backupFileArray)
            .map(File::getName)
            .filter(fileName -> fileName.startsWith(todayBackupFileNamePrefix))
            .map(fileName -> Integer.valueOf(fileName.replaceAll(todayBackupFileNamePrefix, "").split("[.]")[0]))
            .max(Comparator.comparingInt(Integer::valueOf))
            .map(maxIndex -> maxIndex + 1)
            .orElse(0);
    }

    /**
     * Returns the number of existing backup files with the given prefix.
     *
     * @param backupFileNamePrefix
     *     The prefix of the backup file to create
     *
     * @return the number of existing backup files with the given prefix
     */
    private long countExistingBackups(final String backupFileNamePrefix) {
        // Retrieves the array of files contained in the database folder
        final File[] backupFileArray = new File(this.databaseLocation).listFiles();

        // If there is no backup file, returns 0
        if (isNull(backupFileArray)) {
            log.trace("No backup file found");
            return 0L;
        }

        log.trace("{} backup file(s) found", backupFileArray.length);

        // Returns the number of backup files with the given prefix
        return Stream.of(backupFileArray).map(File::getName).filter(fileName -> fileName.startsWith(backupFileNamePrefix)).count();
    }

    /**
     * Deletes the oldest backup file with the given prefix.
     *
     * @param backupFileNamePrefix
     *     The prefix of the backup file to create
     */
    private void deleteOldestBackupFile(final String backupFileNamePrefix) {
        // Retrieves the array of files contained in the database folder
        final File[] backupFileArray = new File(this.databaseLocation).listFiles();

        // If there is no backup file, does nothing ; else, deletes the oldest one
        if (isNull(backupFileArray)) {
            log.trace("No backup file found");
        } else {
            log.trace("{} backup file(s) found", backupFileArray.length);

            // Deletes the oldest found backup file
            Stream.of(backupFileArray)
                .filter(file -> file.getName().startsWith(backupFileNamePrefix))
                .min(Comparator.comparingLong(File::lastModified))
                .ifPresent(File::delete);
        }
    }

    /**
     * Backups the database before an update.
     *
     * @throws IOException
     *     if an error occurs while reading the database file or writing the backup file
     */
    final void backupDatabaseForUpdate() throws IOException {
        log.debug("Creating a backup of the database before updating");
        this.backupDatabase(BACKUP_FILE_NAME_UPDATE_PREFIX, this.databaseUpdateBackupNumber);
    }

}
