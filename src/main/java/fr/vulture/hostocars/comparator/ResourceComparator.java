package fr.vulture.hostocars.comparator;

import static java.util.Objects.isNull;

import java.util.Comparator;
import javax.validation.constraints.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Custom comparator for resources.
 */
@Component("resourceComparator")
public class ResourceComparator implements Comparator<Resource> {

    @Override
    public int compare(@NotNull final Resource o1, @NotNull final Resource o2) {
        final String fileName1 = o1.getFilename();
        final String fileName2 = o2.getFilename();

        if (isNull(fileName1) || isNull(fileName2)) {
            throw new IllegalArgumentException("The compared resources have to have a file name");
        }

        return fileName1.compareTo(fileName2);
    }

}
