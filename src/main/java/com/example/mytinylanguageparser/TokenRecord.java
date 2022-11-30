package com.example.mytinylanguageparser;

import lombok.Data;

@Data
public class TokenRecord {
    private String TokenType;
    private String TokenValue;

    public TokenRecord(String tokenValue, String tokenType) {
        this.TokenType = tokenType;
        this.TokenValue = tokenValue;
    }
    public String getTokenValue(){
        return TokenValue;
    }

    @Override
    public String toString() {
        return "(\"" +
                TokenValue +
                "\", " + TokenType +
                ')';
    }
}