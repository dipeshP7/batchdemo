package com.batchexample.batchdemo.exception;

public class CustomBatchException extends RuntimeException{
    public CustomBatchException(String message, Throwable e){
        super(message, e);
    }

    public CustomBatchException(String message){
        super(message);
    }
}
