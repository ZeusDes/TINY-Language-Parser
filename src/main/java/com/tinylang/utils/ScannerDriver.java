package com.tinylang.utils;

import java.io.FileWriter;
import java.io.IOException;

public class ScannerDriver {
    public static void main(String[] args) throws IOException {
        Scanner myScanner = new Scanner("/home/muhammad/Desktop/example.tiny");
        TokenRecord myTkn = myScanner.getToken();
        FileWriter myWriter = new FileWriter("/home/muhammad/Desktop/tinyOut.out");
        while (myTkn != null) {
            try {
                myWriter.write(myTkn.toString() + '\n');
                myTkn = myScanner.getToken();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        myWriter.close();
    }
}
