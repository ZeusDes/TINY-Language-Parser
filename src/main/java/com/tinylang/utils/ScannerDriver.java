package com.tinylang.utils;

import com.tinylang.utils.Exceptions.ScannerException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScannerDriver {
    public static boolean scan(File inputFile, File outputFile) throws IOException, ScannerException {
        Scanner myScanner = new Scanner(inputFile.getAbsolutePath());
        TokenRecord myTkn = myScanner.getToken();
        FileWriter myWriter = new FileWriter(outputFile);
        try {
            while (myScanner.hasNextChar()) {
                if (myTkn != null) {
                    myWriter.write(myTkn.toString() + '\n');
                }
                myWriter.flush();
                myTkn = myScanner.getToken();
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        } catch (ScannerException e){
            myWriter.flush();
            throw e;
        } finally {
            myWriter.close();
        }
        return true;
    }
}
