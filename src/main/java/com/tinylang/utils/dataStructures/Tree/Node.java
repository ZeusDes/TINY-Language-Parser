package com.tinylang.utils.dataStructures.Tree;

import com.tinylang.utils.TokenRecord;
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

    @Setter(AccessLevel.NONE)
    private List<Node> children;
    private Node sibling;

    public Node(String name, Shape shape) {
        this.name = name;
        this.shape = shape;
        this.children = new ArrayList<>();
        this.sibling = null;
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
    };

    public String toDot(int id) {
        String id_str = this.name + id;
        String dot = " " + id_str + "[label=\"" + this.name + "\"] ";
        if(this.sibling != null){
            dot += " " + "{ rank = same  " + this.name + " " + this.sibling.getName() + "  } ";
            dot += " " + this.name + " -> " + this.sibling.getName() + " ";
            dot += this.sibling.toDot(++id);
        }

        for (Node child :
                this.children) {
            dot += " " + this.name + " -> " + child.getName() + " ";
            dot += child.toDot(++id);
        }

        return dot;
    }
}
