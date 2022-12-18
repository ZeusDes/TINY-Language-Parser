package com.tinylang.utils;
import com.tinylang.utils.Exceptions.ParserException;
import com.tinylang.utils.Exceptions.ScannerException;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.model.MutableNode;

import java.io.IOException;

import static guru.nidi.graphviz.model.Factory.mutNode;

public class Parser {
    Scanner scanner;
    TokenRecord currToken;
    public Parser(String file_path) throws ScannerException, IOException {
        scanner = new Scanner(file_path);
        currToken = scanner.getToken();
    }

    // FIXME: Handling Parser Exception in GUI
    boolean match(String target) throws ParserException, ScannerException, IOException {
        String tokenVal = currToken.getTokenType();
        if(!tokenVal.equals(target)) {
            throw new ParserException(tokenVal);
        }
        currToken = scanner.getToken();
        return true;
    }

    MutableNode addop() throws ParserException, ScannerException, IOException {
        MutableNode node;
        String lbl = "<b>OPERATOR</b></br>";
        if(currToken.getTokenType().equals("PLUS")){
            node = mutNode("+").add(Label.html(lbl + "(+)"));
            match("PLUS");
        } else if(currToken.getTokenType().equals("MINUS")){
            node = mutNode("-").add(Label.html(lbl + "(-)"));
            match("MINUS");
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    MutableNode mulop() throws ParserException, ScannerException, IOException {
        MutableNode node;
        String lbl = "<b>OPERATOR</b></br>";
        if(currToken.getTokenType().equals("MULT")){
            node = mutNode("*").add(Label.html(lbl + "(*)"));
            match("MULT");
        } else if(currToken.getTokenType().equals("/")){
            node = mutNode("DIV").add(Label.html(lbl + "(/)"));
            match("DIV");
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    MutableNode comparison_op() throws ParserException, ScannerException, IOException {
        MutableNode node;
        String lbl = "<b>OPERATOR</b></br>";
        if(currToken.getTokenType().equals("LESSTHAN")){
            node = mutNode("<").add(Label.html(lbl + "(<)"));
            match("LESSTHAN");
        } else if(currToken.getTokenType().equals("EQUAL")){
            node = mutNode("=").add(Label.html(lbl + "(=)"));
            match("EQUAL");
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    MutableNode factor(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    MutableNode term(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    MutableNode statement(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    MutableNode stmt_seq(){
        MutableNode node = null;
        /* Code*/
        return node;
    }

}
