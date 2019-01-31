package oleksii.dankov.cli;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CliArgumentHandlerTest {

    @Test
    public void AllFilesShouldBeNonNullWhenAllArgumentsPresent() throws Exception {
        String[] args = {
                "-libsDir", "src/test/resources/libs",
                "-appRes", "src/test/resources/appres/",
                "-outputDirectory", "tmp/output"
        };
        CliArgumentHandler cli = CliArgumentHandler.fromArgs(args);
        assertTrue(cli.allArgumentsPresent());
        assertNotNull(cli.getAppResDirectory());
        assertNotNull(cli.getLibsDirectory());
        assertNotNull(cli.getOutputDirectory());
    }

    @Test
    public void allArgumentsPresentShouldReturnFalseWhenAppResAbsent() throws Exception {
        String[] args = {
                "-libsDir", "src/test/resources/libs",
                "-outputDirectory", "tmp/output"
        };
        boolean actual = CliArgumentHandler.fromArgs(args).allArgumentsPresent();
        assertFalse(actual);
    }

    @Test
    public void allArgumentsPresentShouldReturnFalseWhenLibAbsent() throws Exception {
        String[] args = {
                "-appRes", "src/test/resources/appres/",
                "-outputDirectory", "tmp/output"
        };
        boolean actual = CliArgumentHandler.fromArgs(args).allArgumentsPresent();
        assertFalse(actual);
    }

    @Test
    public void allArgumentsPresentShouldReturnFalseWhenOutputAbsent() throws Exception {

        String[] args = {
                "-libsDir", "src/test/resources/libs",
                "-appRes", "src/test/resources/appres/"
        };
        boolean actual = CliArgumentHandler.fromArgs(args).allArgumentsPresent();
        assertFalse(actual);
    }
}