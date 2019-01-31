package oleksii.dankov;

import oleksii.dankov.cli.CliArgumentHandler;
import oleksii.dankov.merger.ResourceMergerImpl;
import oleksii.dankov.parser.DocumentsParserImpl;
import oleksii.dankov.writer.DocumentWriterImpl;

import javax.xml.parsers.DocumentBuilderFactory;

public class Merge {

    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setIgnoringComments(true);
        new App(
                CliArgumentHandler.fromArgs(args),
                new DocumentsParserImpl(dbFactory),
                new ResourceMergerImpl(dbFactory),
                new DocumentWriterImpl()
        ).process();
    }

}
