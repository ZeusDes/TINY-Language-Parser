package com.tinylang.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScannerDriver {
    public static boolean scan(File inputFile, File outputFile) throws IOException {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        Scanner myScanner = new Scanner(inputFile.getAbsolutePath());
        TokenRecord myTkn = myScanner.getToken();
        FileWriter myWriter = new FileWriter(outputFile);
        while (myScanner.hasNextChar()) {
            try {
                if(myTkn != null) {
                    myWriter.write(myTkn.toString() + '\n');
                }
                myTkn = myScanner.getToken();
                if(myTkn.getTokenValue().equals("-1")){
                    myWriter.write("Error: Code has something wrong" + '\n');
                    return false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        myWriter.close();
        return true;
    }
}