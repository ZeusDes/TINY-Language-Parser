package com.tinylang;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.tinylang.utils.Exceptions.ParserException;
import com.tinylang.utils.Exceptions.ScannerException;
import com.tinylang.utils.Parser;
import com.tinylang.utils.ScannerDriver;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
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
    private Button parse;

    @FXML
    protected void compile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Output");
        outputFile = fileChooser.showSaveDialog(new Stage());
        try{
            boolean flag = ScannerDriver.scan(inputFile, outputFile);
            if(flag)
                status.setText("Compiled!");
        } catch (IOException e) {
            status.setText("Error: Cannot Open file");
        } catch (ScannerException e) {
            status.setText(e.getMessage());
        }
    }

    @FXML
    protected void loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open TINY File Code");
        inputFile = fileChooser.showOpenDialog(new Stage());
        compile.setDisable(false);
        parse.setDisable(false);
        status.setText("File Opened");
    }

    @FXML
    protected void parseController(){
        status.setText("Parsing...");
        try {
            Parser parser = new Parser(inputFile);
            String dot = parser.parse();
            System.out.println(dot);
            ImageView img = showParseTree(dot);
            /* Setting new window with Graph */
            Stage stage = new Stage();
            stage.setTitle("Syntax Tree");
            HBox hbox = new HBox(img);
            stage.setScene(new Scene(hbox));
            stage.show();
            status.setText("Parsed!");
        } catch (IOException e){
            status.setText("Error: Cannot Open file");
        } catch (ScannerException | ParserException e) {
            status.setText(e.getMessage());
        }
    }

    private ImageView showParseTree(String dot) throws IOException {
        MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
        BufferedImage image = Graphviz.fromGraph(g).width(1080).render(Format.PNG).toImage();

        WritableImage wr = null;
        wr = new WritableImage(image.getWidth(), image.getHeight());
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pw.setArgb(x, y, image.getRGB(x, y));
            }
        }
        ImageView imageView = new ImageView(wr);
        return imageView;
    }
}