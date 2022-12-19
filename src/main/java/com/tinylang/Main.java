package com.tinylang;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String dot = "digraph G { graph []  A1[label=\"Thaboot\"] A1 -> B1 { rank = same  A1 D1  } A1 -> D1 D1 -> E1 }";

        MutableGraph g = new Parser().read(dot);
        Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex1m.png"));



//        GUIApp.run(args);
    }
}
