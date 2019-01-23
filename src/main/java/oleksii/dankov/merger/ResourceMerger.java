package oleksii.dankov.merger;

import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

public interface ResourceMerger {
    Document mergeValues(List<File> files) throws ResourcesMergingException;
}
