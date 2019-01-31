package oleksii.dankov.writer;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DocumentWriterImpl implements DocumentWriter {
    @Override
    public void save(Document document, File saveTo) throws DocumentWriterException {
        try {
            saveTo.getParentFile().mkdirs();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(saveTo);
            transformer.transform(source, result);

        } catch (TransformerException e) {
            throw new DocumentWriterException("Issue with writing document", e);
        }

    }
}
