package oleksii.dankov.parser;

import oleksii.dankov.filters.ValuesFilter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentsParserImpl implements DocumentsParser {

    private final DocumentBuilderFactory dbFactory;

    public DocumentsParserImpl(DocumentBuilderFactory dbFactory) {
        this.dbFactory = dbFactory;
    }

    @Override
    public Map<String, List<Document>> parseDocuments(List<File> resourceDirs) throws ResourceParsingException {
        HashMap<String, List<Document>> filesByQualifier = new HashMap<>();
        for (File dir : resourceDirs) {
            handleResDirectory(filesByQualifier, dir);
        }
        return filesByQualifier;
    }

    private void handleResDirectory(Map<String, List<Document>> valuesForQualifiers, File res) throws ResourceParsingException {
        ValuesFilter valuesFilter = new ValuesFilter();
        File[] valuesDirs = res.listFiles(valuesFilter);
        if (valuesDirs == null || valuesDirs.length == 0) {
            System.out.println(res.getAbsolutePath() + " empty resource dir, skipping ");
            return;
        }
        for (File valuesDir : valuesDirs) {
            List<Document> filesForQualifier = getFilesForQualifier(valuesForQualifiers, valuesDir);
            List<Document> xmls = parseFiles(valuesDir.listFiles());
            if (xmls != null) {
                filesForQualifier.addAll(xmls);
            }
            valuesForQualifiers.put(valuesDir.getName(), filesForQualifier);
        }
    }

    private List<Document> getFilesForQualifier(Map<String, List<Document>> valuesWithQualifiers, File valuesDir) {
        List<Document> filesForQualifier = valuesWithQualifiers.get(valuesDir.getName());
        return filesForQualifier == null ? new ArrayList<>() : filesForQualifier;
    }

    private List<Document> parseFiles(File[] listFiles) throws ResourceParsingException {
        if (listFiles == null || listFiles.length == 0) {
            return null;
        }
        List<Document> documents = new ArrayList<>();
        try {
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            for (File file : listFiles) {
                try {
                    documents.add(dbBuilder.parse(file));
                } catch (SAXException e) {
                    throw new ResourceParsingException("Sax: Failed to parse file: " + file.getAbsolutePath(), e);
                } catch (IOException e) {
                    throw new ResourceParsingException("IO: Failed to parse file: " + file.getAbsolutePath(), e);
                }
            }

        } catch (ParserConfigurationException e) {
            throw new ResourceParsingException("Failed to create parse configuration", e);
        }
        return documents;
    }
}
