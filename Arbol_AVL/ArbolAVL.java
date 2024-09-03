public class ArbolAVL {
    private NodeAVL root;

    public NodeAVL search(int id) {
        return searchNode(root, id);
    }

    private NodeAVL searchNode(NodeAVL node, int id) {
        if (node == null || node.getId() == id) {
            return node;
        }

        if (id < node.getId()) {
            return searchNode(node.getLeft(), id);
        } else {
            return searchNode(node.getRight(), id);
        }
    }

    public void insert(int id, String valor) {
        root = insertNode(root, id, valor);
    }

    private NodeAVL insertNode(NodeAVL node, int id, String valor) {
        if (node == null) {
            return new NodeAVL(id, valor);
        }

        if (id < node.getId()) {
            node.setLeft(insertNode(node.getLeft(), id, valor));
        } else if (id > node.getId()) {
            node.setRight(insertNode(node.getRight(), id, valor));
        } else {
            return node;
        }

        node.updateHeight();

        int balance = node.getBalance();

        if (balance > 1 && id < node.getLeft().getId()) {
            return node.rotateRight();
        }

        if (balance < -1 && id > node.getRight().getId()) {
            return node.rotateLeft();
        }

        if (balance > 1 && id > node.getLeft().getId()) {
            node.setLeft(node.getLeft().rotateLeft());
            return node.rotateRight();
        }

        if (balance < -1 && id < node.getRight().getId()) {
            node.setRight(node.getRight().rotateRight());
            return node.rotateLeft();
        }

        return node;
    }

    public void delete(int id) {
        root = deleteNode(root, id);
    }

    private NodeAVL deleteNode(NodeAVL node, int id) {
        if (node == null) {
            return node;
        }

        if (id < node.getId()) {
            node.setLeft(deleteNode(node.getLeft(), id));
        } else if (id > node.getId()) {
            node.setRight(deleteNode(node.getRight(), id));
        } else {
            if ((node.getLeft() == null) || (node.getRight() == null)) {
                NodeAVL temp = null;
                if (temp == node.getLeft()) {
                    temp = node.getRight();
                } else {
                    temp = node.getLeft();
                }

                if (temp == null) {
                    temp = node;
                    node = null;
                } else { 
                    node = temp;
                }
            } else {
                NodeAVL temp = minValueNode(node.getRight());

                node.setId(temp.getId());
                node.setValor(temp.getValor());
                node.setRight(deleteNode(node.getRight(), temp.getId()));
            }
        }

        if (node == null) {
            return node;
        }

        node.updateHeight();

        int balance = node.getBalance();

        if (balance > 1 && node.getLeft().getBalance() >= 0) {
            return node.rotateRight();
        }

        if (balance < -1 && node.getRight().getBalance() <= 0) {
            return node.rotateLeft();
        }

        if (balance > 1 && node.getLeft().getBalance() < 0) {
            node.setLeft(node.getLeft().rotateLeft());
            return node.rotateRight();
        }

        if (balance < -1 && node.getRight().getBalance() > 0) {
            node.setRight(node.getRight().rotateRight());
            return node.rotateLeft();
        }

        return node;
    }

    private NodeAVL minValueNode(NodeAVL node) {
        NodeAVL current = node;

        while (current.getLeft() != null) {
            current = current.getLeft();
        }

        return current;
    }
}
