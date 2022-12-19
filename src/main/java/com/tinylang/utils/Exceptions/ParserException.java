package com.tinylang.utils.Exceptions;

public class ParserException extends Exception{
    public ParserException(String tkn) {
        super("SyntaxError: Unexpected token (\"" + tkn + "\")");
    }

    public ParserException(String tkn, String expectedTkn) {
        super("SyntaxError: Unexpected token (\"" + tkn + "\")" + ", Expected token (\""+ expectedTkn + "\")");
    }
}
