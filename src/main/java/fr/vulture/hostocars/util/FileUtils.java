package fr.vulture.hostocars.util;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class to manage files.
 */
public class FileUtils {

    /**
     * Default constructor.
     */
    private FileUtils() {
        super();
    }

    /**
     * Returns a BLOB from a file by its URL.
     *
     * @param url
     *     The file URL
     *
     * @return a BLOB
     *
     * @throws IOException
     *     if the file reading fails
     */
    static byte[] readBlobFromUrl(final String url) throws IOException {
        return nonNull(url) ? Files.readAllBytes(Paths.get(url)) : null;
    }

}
