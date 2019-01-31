package oleksii.dankov.writer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DocumentWriterTest {

    @Before
    public void makeSureTmpFileDoesntExist() {
        File tmp = createTmpFile();
        tmp.delete();
        tmp.getParentFile().delete();
        assertFalse(tmp.exists());
        assertFalse(tmp.getParentFile().exists());
    }

    @After
    public void deleteTest() {
        File tmp = createTmpFile();
        tmp.delete();
        tmp.getParentFile().delete();
    }


    @Test
    public void outputDirectoryShouldBeCreatedIFAbsent() throws Exception {
        DocumentWriter documentWriter = new DocumentWriterImpl();
        File tmp = createTmpFile();
        documentWriter.save(createDummyDocument(), tmp);
        assertTrue(tmp.exists());
        assertTrue(tmp.getParentFile().exists());
    }



    private File createTmpFile() {
        return new File("tmp/test_output/test.xml");
    }

    private Document createDummyDocument() throws Exception {
        Document dummyDocument = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        dummyDocument.appendChild(dummyDocument.createElement("resources"));
        return dummyDocument;
    }
}