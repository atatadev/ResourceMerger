package oleksii.dankov.filters;

import java.io.File;
import java.io.FileFilter;

public class ValuesFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().startsWith("values");
    }
}
