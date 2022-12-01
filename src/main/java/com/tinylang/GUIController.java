package com.tinylang;

import java.io.File;
import java.io.IOException;

import com.tinylang.utils.ScannerDriver;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Data;

@Data
public class GUIController {
    private File inputFile;
    private File outputFile;

    @FXML
    private Label status;
    @FXML
    private Button compile;

    @FXML
    protected void compile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Output");
        outputFile = fileChooser.showSaveDialog(new Stage());
        try{
            boolean flag = ScannerDriver.scan(inputFile, outputFile);
            if(flag)
                status.setText("Compiled!");
            else
                status.setText("Code has Error!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open TINY File Code");
        inputFile = fileChooser.showOpenDialog(new Stage());
        compile.setDisable(false);
        status.setText("File Opened");
    }
}