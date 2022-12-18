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
    TokenRecord tokenRecord;
    Boolean isTerminal;

    @Setter(AccessLevel.NONE)
    List<Node> children;

    public Node(TokenRecord tokenRecord, Boolean isTerminal) {
        this.tokenRecord = tokenRecord;
        this.isTerminal = isTerminal;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public void addChilds(Node... nodes) {
        Collections.addAll(children, nodes);
    }
}
