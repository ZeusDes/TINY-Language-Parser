package com.tinylang.utils.dataStructures.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
    private String name;
    private Shape shape;
    private int id;
    private static int count = 1;

    @Setter(AccessLevel.NONE)
    private List<Node> children;
    private Node sibling;

    public Node(String name, Shape shape) {
        this.name = name;
        this.shape = shape;
        this.children = new ArrayList<>();
        this.sibling = null;
        id = count++;
    }

    public void setSibling(Node node) {
        this.sibling = node;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void addChilds(Node... nodes) {
        Collections.addAll(children, nodes);
    }

    public enum Shape{
        RECTANGLE,
        CIRCLE
    }

    public String toDot() {
        String dotShape = "";
        if(this.shape == Shape.RECTANGLE){
            dotShape = ", shape=rect";
        }
        String dot = " " + id + "[label=\"" + this.name + "\" " + dotShape + "] ";
        if(this.sibling != null){
            dot += this.sibling.toDot();
            dot += " " + "{ rank = same  " + this.id + " " + this.sibling.getId() + "  } ";
            dot += " " + this.id + " -- " + this.sibling.getId() + " ";
        }

        for (Node child :
                this.children) {
            dot += " " + this.id + " -- " + child.getId() + " ";
            dot += child.toDot();
        }
        return dot;
    }
}
