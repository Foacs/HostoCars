package fr.vulture.hostocars.database.controller;

import static fr.vulture.hostocars.comparator.VersionComparator.VERSION_STRING_REGEX;
import static fr.vulture.hostocars.configuration.ProfileEnum.DEV;
import static fr.vulture.hostocars.configuration.ProfileEnum.PROD;

import fr.vulture.hostocars.comparator.ResourceComparator;
import fr.vulture.hostocars.comparator.VersionComparator;
import fr.vulture.hostocars.configuration.ProfileEnum;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

/**
 * Component used to extract SQL resources.
 */
@Component("sqlResourceExtractor")
public class SQLResourceExtractor {

    private static final String EXECUTABLE_SQL_SCRIPT_EXTENSION = ".sql";

    private final ResourceComparator resourceComparator;
    private final VersionComparator versionComparator;

    @NotNull
    @Value("${spring.profiles.active}")
    private String profile;

    @NotNull
    @Value("${sql.scripts.resources.location}")
    private String sqlScriptsResourcesLocation;

    /**
     * Valued autowired constructor.
     *
     * @param resourceComparator
     *     The autowired {@link ResourceComparator} component
     * @param versionComparator
     *     The autowired {@link VersionComparator} component
     */
    @Autowired
    public SQLResourceExtractor(@NotNull final ResourceComparator resourceComparator, @NotNull final VersionComparator versionComparator) {
        this.resourceComparator = resourceComparator;
        this.versionComparator = versionComparator;
    }

    /**
     * Extracts the executable SQL resources, groups them by their corresponding versions (ignoring versions lower than the current database one and
     * those higher than the target one) and sort them by version and by file name.
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
    SortedMap<String, SortedSet<Resource>> extractSQLResources(@NotNull final String currentVersion, @NotNull final String targetVersion)
        throws IOException {
        // Extracts the resources from the classpath
        final Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:" + sqlScriptsResourcesLocation + "/**");

        // Extracts the SQL resources paths depending on the active profile
        final Map<String, Resource> resourcesPaths = extractResourcePaths(resources);

        // Extracts the versions and groups the resources by their corresponding ones
        return groupResourcesByVersions(resourcesPaths, currentVersion, targetVersion);
    }

    /**
     * Extracts the paths from the input resources and map them to their corresponding ones, ignoring the resources that are not executable SQL ones.
     *
     * @param resources
     *     The resources from which to extracts the paths
     *
     * @return a map of resources by their corresponding paths
     */
    private Map<String, Resource> extractResourcePaths(@NotNull final Resource[] resources) {
        final ProfileEnum activeProfile = ProfileEnum.valueOf(profile.toUpperCase());

        // Extracts the SQL resources paths depending on the active profile
        final Map<String, Resource> result = new HashMap<>();
        if (PROD.equals(activeProfile)) {
            for (final Resource resource : resources) {
                final String resourcePath = ((ClassPathResource) resource).getPath();

                // Filters the resources that are not executable SQL scripts
                if (resourcePath.endsWith(EXECUTABLE_SQL_SCRIPT_EXTENSION)) {
                    result.put(resourcePath, resource);
                }
            }
        } else if (DEV.equals(activeProfile)) {
            for (final Resource resource : resources) {
                final String resourcePath = ((FileSystemResource) resource).getPath();

                // Filters the resources that are not executable SQL scripts
                if (resourcePath.endsWith(EXECUTABLE_SQL_SCRIPT_EXTENSION)) {
                    result.put(resourcePath, resource);
                }
            }
        }

        return result;
    }

    /**
     * Groups the input resources by their corresponding versions, filtering the ones that are below the current database version or over the target
     * version.
     *
     * @param resourcesPaths
     *     The map of resources by their extracted path
     * @param currentVersion
     *     The current database version
     * @param targetVersion
     *     The target version
     *
     * @return a sorted map of the sorted resources by their corresponding versions
     */
    private SortedMap<String, SortedSet<Resource>> groupResourcesByVersions(@NotNull final Map<String, Resource> resourcesPaths,
        @NotNull final String currentVersion, @NotNull final String targetVersion) {
        final SortedMap<String, SortedSet<Resource>> result = new TreeMap<>(versionComparator);
        for (final String resourcePath : resourcesPaths.keySet()) {
            // Tries to find a version in the current path
            final Matcher matcher = java.util.regex.Pattern.compile(VERSION_STRING_REGEX).matcher(resourcePath);

            // If a version has been found, tries to add the current resource to the result
            if (matcher.find()) {
                final String version = matcher.group(0);

                // Adds the current resource only if the corresponding version is higher than the current one and lower or equal to the target one
                if (versionComparator.compare(version, currentVersion) > 0 || versionComparator.compare(version, targetVersion) <= 0) {
                    // If no previous script has been added to the current version, initialize the set of resources
                    if (!result.containsKey(version)) {
                        result.put(version, new TreeSet<>(resourceComparator));
                    }

                    result.get(version).add(resourcesPaths.get(resourcePath));
                }
            }
        }

        return result;
    }

}
