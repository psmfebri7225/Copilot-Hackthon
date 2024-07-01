package com.myapp.exception;

public class NoNumberArrayException extends RuntimeException {
    
        public NoNumberArrayException(String message) {
            super(message);
        }

        public NoNumberArrayException(String message, Throwable cause) {
            super(message, cause);
        }

        public NoNumberArrayException(Throwable cause) {
            super(cause);
        }

        public NoNumberArrayException() {
            super();
        }

        private static final long serialVersionUID = 1L;
}
