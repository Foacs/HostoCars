package fr.vulture.hostocars.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

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
    public static byte[] readBlobFromUrl(final String url) throws IOException {
        return Objects.nonNull(url) ? Files.readAllBytes(Paths.get(url)) : null;
    }

}
