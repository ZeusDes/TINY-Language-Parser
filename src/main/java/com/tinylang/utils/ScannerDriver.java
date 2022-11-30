package com.tinylang.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScannerDriver {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Scanner_ myScanner = new Scanner_(sc.nextLine());
        TokenRecord myTkn = myScanner.getToken();
        FileWriter myWriter = new FileWriter(sc.nextLine());
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
        System.out.println("Press 'Enter' key to exit.");
       sc.nextLine();
    }
}
