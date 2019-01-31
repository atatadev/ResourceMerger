package oleksii.dankov.merger;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResourceMergerImpl implements ResourceMerger {

    private final DocumentBuilderFactory dbFactory;

    public ResourceMergerImpl(DocumentBuilderFactory dbFactory) {

        this.dbFactory = dbFactory;
    }

    @Override
    public Document mergeValues(List<Document> documents) throws ResourcesMergingException {
        try {
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document mergedDocument = dbBuilder.newDocument();
            mergedDocument.setXmlStandalone(true);
            Element root = createRootElement(mergedDocument);
            mergedDocument.appendChild(root);

            Map<String, Node> mergedValues = new TreeMap<>();
            for (Document doc : documents) {
                extractValuesFromDocument(mergedValues, doc);
            }
            for (Node value : mergedValues.values()) {
                root.appendChild(mergedDocument.createTextNode("\n    "));
                root.appendChild(mergedDocument.importNode(value, true));
            }

            mergedDocument.normalizeDocument();
            return mergedDocument;
        } catch (ParserConfigurationException e) {
            throw new ResourcesMergingException("Failed to create document builder", e);
        }

    }

    private void extractValuesFromDocument(Map<String, Node> mergedValues, Document doc) {
        Element rootElement = doc.getDocumentElement();
        rootElement.normalize();
        NodeList values = rootElement.getChildNodes();
        for (int i = 0; i < values.getLength(); i++) {
            Node item = values.item(i);
            if(item.getNodeType() == Node.ELEMENT_NODE) {
                String key = item.getAttributes().getNamedItem("name").getNodeValue();
                mergedValues.put(key, item);
            }
        }
    }

    private Element createRootElement(Document mergedDocument) {
        Element root = mergedDocument.createElement("resources");
        Attr xmlns = mergedDocument.createAttribute("xmlns:ns1");
        xmlns.setValue("urn:oasis:names:tc:xliff:document:1.2");
        root.getAttributes().setNamedItemNS(xmlns);
        return root;
    }

}
