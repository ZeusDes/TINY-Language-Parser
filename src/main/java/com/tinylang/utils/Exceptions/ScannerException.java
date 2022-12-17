package com.tinylang.utils.Exceptions;

public class ScannerException extends Exception{
    public ScannerException(String tkn){
        super("SyntaxError: Undefined Token (\"" + tkn + "\")");
    }

}
