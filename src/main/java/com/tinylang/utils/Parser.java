package com.tinylang.utils;
import com.tinylang.utils.Exceptions.ParserException;
import com.tinylang.utils.Exceptions.ScannerException;
import com.tinylang.utils.dataStructures.Tree.Node;

import java.io.File;
import java.io.IOException;

public class Parser {
    Scanner scanner;
    TokenRecord currToken;
    public Parser(File input) throws ScannerException, IOException {
        scanner = new Scanner(input);
        currToken = scanner.getToken();
    }

    public String parse() throws ScannerException, ParserException {
        String dot = "graph G { graph []\n ordering=\"out\"" + stmt_seq().toDot() + "}";
        System.out.println(dot);
        return dot;
    }

    private void match(String target) throws ParserException, ScannerException {
        String tokenVal = currToken.getTokenType();
        if(!tokenVal.equals(target)) {
            throw new ParserException(tokenVal, target);
        }
        currToken = scanner.getToken();
    }

    private Node addop() throws ParserException, ScannerException {
        Node node;
        String label = "OPERATOR\\n(";
        String tk_type = currToken.getTokenType();
        if (tk_type.equals("PLUS")) {
            node = new Node(label + "+)", Node.Shape.CIRCLE);
            match("PLUS");
        } else if(tk_type.equals("MINUS")) {
            node = new Node(label + "-)", Node.Shape.CIRCLE);
            match("MINUS");
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    private Node mulop() throws ParserException, ScannerException {
        Node node;
        String label = "OPERATOR\\n(";
        String tk_type = currToken.getTokenType();
        if (tk_type.equals("MULT")) {
            node = new Node(label + "*)", Node.Shape.CIRCLE);
            match("MULT");
        } else if(tk_type.equals("DIV")) {
            node = new Node(label + "/)", Node.Shape.CIRCLE);
            match("DIV");
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    private Node comparison_op() throws ParserException, ScannerException {
        Node node;
        String label = "OPERATOR\\n(";
        String tk_type = currToken.getTokenType();
        if (tk_type.equals("LESSTHAN")) {
            node = new Node(label + "<)", Node.Shape.CIRCLE);
            match("LESSTHAN");
        } else if(tk_type.equals("EQUAL")) {
            node = new Node(label + "=)", Node.Shape.CIRCLE);
            match("EQUAL");
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    private Node factor() throws ScannerException, ParserException {
        Node node;
        if(currToken.getTokenType().equals("OPENBRACKET")){
            match("OPENBRACKET");
            node = exp();
            match("CLOSEDBRACKET");
        } else if(currToken.getTokenType().equals("IDENTIFIER")){
            node = new Node("IDENTIFIER\\n(" + currToken.getStringValue() + ")" , Node.Shape.CIRCLE);
            match(currToken.getTokenType());
        } else if(currToken.getTokenType().equals("NUMBER")) {
            node = new Node("CONSTANT\\n(" + currToken.getStringValue() + ")", Node.Shape.CIRCLE);
            match(currToken.getTokenType());
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    private Node term() throws ScannerException, ParserException {
        Node node = factor();
        Node currNode;
        String tk_type = currToken.getTokenType();
        while(tk_type.equals("MULT") || tk_type.equals("DIV")){
            currNode = mulop();
            currNode.addChild(node);
            node = currNode;
            node.addChild(factor());
            tk_type = currToken.getTokenType();
        }
        return node;
    }

    private Node statement() throws ScannerException, ParserException {
        Node node;
        if(currToken.getTokenType().equals("IF")) {
            node = if_stmt();
        } else if (currToken.getTokenType().equals("REPEAT")) {
            node = repeat();
        } else if (currToken.getTokenType().equals("IDENTIFIER")) {
            node = assign_stmt();
        } else if (currToken.getTokenType().equals("READ")) {
            node = read_stmt();
        } else if (currToken.getTokenType().equals("WRITE")) {
            node = write_stmt();
        } else {
            throw new ParserException(currToken.getStringValue());
        }
        return node;
    }

    private Node stmt_seq() throws ScannerException, ParserException {
        Node oldNode = statement();
        Node node = oldNode;
        while(currToken != null && currToken.getTokenType().equals("SEMICOLON")){
            match("SEMICOLON");
            Node currNode = statement();
            node.setSibling(currNode);
            node = currNode;
        }
        return oldNode;
    }

    private Node simple_exp() throws ScannerException, ParserException {
        Node node = term();
        String curType = currToken.getTokenType();
        while(curType.equals("PLUS") || curType.equals("MINUS")) {
            Node newNode = addop();
            newNode.addChilds(node, term());
            node = newNode;
            curType = currToken.getTokenType();
        }
        return node;
    }

    private Node exp() throws ScannerException, ParserException {
        Node node = simple_exp();
        String curType = currToken.getTokenType();
        if(curType.equals("LESSTHAN") || curType.equals("EQUAL")) {
            Node newNode = comparison_op();
            newNode.addChilds(node, simple_exp());
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
        String assignStr = "ASSIGN\\n(" + curValue + ")";
        Node node = new Node(assignStr, Node.Shape.RECTANGLE);
        node.addChild(exp());
        return node;
    }

    private Node read_stmt() throws ScannerException, ParserException {
        match("READ");
        String curValue = currToken.getStringValue();
        match("IDENTIFIER");
        String readStr = "READ\\n(" + curValue + ")";
        return new Node(readStr, Node.Shape.RECTANGLE);
    }


    private Node write_stmt() throws ScannerException, ParserException {
        match("WRITE");
        Node node = new Node("WRITE", Node.Shape.RECTANGLE);
        node.addChild(exp());
        return node;
    }

}
