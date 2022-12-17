package com.tinylang.utils;

import lombok.Getter;

public class TokenRecord {
    @Getter private String TokenType;
    @Getter private String StringValue;
    @Getter private int NumValue;

    public TokenRecord(String StringValue, String tokenType) {
        this.TokenType = tokenType;
        this.StringValue = StringValue;
    }

    public TokenRecord(int NumValue, String tokenType){
        this.TokenType = tokenType;
        this.NumValue = NumValue;
    }

    @Override
    public String toString() {
        if(StringValue != null) {
            return "(\"" +
                    StringValue +
                    "\", " + TokenType +
                    ')';
        } else {
            return "(" + NumValue +
                    ", " + TokenType +
                    ')';
        }
    }
}