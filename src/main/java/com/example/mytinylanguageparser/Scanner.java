package com.example.mytinylanguageparser;

import java.io.*;
import java.util.Map;

import static java.util.Map.entry;

public class Scanner {
    private java.util.Scanner in;
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

    public Scanner(String file_path){
        try{
            in = new java.util.Scanner(new File(file_path));
            currState = STATE.START;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNextChar(){
        if(in.hasNext())
            return true;
        return false;
    }

    TokenRecord getToken() throws IOException {
        if(currState == STATE.EOF || currState == STATE.ERROR) {
            return null;
        }
        StringBuilder TokenVal = new StringBuilder("");
        while(in.hasNext() && currState != STATE.DONE) {
            if(!isDelimiter) {
                in.useDelimiter("");
                currChar = in.next().charAt(0);
            }
            isDelimiter = false;
            if (currState != STATE.IN_ASSIGN && currState != STATE.IN_COMMENT && special_chars.containsKey(Character.toString(currChar)) && TokenVal.length() == 0) {
                currState = STATE.START;
                return new TokenRecord(Character.toString(currChar), special_chars.get(Character.toString(currChar)));
            } else if (currState != STATE.IN_ASSIGN && currState != STATE.IN_COMMENT && special_chars.containsKey(Character.toString(currChar))){
                isDelimiter = true;
                currState = STATE.DONE;
            }
            // ---------------------------------------------------------------
            else if (currChar == '{'){
                currState = STATE.IN_COMMENT;
            } else if (currChar == '}' && currState == STATE.IN_COMMENT) {
                currState = STATE.START;
            } else if (currState == STATE.IN_COMMENT || ((currChar == ' ' || currChar == '\n') && TokenVal.length() == 0)){
                /* DO NOTHING - SKIP */
            } else if ((currChar == ' ' || currChar == '\r'  || currChar == '\n') && !TokenVal.equals("")) {
                currState = STATE.DONE;
            } else if (currChar == ':') {
                TokenVal.append(currChar);
                currState = STATE.IN_ASSIGN;
            } else if (currChar != '=' && currState == STATE.IN_ASSIGN) {
                currState = STATE.ERROR;
                break;
            } else if(currChar == '=' && currState == STATE.IN_ASSIGN) {
                currState = STATE.START;
                TokenVal.append(currChar);
                return new TokenRecord(TokenVal.toString(), "ASSIGN");
            } else if(Character.isAlphabetic(currChar) && currState != STATE.IN_NUM) {
                TokenVal.append(currChar);
                currState = STATE.IN_ALPHA;
            } else if(Character.isDigit(currChar)) {
                TokenVal.append(currChar);
                if(currState != STATE.IN_ALPHA) currState = STATE.IN_NUM;
            } else {
                currState = STATE.ERROR;
                System.out.println("Error: Input code is incorrect");
                break;
            }
        }

        if(!in.hasNext()) {
            currState = STATE.EOF;
        } else if (currState != STATE.ERROR) {
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
