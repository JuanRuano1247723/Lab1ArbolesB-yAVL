public class ArbolAste {

    private NodeAste root;
    private int t; 

    // Constructor
    public ArbolAste(int t) {
        this.t = t;
        this.root = new NodeAste(2 * t - 1, true);
    }

    public void traverse() {
        if (root != null) {
            traverse(root);
        }
    }

    private void traverse(NodeAste node) {
        int i;
        for (i = 0; i < node.getNumKeys(); i++) {
            if (!node.isLeaf()) {
                traverse(node.getHijo(i));
            }
            System.out.print(" " + node.getId(i) + " | " + node.getValor(i));
        }

        if (!node.isLeaf()) {
            traverse(node.getHijo(i));
        }
    }

    public NodeAste searchB(int k) {
        return (root == null) ? null : search(root, k);
    }

    private NodeAste search(NodeAste node, int k) {
        int i = 0;
        while (i < node.getNumKeys() && k > node.getId(i)) {
            i++;
        }

        if (i < node.getNumKeys() && node.getId(i) == k) {
            return node;
        }

        if (node.isLeaf()) {
            return null;
        }

        return search(node.getHijo(i), k);
    }

    public void insert(int k, String valor) {
        NodeAste r = root;
        if (r.getNumKeys() == (2 * t - 1)) {
            NodeAste s = new NodeAste(2 * t - 1, false);
            root = s;
            s.setHijo(0, r);
            splitChild(s, 0, r);
            insertNonFull(s, k, valor);
        } else {
            insertNonFull(r, k, valor);
        }
    }

    private void insertNonFull(NodeAste node, int k, String valor) {
        int i = node.getNumKeys() - 1;

        if (node.isLeaf()) {
            while (i >= 0 && k < node.getId(i)) {
                node.setId(i + 1, node.getId(i));
                node.setValor(i + 1, node.getValor(i));
                i--;
            }
            node.setId(i + 1, k);
            node.setValor(i + 1, valor);
            node.setNumKeys(node.getNumKeys() + 1);
        } else {
            while (i >= 0 && k < node.getId(i)) {
                i--;
            }
            i++;
            if (node.getHijo(i).getNumKeys() == (2 * t - 1)) {
                splitChild(node, i, node.getHijo(i));
                if (k > node.getId(i)) {
                    i++;
                }
            }
            insertNonFull(node.getHijo(i), k, valor);
        }
    }

    private void splitChild(NodeAste node, int i, NodeAste y) {
        NodeAste z = new NodeAste(2 * t - 1, y.isLeaf());
        z.setNumKeys(t - 1);
        for (int j = 0; j < t - 1; j++) {
            z.setId(j, y.getId(j + t));
            z.setValor(j, y.getValor(j + t));
        }

        if (!y.isLeaf()) {
            for (int j = 0; j < t; j++) {
                z.setHijo(j, y.getHijo(j + t));
            }
        }

        y.setNumKeys(t - 1);

        for (int j = node.getNumKeys(); j >= i + 1; j--) {
            node.setHijo(j + 1, node.getHijo(j));
        }

        node.setHijo(i + 1, z);

        for (int j = node.getNumKeys() - 1; j >= i; j--) {
            node.setId(j + 1, node.getId(j));
            node.setValor(j + 1, node.getValor(j));
        }

        node.setId(i, y.getId(t - 1));
        node.setValor(i, y.getValor(t - 1));
        node.setNumKeys(node.getNumKeys() + 1);
    }

    public void deleteB(int k) {
        if (root == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        delete(root, k);

        if (root.getNumKeys() == 0) {
            if (!root.isLeaf()) {
                root = root.getHijo(0);
            } else {
                root = null;
            }
        }
    }

    private void delete(NodeAste node, int k) {
        int idx = findKey(node, k);

        if (idx < node.getNumKeys() && node.getId(idx) == k) {
            if (node.isLeaf()) {
                removeFromLeaf(node, idx);
            } else {
                removeFromNonLeaf(node, idx);
            }
        } else {
            if (node.isLeaf()) {
                System.out.println("La clave " + k + " no está en el árbol.");
                return;
            }

            boolean flag = (idx == node.getNumKeys());

            if (node.getHijo(idx).getNumKeys() < t) {
                fill(node, idx);
            }

            if (flag && idx > node.getNumKeys()) {
                delete(node.getHijo(idx - 1), k);
            } else {
                delete(node.getHijo(idx), k);
            }
        }
    }

    private int findKey(NodeAste node, int k) {
        int idx = 0;
        while (idx < node.getNumKeys() && node.getId(idx) < k) {
            ++idx;
        }
        return idx;
    }

    private void removeFromLeaf(NodeAste node, int idx) {
        for (int i = idx + 1; i < node.getNumKeys(); ++i) {
            node.setId(i - 1, node.getId(i));
            node.setValor(i - 1, node.getValor(i));
        }
        node.setNumKeys(node.getNumKeys() - 1);
    }

    private void removeFromNonLeaf(NodeAste node, int idx) {
        int k = node.getId(idx);

        if (node.getHijo(idx).getNumKeys() >= t) {
            NodeAste pred = getPredecessor(node, idx);
            node.setId(idx, pred.getId(pred.getNumKeys() - 1));
            node.setValor(idx, pred.getValor(pred.getNumKeys() - 1));
            delete(node.getHijo(idx), pred.getId(pred.getNumKeys() - 1));
        } else if (node.getHijo(idx + 1).getNumKeys() >= t) {
            NodeAste succ = getSuccessor(node, idx);
            node.setId(idx, succ.getId(0));
            node.setValor(idx, succ.getValor(0));
            delete(node.getHijo(idx + 1), succ.getId(0));
        } else {
            merge(node, idx);
            delete(node.getHijo(idx), k);
        }
    }

    private NodeAste getPredecessor(NodeAste node, int idx) {
        NodeAste cur = node.getHijo(idx);
        while (!cur.isLeaf()) {
            cur = cur.getHijo(cur.getNumKeys());
        }
        return cur;
    }

    private NodeAste getSuccessor(NodeAste node, int idx) {
        NodeAste cur = node.getHijo(idx + 1);
        while (!cur.isLeaf()) {
            cur = cur.getHijo(0);
        }
        return cur;
    }

    private void fill(NodeAste node, int idx) {
        if (idx != 0 && node.getHijo(idx - 1).getNumKeys() >= t) {
            borrowFromPrev(node, idx);
        } else if (idx != node.getNumKeys() && node.getHijo(idx + 1).getNumKeys() >= t) {
            borrowFromNext(node, idx);
        } else {
            if (idx != node.getNumKeys()) {
                merge(node, idx);
            } else {
                merge(node, idx - 1);
            }
        }
    }

    private void borrowFromPrev(NodeAste node, int idx) {
        NodeAste child = node.getHijo(idx);
        NodeAste sibling = node.getHijo(idx - 1);

        for (int i = child.getNumKeys() - 1; i >= 0; --i) {
            child.setId(i + 1, child.getId(i));
            child.setValor(i + 1, child.getValor(i));
        }

        if (!child.isLeaf()) {
            for (int i = child.getNumKeys(); i >= 0; --i) {
                child.setHijo(i + 1, child.getHijo(i));
            }
        }

        child.setId(0, node.getId(idx - 1));
        child.setValor(0, node.getValor(idx - 1));

        if (!node.isLeaf()) {
            child.setHijo(0, sibling.getHijo(sibling.getNumKeys()));
        }

        node.setId(idx - 1, sibling.getId(sibling.getNumKeys() - 1));
        node.setValor(idx - 1, sibling.getValor(sibling.getNumKeys() - 1));

        child.setNumKeys(child.getNumKeys() + 1);
        sibling.setNumKeys(sibling.getNumKeys() - 1);
    }

    private void borrowFromNext(NodeAste node, int idx) {
        NodeAste child = node.getHijo(idx);
        NodeAste sibling = node.getHijo(idx + 1);

        child.setId(child.getNumKeys(), node.getId(idx));
        child.setValor(child.getNumKeys(), node.getValor(idx));

        if (!child.isLeaf()) {
            child.setHijo(child.getNumKeys() + 1, sibling.getHijo(0));
        }

        node.setId(idx, sibling.getId(0));
        node.setValor(idx, sibling.getValor(0));

        for (int i = 1; i < sibling.getNumKeys(); ++i) {
            sibling.setId(i - 1, sibling.getId(i));
            sibling.setValor(i - 1, sibling.getValor(i));
        }

        if (!sibling.isLeaf()) {
            for (int i = 1; i <= sibling.getNumKeys(); ++i) {
                sibling.setHijo(i - 1, sibling.getHijo(i));
            }
        }

        child.setNumKeys(child.getNumKeys() + 1);
        sibling.setNumKeys(sibling.getNumKeys() - 1);
    }

    private void merge(NodeAste node, int idx) {
        NodeAste child = node.getHijo(idx);
        NodeAste sibling = node.getHijo(idx + 1);

        child.setId(t - 1, node.getId(idx));
        child.setValor(t - 1, node.getValor(idx));

        for (int i = 0; i < sibling.getNumKeys(); ++i) {
            child.setId(i + t, sibling.getId(i));
            child.setValor(i + t, sibling.getValor(i));
        }

        if (!child.isLeaf()) {
            for (int i = 0; i <= sibling.getNumKeys(); ++i) {
                child.setHijo(i + t, sibling.getHijo(i));
            }
        }

        for (int i = idx + 1; i < node.getNumKeys(); ++i) {
            node.setId(i - 1, node.getId(i));
            node.setValor(i - 1, node.getValor(i));
            node.setHijo(i, node.getHijo(i + 1));
        }

        child.setNumKeys(child.getNumKeys() + sibling.getNumKeys() + 1);
        node.setNumKeys(node.getNumKeys() - 1);
    }
}