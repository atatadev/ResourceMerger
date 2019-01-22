package oleksii.dankov.merger;

public class ResourcesMergingException extends Exception{
    public ResourcesMergingException(String reason, Exception e) {
        super(reason, e);
    }
}
