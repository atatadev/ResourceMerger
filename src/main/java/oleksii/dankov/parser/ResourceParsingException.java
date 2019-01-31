package oleksii.dankov.parser;

public class ResourceParsingException extends Exception{
    public ResourceParsingException(String reason, Exception e) {
        super(reason, e);
    }

}
