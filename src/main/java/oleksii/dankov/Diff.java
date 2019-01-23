package oleksii.dankov;

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
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class Diff {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        File original = new File(args[0]);
        File generated = new File(args[1]);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Map<String, Node> nodesOriginal = parseFile(original, dbFactory);
        Map<String, Node> nodesGenerated = parseFile(generated, dbFactory);
        System.out.println(nodesOriginal.values().size());
        System.out.println(nodesGenerated.values().size());

        HashSet<String> diff = new HashSet<>(nodesOriginal.keySet());
        diff.addAll(nodesGenerated.keySet());

        for (String key : diff) {
            if (nodesOriginal.get(key) == null) {
                System.out.println("NodesOriginal doesn't have value for: " + key);
                System.out.println("where nodes generated have value:  " + nodesGenerated.get(key) );

            }
        }

        System.out.println("=====");
        for (String key : diff) {
//            System.out.println("checking: " + key);
            if (nodesGenerated.get(key) == null) {
                System.out.println("NodesOriginal doesn't have value for: " + key);
                System.out.println("where nodes generated have value:  " + nodesOriginal.get(key));
            }
        }
    }

    private static Map<String, Node> parseFile(File original, DocumentBuilderFactory dbFactory) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();

        Document doc = dbBuilder.parse(original);
        Element rootElement = doc.getDocumentElement();
        rootElement.normalize();
        NodeList values = rootElement.getChildNodes();
        Map<String, Node> nodes = new TreeMap<>();
        for (int i = 0; i < values.getLength(); i++) {
            Node item = values.item(i);
            if(item.getNodeType() == Node.ELEMENT_NODE) {
                String key = item.getAttributes().getNamedItem("name").getNodeValue();
                nodes.put(key, item);
            }
        }
        return nodes;
    }
}
