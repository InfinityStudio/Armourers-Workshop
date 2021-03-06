package net.skin43d.exception;

public class SkinSaveException extends Exception {
    
    private final SkinSaveExceptionType type;
    
    public SkinSaveException(String message, SkinSaveExceptionType type) {
        super(message);
        this.type = type;
    }
    
    public SkinSaveExceptionType getType() {
        return type;
    }
    
    public enum SkinSaveExceptionType {
        MARKER_ERROR,
        NO_DATA,
        MISSING_PARTS;
    }
}
