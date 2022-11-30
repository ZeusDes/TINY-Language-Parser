package com.example.mytinylanguageparser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScannerDriver {
    public static void scan(File inputFile, File outputFile) throws IOException {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        Scanner myScanner = new Scanner(inputFile.getAbsolutePath());
        TokenRecord myTkn = myScanner.getToken();
        FileWriter myWriter = new FileWriter(outputFile);
        while (myScanner.hasNextChar()) {
            try {
                if(myTkn != null) {
                    myWriter.write(myTkn.toString() + '\n');
                    System.out.println(myTkn.toString());
                }
                myTkn = myScanner.getToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        myWriter.close();
    }
}