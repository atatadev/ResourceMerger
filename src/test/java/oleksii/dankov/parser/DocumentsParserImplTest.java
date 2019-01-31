package oleksii.dankov.parser;

import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DocumentsParserImplTest {

    @Test
    public void shouldContainDocumentsForDifferentQualifiers() throws Exception {
        DocumentsParser parser = new DocumentsParserImpl(DocumentBuilderFactory.newInstance());
        ArrayList<File> documents = new ArrayList<>();
        documents.add(new File("src/test/resources/libs/lib1/res"));
        documents.add(new File("src/test/resources/libs/lib2/res"));
        documents.add(new File("src/test/resources/appres/"));
        Map<String, List<Document>> documentsForQualifiers = parser.parseDocuments(documents);

        assertTrue(documentsForQualifiers.containsKey("values"));
        assertTrue(documentsForQualifiers.containsKey("values_en"));
        assertEquals(3, documentsForQualifiers.get("values").size());
        assertEquals(1,documentsForQualifiers.get("values_en").size());
    }
}