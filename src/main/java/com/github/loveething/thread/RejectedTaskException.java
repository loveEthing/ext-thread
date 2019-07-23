package com.github.loveething.thread;

public class RejectedTaskException extends Exception{

    private static final long serialVersionUID = -1L;

    public RejectedTaskException() { }

    public RejectedTaskException(String message) {
        super(message);
    }

    public RejectedTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public RejectedTaskException(Throwable cause) {
        super(cause);
    }

}
