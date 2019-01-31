package oleksii.dankov.parser;

import oleksii.dankov.merger.ResourcesMergingException;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface DocumentsParser {
    /***
     * extracts by qualifiers (like values_en, values, values_ru) and parses documents from resources dirs
     * @param resourceDirs - resources directories to extract documents from, like library's '/res' or app's 'values' directories
     * @return - List of documents by qualifier
     * @throws ResourceParsingException
     */
    Map<String, List<Document>> parseDocuments(List<File> resourceDirs) throws ResourceParsingException;
}
