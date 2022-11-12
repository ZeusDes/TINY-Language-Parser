package com.tinylang.utils;

import lombok.Data;

@Data
public class TokenRecord {
    private String TokenType;
    private StringBuilder TokenValue;
    private int TokenNumVal;

    public TokenRecord(String tokenType, StringBuilder tokenValue) {
        this.TokenType = tokenType;
        this.TokenValue = tokenValue;
    }

    @Override
    public String toString() {
        return "(" +
                TokenType + '\'' +
                ", " + TokenValue + '\'' +
                '(';
    }
}
