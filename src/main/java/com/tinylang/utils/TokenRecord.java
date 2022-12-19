package com.tinylang.utils;

import lombok.Getter;

public class TokenRecord {
    @Getter private final String TokenType;
    @Getter private final String StringValue;

    public TokenRecord(String StringValue, String tokenType) {
        this.TokenType = tokenType;
        this.StringValue = StringValue;
    }

    @Override
    public String toString() {
        return "(\"" +
                this.StringValue +
                "\", " + this.TokenType +
                ')';
    }
}