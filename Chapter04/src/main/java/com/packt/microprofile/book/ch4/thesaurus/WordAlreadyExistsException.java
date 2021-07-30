package com.packt.microprofile.book.ch4.thesaurus;

public class WordAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 769246682475142467L;

    public WordAlreadyExistsException() {
    }

    public WordAlreadyExistsException(String message) {
        super(message);
    }

    public WordAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public WordAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
