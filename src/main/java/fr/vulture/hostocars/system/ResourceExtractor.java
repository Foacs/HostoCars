package fr.vulture.hostocars.system;

import static fr.vulture.hostocars.configuration.ProfileEnum.DEV;
import static fr.vulture.hostocars.configuration.ProfileEnum.PROD;

import fr.vulture.hostocars.configuration.ProfileEnum;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.util.Version;
import org.springframework.stereotype.Component;

/**
 * Extractor component for the resources.
 */
@Slf4j
@Component
public class ResourceExtractor {

    @NonNull
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Extracts the icon resource file {@link URL} used for the application tray icon.
     *
     * @return the {@link URL} of the tray icon resource file
     *
     * @throws IOException
     *     if an I/O error occurs while extracting the resource URL
     */
    final URL extractIcon() throws IOException {
        return new PathMatchingResourcePatternResolver().getResources("classpath:*icon.png")[0].getURL();
    }

    /**
     * Extracts the executable SQL resources, groups them by their corresponding versions and sort them by version and by file name. <br /> The
     * versions lower than the current one or higher than the target one are ignored.
     *
     * @param currentVersion
     *     The current database version
     * @param targetVersion
     *     The target version
     *
     * @return a sorted map of the resources by their corresponding versions
     *
     * @throws IOException
     *     if an I/O error occurs while extracting the resources
     */
    public final SortedMap<Version, SortedSet<Resource>> extractSQLResources(@NonNull final Version currentVersion,
        @NonNull final Version targetVersion) throws IOException {
        log.debug("Extracting the executable SQL resources from version {} to {}", currentVersion, targetVersion);

        // Extracts the resources from the classpath
        final Resource[] resourceArray = new PathMatchingResourcePatternResolver().getResources("classpath:sql/**");

        log.trace("{} resource(s) found", resourceArray.length);

        // Extracts the SQL resources paths depending on the active profile
        final Map<String, Resource> resourceByPathMap = this.extractResourcePaths(resourceArray);

        // Extracts the versions and groups the resources by their corresponding ones
        return this.groupResourcesByVersions(resourceByPathMap, currentVersion, targetVersion);
    }

    /**
     * Extracts the paths from the given resources and map them to their corresponding ones, ignoring the resources that are not executable SQL ones.
     *
     * @param resourceArray
     *     The resources from which to extracts the paths
     *
     * @return a map of resources by their corresponding paths
     */
    private Map<String, Resource> extractResourcePaths(@NonNull final Resource[] resourceArray) {
        log.debug("Extracting paths from found resources");

        // Retrieves the runtime active profile in upper case
        final ProfileEnum activeProfile = ProfileEnum.valueOf(this.profile.toUpperCase(Locale.ENGLISH));

        log.trace("Active profile detected : {}", activeProfile);

        // Extracts the SQL resources paths depending on the active profile
        final Map<String, Resource> resultMap = new HashMap<>(0);
        if (PROD == activeProfile) {
            // Iterates over the resources to retrieve their paths
            for (final Resource resource : resourceArray) {
                // Retrieves the resource's path
                final String resourcePath = ((ClassPathResource) resource).getPath();

                log.trace("Resource path : {}", resourcePath);

                // Filters the resources that are not executable SQL scripts
                if (resourcePath.endsWith(".sql")) {
                    resultMap.put(resourcePath, resource);

                    log.trace("Resource \"{}\" added", resourcePath);
                } else {
                    log.trace("Resource \"{}\" ignored", resourcePath);
                }
            }
        } else if (DEV == activeProfile) {
            for (final Resource resource : resourceArray) {
                // Retrieves the resource's path
                final String resourcePath = ((FileSystemResource) resource).getPath();

                log.trace("Resource path : {}", resourcePath);

                // Filters the resources that are not executable SQL scripts
                if (resourcePath.endsWith(".sql")) {
                    resultMap.put(resourcePath, resource);
                    log.trace("Resource \"{}\" added", resourcePath);
                } else {
                    log.trace("Resource \"{}\" ignored", resourcePath);
                }
            }
        }

        log.debug("Paths extracted from resources ({} out of {} remaining)", resultMap.size(), resourceArray.length);

        return resultMap;
    }

    /**
     * Groups the given resources by their corresponding versions, filtering the ones that are lower then the current one or higher than the target
     * one.
     *
     * @param resourceByPathMap
     *     The map of resources by their extracted path
     * @param currentVersion
     *     The current database version
     * @param targetVersion
     *     The target version
     *
     * @return a sorted map of the sorted resources by their corresponding versions
     */
    private SortedMap<Version, SortedSet<Resource>> groupResourcesByVersions(final @NonNull Map<String, ? extends Resource> resourceByPathMap,
        @NonNull final Version currentVersion, @NonNull final Version targetVersion) {
        log.debug("Grouping the found resources by version");

        // Declares the regular expression to extract a version
        final String versionRegex = "[0-9]+(?:[.][0-9]+)+";

        // Iterates over the found resources to put them in the result map
        final SortedMap<Version, SortedSet<Resource>> resultMap = new TreeMap<>(Version::compareTo);
        for (final Entry<String, ? extends Resource> entry : resourceByPathMap.entrySet()) {
            // Gets the resource path
            final String resourcePath = entry.getKey();

            // Tries to find a version in the current path
            final Matcher matcher = Pattern.compile(versionRegex).matcher(resourcePath);

            // If a version has been found, tries to add the resource to the result map
            if (matcher.find()) {
                // Gets the found version
                final Version version = Version.parse(matcher.group(0));

                log.trace("Found version for resource path \"{}\" : {}", resourcePath, version);

                // Adds the current resource only if the corresponding version is higher than the current one and lower or equal to the target one
                if (0 < version.compareTo(currentVersion) || 0 >= version.compareTo(targetVersion)) {
                    // If no previous resource has been added to the current version, initialize the set of resources
                    if (!resultMap.containsKey(version)) {
                        resultMap.put(version, new TreeSet<>(Comparator.comparing(Resource::getFilename)));
                    }

                    // Adds the resource to the result map
                    resultMap.get(version).add(entry.getValue());

                    log.trace("Resource \"{}\" added", resourcePath);
                } else {
                    log.trace("Resource \"{}\" ignored", resourcePath);
                }
            }
        }

        log.debug("Resources grouped by versions ({} out of {} remaining for {} versions)",
            resultMap.values().stream().map(Set::size).reduce(0, Integer::sum), resourceByPathMap.size(), resultMap.size());

        return resultMap;
    }

}
