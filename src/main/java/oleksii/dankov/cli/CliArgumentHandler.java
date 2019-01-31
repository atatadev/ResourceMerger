package oleksii.dankov.cli;

import org.apache.commons.cli.*;

import java.io.File;

public class CliArgumentHandler implements ArgumentsHandler {

    private static final String APP_RES_DIR_ARG_NAME = "appRes";
    private static String LIBS_DIR_ARG_NAME = "libsDir";
    private static String OUTPUT_ARG_NAME = "outputDirectory";
    private final String[] args;
    private final CommandLine cmd;

    private CliArgumentHandler(String[] args, CommandLine cmd) {
        this.args = args;
        this.cmd = cmd;
    }

    public static CliArgumentHandler fromArgs(String[] args) throws ParseException {
        DefaultParser parser = new DefaultParser();
        return new CliArgumentHandler(args, parser.parse(getOptions(), args));
    }


    @Override
    public File getLibsDirectory() {
        return new File(cmd.getOptionValue(LIBS_DIR_ARG_NAME));
    }

    @Override
    public File getOutputDirectory() {
        return new File(cmd.getOptionValue(OUTPUT_ARG_NAME));
    }

    @Override
    public File getAppResDirectory() {
        return new File(cmd.getOptionValue(APP_RES_DIR_ARG_NAME));
    }

    @Override
    public boolean allArgumentsPresent() {
        boolean present;
        present = verifyArgumentPresent(LIBS_DIR_ARG_NAME);
        present = verifyArgumentPresent(OUTPUT_ARG_NAME);
        present = verifyArgumentPresent(APP_RES_DIR_ARG_NAME);
        return present;
    }

    private boolean verifyArgumentPresent(String appResDirArgName) {
        if (!cmd.hasOption(appResDirArgName)) {
            System.out.println( "-" + appResDirArgName + " is mandatory argument");
            return false;
        }
        return true;
    }

    private static Options getOptions() {
        Options options = new Options();
        options.addOption(LIBS_DIR_ARG_NAME, true, "path to libs directory");
        options.addOption(APP_RES_DIR_ARG_NAME, true, "path to app's resources directory");
        options.addOption(OUTPUT_ARG_NAME, true, "merged output file path");
        return options;
    }
}
