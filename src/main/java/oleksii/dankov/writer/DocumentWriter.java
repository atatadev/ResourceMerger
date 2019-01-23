package oleksii.dankov.writer;

import org.w3c.dom.Document;

import java.io.File;

public interface DocumentWriter {
    void save(Document document, File saveTo) throws DocumentWriterException;
}
