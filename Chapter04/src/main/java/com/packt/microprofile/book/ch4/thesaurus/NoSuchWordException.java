package com.packt.microprofile.book.ch4.thesaurus;

public class NoSuchWordException extends Exception {
    private static final long serialVersionUID = 7501251668604922363L;

    public NoSuchWordException() {
    }

    public NoSuchWordException(String message) {
        super(message);
    }

    public NoSuchWordException(Throwable cause) {
        super(cause);
    }

    public NoSuchWordException(String message, Throwable cause) {
        super(message, cause);
    }
}
