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
        if (isNull(o1.getFilename()) || isNull(o2.getFilename())) {
            throw new IllegalArgumentException("The compared resources have to have a file name");
        }

        return o1.getFilename().compareTo(o2.getFilename());
    }

}
