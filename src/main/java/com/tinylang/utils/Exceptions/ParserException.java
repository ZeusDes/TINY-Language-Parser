package com.tinylang.utils.Exceptions;

public class ParserException extends Exception{
    public ParserException(String tkn) {
        super("SyntaxError: Unexpected token (\"" + tkn + "\")");
    }
}
