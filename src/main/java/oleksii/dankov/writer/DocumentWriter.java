package oleksii.dankov.writer;

import org.w3c.dom.Document;

import java.io.File;

public interface DocumentWriter {
    /***
     * saves document to file
     * @param document - document required to save
     * @param saveTo - file to save
     * @throws DocumentWriterException
     */
    void save(Document document, File saveTo) throws DocumentWriterException;
}
