package oleksii.dankov.cli;

import java.io.File;

public interface ArgumentsHandler {
    File getLibsDirectory();
    File getOutputDirectory();
    File getAppResDirectory();
    boolean allArgumentsPresent();
}
