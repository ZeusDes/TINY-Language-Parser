package com.tinylang.utils;
import com.tinylang.utils.Exceptions.ParserException;
import com.tinylang.utils.Exceptions.ScannerException;

import java.io.IOException;

public class Parser {
    Scanner scanner;
    TokenRecord currToken;
    /* ParseTree tree; */
    public Parser(String file_path) throws ScannerException, IOException {
        scanner = new Scanner(file_path);
        currToken = scanner.getToken();
    }

    // FIXME: Handling Parser Exception in GUI
    boolean match(String target) throws ParserException, ScannerException, IOException {
        String tokenVal = currToken.getStringValue();
        if(!tokenVal.equals(target)) {
            throw new ParserException(tokenVal);
        }
        currToken = scanner.getToken();
        return true;
    }
}
