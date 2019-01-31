package oleksii.dankov.merger;

import org.w3c.dom.Document;

import java.util.List;

public interface ResourceMerger {
    /***
     * Receives document list and merges all values to single document
     * @param documents - document list that values from should be merged
     * @return document with merged values
     * @throws ResourcesMergingException
     */
    Document mergeValues(List<Document> documents) throws ResourcesMergingException;
}
