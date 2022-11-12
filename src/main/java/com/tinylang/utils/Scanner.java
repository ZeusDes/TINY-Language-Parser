package com.tinylang.utils;

import java.io.*;
import java.util.HashMap;

public class Scanner {
    private FileReader in;
    private STATE currState;
    private int ASCII;
    private final static HashMap<String, String> special_chars = new HashMap<>() {{
        special_chars.put(";", "SEMICOLON");
        special_chars.put(":=", "ASSIGN");
        special_chars.put("<", "LESSTHAN");
        special_chars.put("=", "EQUAL");
        special_chars.put("+", "PLUS");
        special_chars.put("-", "MINUS");
        special_chars.put("*", "MULT");
        special_chars.put("/", "DIV");
        special_chars.put("(", "OPENBRACKET");
        special_chars.put(")", "CLOSEDBRACKET");
    }};
    private final static HashMap<String, String> Reserved_keyword = new HashMap<>() {{
        Reserved_keyword.put("if", "IF");
        Reserved_keyword.put("then", "THEN");
        Reserved_keyword.put("end", "END");
        Reserved_keyword.put("repeat", "REPEAT");
        Reserved_keyword.put("until", "UNTIL");
        Reserved_keyword.put("read", "READ");
        Reserved_keyword.put("write", "WRITE");
    }};
    private enum STATE {
        START,  // Starting state
        IN_ID,  // Indicates reading an identifier
        IN_NUM, // Indicates reading a numeric character
        ERROR,  // Indictes error state
        DONE,
        EOF
    }

    public Scanner(String file_path){
        try{
            in = new FileReader(file_path);
            currState = STATE.START;
            ASCII = -1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    TokenRecord getToken() throws IOException {
        ASCII = in.read();
        char currChar;
        StringBuilder TokenVal = new StringBuilder();
        if(ASCII == -1) {
            currState = STATE.EOF;
            return null;
        }

        while(ASCII != -1 && currState != STATE.DONE) {
            currChar = (char)ASCII;
            TokenVal.append(currChar);

            ASCII = in.read();
        }

        String TokenType;
        if(Reserved_keyword.containsKey(TokenVal.toString()))
            TokenType = Reserved_keyword.get(TokenVal.toString());
        else if(special_chars.containsKey(TokenVal.toString()))
            TokenType = special_chars.get(TokenVal.toString());
        else if(Character.isDigit(TokenVal.charAt(0))){
            TokenType = "NUMBER";
        } else {
            TokenType = "IDENTIFIER";
        }
        return new TokenRecord(TokenType, TokenVal);
    }
}
