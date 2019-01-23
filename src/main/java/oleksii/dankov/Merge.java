package oleksii.dankov;

import oleksii.dankov.cli.CliArgumentHandler;
import oleksii.dankov.merger.ResourceMergerImpl;
import oleksii.dankov.merger.ResourcesMergingException;
import oleksii.dankov.writer.DocumentWriterException;
import oleksii.dankov.writer.DocumentWriterImpl;
import org.apache.commons.cli.ParseException;

public class Merge {

    public static void main(String[] args) throws ParseException, DocumentWriterException, ResourcesMergingException {
        new App(
                CliArgumentHandler.fromArgs(args),
                new DocumentWriterImpl(),
                new ResourceMergerImpl()
        ).process();
    }

}
