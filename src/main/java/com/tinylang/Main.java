package com.tinylang;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;
import static guru.nidi.graphviz.model.Factory.node;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        /*MutableNode nodeA = mutNode("a").add(Color.RED);
        MutableNode nodeB = mutNode("b").add(Color.RED);
        MutableNode nodeC = mutNode("c").add(Color.RED);
        MutableNode nodeD = mutNode("d").add(Color.RED);

        nodeA.addLink(nodeC);

        MutableGraph g1 = mutGraph("example1").setDirected(true).add(nodeA).add(nodeC);
//        g1.graphAttrs().add(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT));
        g1.graphAttrs().add(Rank.inSubgraph(Rank.RankType.SAME));

        MutableGraph g2 = mutGraph("example1").setDirected(true).add(nodeA).add(nodeB);

        MutableGraph g = mutGraph("example1").setCluster(true).setDirected(true).add(g1).add(g2);
        nodeA.addLink(nodeB);

//        g.graphAttrs().add(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT));


        Graph gtest = graph().with(
                graph("1").cluster().graphAttr().with(Label.of("#1")).with(node("a0").link("a1")),
                graph("2").cluster().graphAttr().with(Label.of("#2")).with(node("b0").link("b1"))
                        .graphAttr().with(Rank.dir(Rank.RankDir.LEFT_TO_RIGHT)),
                node("start").link("a0", "b0"));
        Graphviz.fromGraph(gtest).width(1000).render(Format.PNG).toFile(new File("example/ex1m.png"));
                */

        /*MutableNode node = mutNode("c");
        MutableGraph gg = mutGraph("example1").setDirected(false).graphAttrs().add(Rank.dir(Rank.RankDir.TOP_TO_BOTTOM)).add(
                node.addLink(mutNode("d")));
        MutableGraph g = mutGraph("example2").setDirected(false).graphAttrs().add(Rank.inSubgraph(Rank.RankType.SAME)).add(
                mutNode("a").add(Color.RED).addLink(mutNode("b")).addLink(gg));

        Graphviz.fromGraph(g).width(400).render(Format.PNG).toFile(new File("example/ex1m.png"));*/

        String dot = "digraph G { graph []  A1[label=\"Thaboot\"] A1 -> B1 { rank = same  A1 D1  } A1 -> D1 D1 -> E1 }";

        MutableGraph g = new Parser().read(dot);
        Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("example/ex1m.png"));



//        GUIApp.run(args);
    }
}
