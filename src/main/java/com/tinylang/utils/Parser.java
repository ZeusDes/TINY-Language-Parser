package com.tinylang.utils;
import com.tinylang.utils.Exceptions.ParserException;
import com.tinylang.utils.Exceptions.ScannerException;
import com.tinylang.utils.dataStructures.Tree.Node;
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

    private Node addop() throws ParserException, ScannerException {
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

    private Node mulop() throws ParserException, ScannerException, IOException {
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

    private Node comparison_op() throws ParserException, ScannerException {
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

    private Node factor(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    private Node term(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    private Node statement(){
        MutableNode node = null;
        /* Code */
        return node;
    }

    private Node stmt_seq(){
        MutableNode node = null;
        /* Code*/
        return node;
    }

    private Node simple_exp() throws ScannerException, ParserException {
        Node node = term();
        String curType = currToken.getTokenType();
        while(curType.equals("PLUS") || curType.equals("MINUS")) {
            Node newNode = addop();
            newNode.addChild(node); // left
            newNode.addChild(term()); // right
            node = newNode;
        }
        return node;
    }

    private Node exp() throws ScannerException, ParserException {
        Node node = simple_exp();
        String curType = currToken.getTokenType();
        if(curType.equals("LESSTHAN") || curType.equals("EQUAL")) {
            Node newNode = comparison_op();
            newNode.addChild(node); // left
            newNode.addChild(simple_exp()); // right
            node = newNode;
        }
        return node;
    }

    private Node if_stmt() throws ScannerException, ParserException {
        match("IF");
        Node node = new Node("IF", Node.Shape.RECTANGLE);
        node.addChild(exp());
        match("THEN");
        node.addChild(stmt_seq());
        if(currToken.getTokenType().equals("ELSE")) {
            match("ELSE");
            node.addChild(stmt_seq());
        }
        match("END");
        return node;
    }


    private Node repeat() throws ScannerException, ParserException {
        match("REPEAT");
        Node node = new Node("REPEAT", Node.Shape.RECTANGLE);
        node.addChild(stmt_seq()); // left
        match("UNTIL");
        node.addChild(exp()); // right
        return node;
    }

    private Node assign_stmt() throws ScannerException, ParserException {
        String curValue = currToken.getStringValue();
        match("IDENTIFIER");
        match("ASSIGN");
        String assignStr = "ASSIGN<BR />(" + curValue + ")";
        Node node = new Node(assignStr, Node.Shape.RECTANGLE);
        node.addChild(exp());
        return node;
    }

    private Node read_stmt() throws ScannerException, ParserException {
        match("READ");
        String curValue = currToken.getStringValue();
        match("IDENTIFIER");
        String readStr = "READ<BR />(" + curValue + ")";
        Node node = new Node(readStr, Node.Shape.RECTANGLE);
        return node;
    }


    private Node write_stmt() throws ScannerException, ParserException {
        match("WRITE");
        Node node = new Node("WRITE", Node.Shape.RECTANGLE);
        node.addChild(exp());
        return node;
    }

}
