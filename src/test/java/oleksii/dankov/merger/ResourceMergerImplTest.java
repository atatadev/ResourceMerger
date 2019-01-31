package oleksii.dankov.merger;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlmatchers.transform.StringResult;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.xpath.HasXPath.hasXPath;

public class ResourceMergerImplTest {

    private DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();;

    @Test
    public void resourcesShouldHaveNs1NameSpace() throws Exception {
        ResourceMerger resourceMerger = new ResourceMergerImpl(dbFactory);
        Document document = resourceMerger.mergeValues(Collections.singletonList(getAppResValues()));
        String xml = getDocumentStr(document);
        assertTrue(xml.contains("xmlns:ns1=\"urn:oasis:names:tc:xliff:document:1.2\""));
    }

    @Test
    public void hasAllFields() throws Exception {
        ResourceMerger resourceMerger = new ResourceMergerImpl(dbFactory);
        List<Document> documents = new ArrayList<>();
        documents.add(getAppResValues());
        documents.add(parseDocument("src/test/resources/libs/lib1/res/values/res1.xml"));
        documents.add(parseDocument("src/test/resources/libs/lib2/res/values/res2.xml"));
        Source xml = the(resourceMerger.mergeValues(documents));
        assertThat(xml, hasXPath("/resources/string[@name='app_name']"));
        assertThat(xml, hasXPath("/resources/color[@name='colorAccent']"));
        assertThat(xml, hasXPath("/resources/color[@name='colorPrimary']"));
        assertThat(xml, hasXPath("/resources/color[@name='colorPrimaryDark']"));
    }

    private Document getAppResValues() throws Exception {
        return parseDocument("src/test/resources/appres/values/values.xml");
    }

    private Document parseDocument(String pathname) throws SAXException, IOException, ParserConfigurationException {
        return dbFactory.newDocumentBuilder()
                .parse(new File(pathname));
    }

    private String getDocumentStr(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StringResult result = new StringResult();
        transformer.transform(source, result);
        return result.toString();
    }
}