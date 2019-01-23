package oleksii.dankov;

import oleksii.dankov.cli.ArgumentsHandler;
import oleksii.dankov.filters.ValuesFilter;
import oleksii.dankov.merger.ResourceMerger;
import oleksii.dankov.merger.ResourcesMergingException;
import oleksii.dankov.writer.DocumentWriter;
import oleksii.dankov.writer.DocumentWriterException;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;

import java.io.File;
import java.util.*;

public class App {
    private final ArgumentsHandler argumentsHandler;
    private final DocumentWriter documentWriter;
    private final ResourceMerger resourceMerger;

    public App(ArgumentsHandler argumentsHandler, DocumentWriter documentWriter, ResourceMerger resourceMerger) {
        this.argumentsHandler = argumentsHandler;
        this.documentWriter = documentWriter;
        this.resourceMerger = resourceMerger;
    }

    public void process() throws ResourcesMergingException, DocumentWriterException {
        if (argumentsHandler.allArgumentsPresent()) {
            Map<String, List<File>> filesByQualifier = getFilesToMerge(argumentsHandler);
            for (Map.Entry<String, List<File>> filesForQualifier : filesByQualifier.entrySet()) {
                Document mergedDocument = resourceMerger.mergeValues(filesForQualifier.getValue());
                documentWriter.save(mergedDocument, createFileToSave(argumentsHandler, filesForQualifier));
            }
        }
    }


    private File createFileToSave(ArgumentsHandler argumentsHandler, Map.Entry<String, List<File>> entry) {
        String outputDirPath = argumentsHandler.getOutputDirectory().getAbsolutePath();
        return new File(outputDirPath + "/" + entry.getKey() + "/values.xml");
    }

    private Map<String, List<File>> getFilesToMerge(ArgumentsHandler argumentsHandler) {
        File appResDirectory = argumentsHandler.getAppResDirectory();
        File[] libs = argumentsHandler.getLibsDirectory().listFiles();
        return getFilesToMerge(appResDirectory, libs);
    }

    private Map<String, List<File>> getFilesToMerge(File appResDirectory, File[] libs) {
        HashMap<String, List<File>> filesByQualifier = new HashMap<>();
        for (File lib :libs) {
            File res = new File(lib.getAbsolutePath() + "/res");
            if (res.exists()) {
                handleResDirectory(filesByQualifier, res);
            }
        }
        handleResDirectory(filesByQualifier, appResDirectory);
        return filesByQualifier;
    }

    private void handleResDirectory(HashMap<String, List<File>> valuesWithQualifiers, File res) {

        ValuesFilter valuesFilter = new ValuesFilter();
        File[] values = res.listFiles(valuesFilter);
        for (File valuesDir : values) {
            List<File> filesForQualifier = valuesWithQualifiers.get(valuesDir.getName());
            filesForQualifier = filesForQualifier == null ? new ArrayList<>() : filesForQualifier;
            File[] xmls = valuesDir.listFiles();
            if (xmls != null) {
                filesForQualifier.addAll(Arrays.asList(xmls));
            }
            valuesWithQualifiers.put(valuesDir.getName(), filesForQualifier);
        }
    }
}
