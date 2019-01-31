package oleksii.dankov.cli;

import java.io.File;

public interface ArgumentsHandler {
    /***
     * retrieves libs directory
     * @return libs directory file
     */
    File getLibsDirectory();

    /***
     * retrieves output directory
     * @return output directory file
     */
    File getOutputDirectory();

    /***
     * retrieves application /res directory
     * @return application /res directory file
     */
    File getAppResDirectory();

    /***
     * checks all presence of all arguments
     * @return true - if all arguments present, false if at least one argument absent
     */
    boolean allArgumentsPresent();
}
