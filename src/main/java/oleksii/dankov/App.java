package oleksii.dankov;

import oleksii.dankov.cli.ArgumentsHandler;
import oleksii.dankov.merger.ResourceMerger;
import oleksii.dankov.merger.ResourcesMergingException;
import oleksii.dankov.parser.DocumentsParser;
import oleksii.dankov.parser.ResourceParsingException;
import oleksii.dankov.writer.DocumentWriter;
import oleksii.dankov.writer.DocumentWriterException;
import org.w3c.dom.Document;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    private final ArgumentsHandler argumentsHandler;
    private final DocumentWriter documentWriter;
    private final ResourceMerger resourceMerger;
    private final DocumentsParser documentsParser;

    public App(
            ArgumentsHandler argumentsHandler,
            DocumentsParser documentsParser, ResourceMerger resourceMerger, DocumentWriter documentWriter) {
        this.argumentsHandler = argumentsHandler;
        this.documentsParser = documentsParser;
        this.documentWriter = documentWriter;
        this.resourceMerger = resourceMerger;


    }

    public void process() throws ResourcesMergingException, DocumentWriterException, ResourceParsingException {
        if (argumentsHandler.allArgumentsPresent()) {
            Map<String, List<Document>> filesByQualifier = getDocumentsToMerge();
            for (Map.Entry<String, List<Document>> filesForQualifier : filesByQualifier.entrySet()) {
                Document mergedDocument = resourceMerger.mergeValues(filesForQualifier.getValue());
                documentWriter.save(mergedDocument, createFileToSave(filesForQualifier.getKey()));
            }
        }
    }


    private File createFileToSave(String entry) {
        String outputDirPath = argumentsHandler.getOutputDirectory().getAbsolutePath();
        return new File(outputDirPath + "/" + entry + "/values.xml");
    }

    private Map<String, List<Document>> getDocumentsToMerge() throws ResourceParsingException {
        File appResDirectory = argumentsHandler.getAppResDirectory();
        File[] libs = argumentsHandler.getLibsDirectory().listFiles();
        List<File> resourceDirs;
        if (libs == null || libs.length == 0) {
            resourceDirs = new ArrayList<>();
        } else  {
            resourceDirs = getLibsResourcesDirs(libs);
        }

        resourceDirs.add(appResDirectory);
        return documentsParser.parseDocuments(resourceDirs);
    }

    private List<File> getLibsResourcesDirs(File[] libs) {
        return Stream.of(libs)
                .map(lib -> new File(lib.getAbsolutePath() + "/res"))
                .filter(File::exists)
                .collect(Collectors.toList());
    }


}
