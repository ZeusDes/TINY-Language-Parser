package com.tinylang.utils;

import com.tinylang.utils.Exceptions.ScannerException;

import java.io.*;
import java.util.Map;

import static java.util.Map.entry;

public class Scanner {
    private final java.util.Scanner in;
    private STATE currState;
    char currChar = ' ';
    boolean isDelimiter = false;
    private final static Map<String, String> special_chars = Map.ofEntries(
            entry(";", "SEMICOLON"),
            entry(":=", "ASSIGN"),
            entry("<", "LESSTHAN"),
            entry("=", "EQUAL"),
            entry("+", "PLUS"),
            entry("-", "MINUS"),
            entry("*", "MULT"),
            entry("/", "DIV"),
            entry("(", "OPENBRACKET"),
            entry(")", "CLOSEDBRACKET")
    );
    private final static Map<String, String> Reserved_keyword = Map.ofEntries(
            entry("if", "IF"),
            entry("then", "THEN"),
            entry("end", "END"),
            entry("repeat", "REPEAT"),
            entry("until", "UNTIL"),
            entry("read", "READ"),
            entry("write", "WRITE")
    );
    private enum STATE {
        START,
        IN_ALPHA,
        IN_NUM,
        IN_ASSIGN,
        IN_COMMENT,
        ERROR,
        DONE,
        EOF
    }

    public Scanner(String file_path) throws FileNotFoundException {
        in = new java.util.Scanner(new File(file_path));
        currState = STATE.START;
    }

    public boolean hasNextChar(){
        return in.hasNext();
    }

    TokenRecord getToken() throws ScannerException {
        if(currState == STATE.EOF) {
            return null;
        }
        StringBuilder TokenVal = new StringBuilder();
        while(in.hasNext() && currState != STATE.DONE && currState != STATE.ERROR) {
            if (!isDelimiter) {
                in.useDelimiter("");
                currChar = in.next().charAt(0);
            }
            isDelimiter = false;

            if (currState == STATE.START) {
                if(special_chars.containsKey(Character.toString(currChar))){
                    return new TokenRecord(Character.toString(currChar), special_chars.get(Character.toString(currChar)));
                }
                if (Character.isDigit(currChar)) {
                    TokenVal.append(currChar);
                    currState = STATE.IN_NUM;
                } else if (Character.isAlphabetic(currChar)) {
                    TokenVal.append(currChar);
                    currState = STATE.IN_ALPHA;
                } else if (currChar == ':') {
                    TokenVal.append(currChar);
                    currState = STATE.IN_ASSIGN;
                } else if(currChar == '{') {
                    currState = STATE.IN_COMMENT;
                }
            } else if(currState == STATE.IN_COMMENT) {
                if(currChar == '}'){
                    currState = STATE.START;
                }
            } else if (currChar == ' ' || currChar == '\n' || currChar == '\r'){
                currState = STATE.DONE;
            } else if(currState != STATE.IN_ASSIGN && special_chars.containsKey(Character.toString(currChar))){
                isDelimiter = true;
                currState = STATE.DONE;
            } else if (currState == STATE.IN_ALPHA) {
                TokenVal.append(currChar);
            } else if (currState == STATE.IN_NUM) {
                if(Character.isDigit(currChar)){
                    TokenVal.append(currChar);
                } else {
                    TokenVal.append(currChar);
                    currState = STATE.ERROR;
                }
            } else if (currState == STATE.IN_ASSIGN) {
                if(currChar == '='){
                    TokenVal.append(currChar);
                    currState = STATE.START;
                    return new TokenRecord(TokenVal.toString(), "ASSIGN");
                } else {
                    TokenVal.append(currChar);
                    currState = STATE.ERROR;
                }
            } else {
                currState = STATE.ERROR;
            }
        }

        if(!in.hasNext()) {
            currState = STATE.EOF;
        }
        else if(currState == STATE.ERROR) {
            throw new ScannerException(TokenVal.toString());
        } else {
            currState = STATE.START;
        }

        String TokenType;
        if(TokenVal.length() == 0)
            return null;
        if(TokenVal.length() > 0 && Reserved_keyword.containsKey(TokenVal.toString()))
            TokenType = Reserved_keyword.get(TokenVal.toString());
        else if(TokenVal.length() > 0 && Character.isDigit(TokenVal.charAt(0))){
            TokenType = "NUMBER";
        } else {
            TokenType = "IDENTIFIER";
        }
        return new TokenRecord(TokenVal.toString(), TokenType);
    }
}
