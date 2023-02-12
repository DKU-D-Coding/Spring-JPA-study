package com.cha.carrotApi.exception;

import java.io.IOException;

public class FileUploadFailureException extends RuntimeException{
    public FileUploadFailureException(IOException e) {
    }
}
