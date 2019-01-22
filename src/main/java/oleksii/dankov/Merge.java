package oleksii.dankov;

import oleksii.dankov.cli.ArgumentsHandler;
import oleksii.dankov.cli.CliArgumentHandler;
import oleksii.dankov.filters.ValuesFilter;
import oleksii.dankov.merger.ResourceMerger;
import oleksii.dankov.merger.ResourcesMergingException;
import org.apache.commons.cli.*;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class Merge {

    public static void main(String[] args) throws ResourcesMergingException, ParseException, TransformerException {
        ArgumentsHandler argumentsHandler = CliArgumentHandler.fromArgs(args);

        if (argumentsHandler.allArgumentsPresent()) {
            ResourceMerger resourceMerger = new ResourceMerger();
            File libsDirectory = argumentsHandler.getLibsDirectory();
            File appResDirectory = argumentsHandler.getAppResDirectory();
            File[] libs =  Objects.requireNonNull(libsDirectory.listFiles());
            HashMap<String, List<File>> filesByQualifier = new HashMap<>();
            for (File lib :libs) {
                File res = new File(lib.getAbsolutePath() + "/res");
                if (res.exists()) {
                    handleResDirectory(filesByQualifier, res);
                }

            }
            handleResDirectory(filesByQualifier, appResDirectory);
            for (Map.Entry<String, List<File>> entry : filesByQualifier.entrySet()) {
                Document mergedDocument = resourceMerger.mergeValues(entry.getValue());
                String outputDirPath = argumentsHandler.getOutputDirectory().getAbsolutePath();
                File saveTo = new File(outputDirPath + "/" + entry.getKey() + "/values.xml");
                save(mergedDocument, saveTo);

            }
        }



    }

    private static void handleResDirectory(HashMap<String, List<File>> filesByQualifier, File res) {
        ValuesFilter valuesFilter = new ValuesFilter();

        File[] values = res.listFiles(valuesFilter);
        for (File valuesDir : values) {
            List<File> filesForQualifier = filesByQualifier.get(valuesDir.getName());
            filesForQualifier = filesForQualifier == null ? new ArrayList<>() : filesForQualifier;
            File[] xmls = valuesDir.listFiles();
            if (xmls != null) {
                filesForQualifier.addAll(Arrays.asList(xmls));
            }
            filesByQualifier.put(valuesDir.getName(), filesForQualifier);
        }
    }

    private static void save(Document document, File saveTo) throws TransformerException {
        saveTo.getParentFile().mkdirs();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(saveTo);
        transformer.transform(source, result);
    }
}
