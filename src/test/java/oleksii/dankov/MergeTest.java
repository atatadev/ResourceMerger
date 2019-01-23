package oleksii.dankov;

import oleksii.dankov.cli.CliArgumentHandler;
import oleksii.dankov.merger.ResourceMergerImpl;
import oleksii.dankov.merger.ResourcesMergingException;
import oleksii.dankov.writer.DocumentWriterException;
import oleksii.dankov.writer.DocumentWriterImpl;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class MergeTest {
    @Test
    public void generalAppTest() throws DocumentWriterException, ResourcesMergingException, ParseException, ParserConfigurationException, SAXException, IOException {
        String[] args = {
                "-libsDir", "src/test/java/oleksii/dankov/libs",
                "-appRes", "src/test/java/oleksii/dankov/appres/",
                "-outputDirectory", "tmp/output"
        };
        File original = new File("src/test/java/oleksii/dankov/expected_merged.xml");
        File generated = new File("tmp/output/values/values.xml");
        CliArgumentHandler argsHandler = CliArgumentHandler.fromArgs(args);
        new App(argsHandler, new DocumentWriterImpl(), new ResourceMergerImpl()).process();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        NodeList nodesOriginal = parseFile(original, dbFactory);
        NodeList nodesGenerated = parseFile(generated, dbFactory);
        assertEquals(nodesOriginal.getLength(), nodesGenerated.getLength());
    }
    private static NodeList parseFile(File original, DocumentBuilderFactory dbFactory) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();

        Document doc = dbBuilder.parse(original);
        Element rootElement = doc.getDocumentElement();
        rootElement.normalize();
        return rootElement.getChildNodes();
    }
}