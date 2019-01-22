package oleksii.dankov.merger;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResourceMerger {

    private final DocumentBuilderFactory dbFactory;

    public ResourceMerger() {
        dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setIgnoringComments(true);
    }

    public Document mergeValues(List<File> files) throws ResourcesMergingException {
        try {
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document mergedDocument = dbBuilder.newDocument();
            mergedDocument.setXmlStandalone(true);
            Element root = mergedDocument.createElementNS( "urn:oasis:names:tc:xliff:document:1.2","resources");
            mergedDocument.appendChild(root);

            Map<String, Node> mergedValues = new TreeMap<>();
            for (File file : files) {
                try {
                    Document doc = dbBuilder.parse(file);
                    Element rootElement = doc.getDocumentElement();
                    rootElement.normalize();
                    NodeList values = rootElement.getChildNodes();

                    for (int i = 0; i < values.getLength(); i++) {
                        Node item = values.item(i);
//                        root.appendChild(mergedDocument.importNode(item, true));
                        mergedValues.put(item.getNodeName(), item);
                    }
                } catch (SAXException e) {
                    throw new ResourcesMergingException("Sax: Failed to parse file: " + file.getAbsolutePath(), e);
                } catch (IOException e) {
                    throw new ResourcesMergingException("IO: Failed to parse file: " + file.getAbsolutePath(), e);
                }
            }
            for (Node value : mergedValues.values()) {
                root.appendChild(mergedDocument.importNode(value, true));
            }

            mergedDocument.normalizeDocument();
            return mergedDocument;
        } catch (ParserConfigurationException e) {
            throw new ResourcesMergingException("Failed to create parsing configuration", e);
        }

    }

}
