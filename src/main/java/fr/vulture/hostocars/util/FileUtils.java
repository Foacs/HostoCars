package fr.vulture.hostocars.util;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class to manage files.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FileUtils {

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
