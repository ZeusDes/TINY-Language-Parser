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
    boolean match(String target) throws ParserException, ScannerException {
        String tokenVal = currToken.getTokenType();
        if(!tokenVal.equals(target)) {
            throw new ParserException(tokenVal);
        }
        currToken = scanner.getToken();
        return true;
    }

    private MutableNode addop() throws ParserException, ScannerException {
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

    private MutableNode mulop() throws ParserException, ScannerException, IOException {
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

    private MutableNode comparison_op() throws ParserException, ScannerException {
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

    private MutableNode factor(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    private MutableNode term(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    private MutableNode statement(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    private MutableNode stmt_seq(){
        MutableNode node = null;
        /* Code*/
        return node;
    }

    private MutableNode simple_exp() throws ScannerException, ParserException {
        MutableNode node = term();
        String curType = currToken.getTokenType();
        while(curType.equals("PLUS") || curType.equals("MINUS")) {
            MutableNode newNode = addop();
            newNode.addLink(node); // left
            newNode.addLink(term()); // right
            node = newNode;
        }
        return node;
    }

    private MutableNode exp() throws ScannerException, ParserException {
        MutableNode node = simple_exp();
        String curType = currToken.getTokenType();
        if(curType.equals("LESSTHAN") || curType.equals("EQUAL")) {
            MutableNode newNode = comparison_op();
            newNode.addLink(node); // left
            newNode.addLink(simple_exp()); // right
            node = newNode;
        }
        return node;
    }

    private MutableNode if_stmt() throws ScannerException, ParserException {
        match("IF");
        MutableNode node = mutNode("IF").add(Shape.RECTANGLE);
        node.addLink(exp());
        match("THEN");
        node.addLink(stmt_seq());
        if(currToken.getTokenType().equals("ELSE")) {
            match("ELSE");
            node.addLink(stmt_seq());
        }
        match("END");
        return node;
    }


    private MutableNode repeat() throws ScannerException, ParserException {
        match("REPEAT");
        MutableNode node = mutNode("REPEAT").add(Shape.RECTANGLE);
        node.addLink(stmt_seq()); // left
        match("UNTIL");
        node.addLink(exp()); // right
        return node;
    }

    private MutableNode assign_stmt() throws ScannerException, ParserException {
        String curValue = currToken.getStringValue();
        match("IDENTIFIER");
        match("ASSIGN");
        String assignStr = "<p>ASSIGN</p>(" + curValue + ")";
        MutableNode node = mutNode(Label.html(assignStr)).add(Shape.RECTANGLE);
        node.addLink(exp());
        return node;
    }

    private MutableNode read_stmt() throws ScannerException, ParserException {
        match("READ");
        String curValue = currToken.getStringValue();
        match("IDENTIFIER");
        String readStr = "<p>READ</p>(" + curValue + ")";
        MutableNode node = mutNode(Label.html(readStr)).add(Shape.RECTANGLE);
        return node;
    }


    private MutableNode write_stmt() throws ScannerException, ParserException {
        match("WRITE");
        MutableNode node = mutNode("WRITE").add(Shape.RECTANGLE);
        node.addLink(exp());
        return node;
    }

}
