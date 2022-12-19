package com.tinylang.utils.dataStructures.Tree;

import com.tinylang.utils.TokenRecord;
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
}
