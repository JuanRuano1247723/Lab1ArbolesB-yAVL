   public class ArbolBM {
    private NodeB root;
    private int t; 

    // Constructor
    public ArbolBM(int t) {
        this.t = t;
        this.root = new NodeB(2 * t - 1, true);
    }

    public void deleteB(int k) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        delete(root, k);

        if (root.getNumKeys() == 0) {
            if (root.hoja()) {
                root = null;
            } else {
                root = root.getHijo(0);
            }
        }
    }

    private void delete(NodeB node, int k) {
        int idx = findKey(node, k);

        if (idx < node.getNumKeys() && node.getId(idx) == k) {
            if (node.hoja()) {
                removeFromLeaf(node, idx);
            } else {
                removeFromNonLeaf(node, idx);
            }
        } else {
            if (node.hoja()) {
                System.out.println("The key " + k + " does not exist in the tree.");
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

    private int findKey(NodeB node, int k) {
        int idx = 0;
        while (idx < node.getNumKeys() && node.getId(idx) < k) {
            idx++;
        }
        return idx;
    }

    private void removeFromLeaf(NodeB node, int idx) {
        for (int i = idx + 1; i < node.getNumKeys(); ++i) {
            node.setId(i - 1, node.getId(i));
            node.setValor(i - 1, node.getValor(i));
        }
        node.setNumKeys(node.getNumKeys() - 1);
    }

    private void removeFromNonLeaf(NodeB node, int idx) {
        int k = node.getId(idx);

        if (node.getHijo(idx).getNumKeys() >= t) {
            int pred = getFather(node, idx);
            node.setId(idx, pred);
            delete(node.getHijo(idx), pred);
        } else if (node.getHijo(idx + 1).getNumKeys() >= t) {
            int succ = getChild(node, idx);
            node.setId(idx, succ);
            delete(node.getHijo(idx + 1), succ);
        } else {
            merge(node, idx);
            delete(node.getHijo(idx), k);
        }
    }

    private int getFather(NodeB node, int idx) {
        NodeB cur = node.getHijo(idx);
        while (!cur.hoja()) {
            cur = cur.getHijo(cur.getNumKeys());
        }
        return cur.getId(cur.getNumKeys() - 1);
    }

    private int getChild(NodeB node, int idx) {
        NodeB cur = node.getHijo(idx + 1);
        while (!cur.hoja()) {
            cur = cur.getHijo(0);
        }
        return cur.getId(0);
    }

    private void fill(NodeB node, int idx) {
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

    private void borrowFromPrev(NodeB node, int idx) {
        NodeB child = node.getHijo(idx);
        NodeB sibling = node.getHijo(idx - 1);

        for (int i = child.getNumKeys() - 1; i >= 0; --i) {
            child.setId(i + 1, child.getId(i));
            child.setValor(i + 1, child.getValor(i));
        }

        if (!child.hoja()) {
            for (int i = child.getNumKeys(); i >= 0; --i) {
                child.setHijo(i + 1, child.getHijo(i));
            }
        }

        child.setId(0, node.getId(idx - 1));
        child.setValor(0, node.getValor(idx - 1));

        if (!node.hoja()) {
            child.setHijo(0, sibling.getHijo(sibling.getNumKeys()));
        }

        node.setId(idx - 1, sibling.getId(sibling.getNumKeys() - 1));
        node.setValor(idx - 1, sibling.getValor(sibling.getNumKeys() - 1));

        child.setNumKeys(child.getNumKeys() + 1);
        sibling.setNumKeys(sibling.getNumKeys() - 1);
    }

    private void borrowFromNext(NodeB node, int idx) {
        NodeB child = node.getHijo(idx);
        NodeB sibling = node.getHijo(idx + 1);

        child.setId(child.getNumKeys(), node.getId(idx));
        child.setValor(child.getNumKeys(), node.getValor(idx));

        if (!child.hoja()) {
            child.setHijo(child.getNumKeys() + 1, sibling.getHijo(0));
        }

        node.setId(idx, sibling.getId(0));
        node.setValor(idx, sibling.getValor(0));

        for (int i = 1; i < sibling.getNumKeys(); ++i) {
            sibling.setId(i - 1, sibling.getId(i));
            sibling.setValor(i - 1, sibling.getValor(i));
        }

        if (!sibling.hoja()) {
            for (int i = 1; i <= sibling.getNumKeys(); ++i) {
                sibling.setHijo(i - 1, sibling.getHijo(i));
            }
        }

        child.setNumKeys(child.getNumKeys() + 1);
        sibling.setNumKeys(sibling.getNumKeys() - 1);
    }

    private void merge(NodeB node, int idx) {
        NodeB child = node.getHijo(idx);
        NodeB sibling = node.getHijo(idx + 1);

        child.setId(t - 1, node.getId(idx));
        child.setValor(t - 1, node.getValor(idx));

        for (int i = 0; i < sibling.getNumKeys(); ++i) {
            child.setId(i + t, sibling.getId(i));
            child.setValor(i + t, sibling.getValor(i));
        }

        if (!child.hoja()) {
            for (int i = 0; i <= sibling.getNumKeys(); ++i) {
                child.setHijo(i + t, sibling.getHijo(i));
            }
        }

        for (int i = idx + 1; i < node.getNumKeys(); ++i) {
            node.setId(i - 1, node.getId(i));
            node.setValor(i - 1, node.getValor(i));
        }

        for (int i = idx + 2; i <= node.getNumKeys(); ++i) {
            node.setHijo(i - 1, node.getHijo(i));
        }

        child.setNumKeys(child.getNumKeys() + sibling.getNumKeys() + 1);
        node.setNumKeys(node.getNumKeys() - 1);
    }

    public void traverseB() {
        if (root != null) {
            traverse(root);
        }
    }

    private void traverse(NodeB node) {
        int i;
        for (i = 0; i < node.getNumKeys(); i++) {
            if (!node.hoja()) {
                traverse(node.getHijo(i));
            }
            System.out.print(" " + node.getId(i) + " | " + node.getValor(i));
        }

        if (!node.hoja()) {
            traverse(node.getHijo(i));
        }
    }

    public NodeB searchB(int k) {
        return (root == null) ? null : search(root, k);
    }

    private NodeB search(NodeB node, int k) {
        int i = 0;
        while (i < node.getNumKeys() && k > node.getId(i)) {
            i++;
        }

        if (i < node.getNumKeys() && node.getId(i) == k) {
            return node;
        }

        if (node.hoja()) {
            return null;
        }

        return search(node.getHijo(i), k);
    }

    public void insert(int k, String valor) {
        NodeB r = root;
        if (r.getNumKeys() == (2 * t - 1)) {
            NodeB s = new NodeB(2 * t - 1, false);
            root = s;
            s.setHijo(0, r);
            splitChild(s, 0, r);
            insertNonFull(s, k, valor);
        } else {
            insertNonFull(r, k, valor);
        }
    }

    private void insertNonFull(NodeB node, int k, String valor) {
        int i = node.getNumKeys() - 1;

        if (node.hoja()) {
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

    private void splitChild(NodeB node, int i, NodeB y) {
        NodeB z = new NodeB(2 * t - 1, y.hoja());
        z.setNumKeys(t - 1);
        for (int j = 0; j < t - 1; j++) {
            z.setId(j, y.getId(j + t));
            z.setValor(j, y.getValor(j + t));
        }

        if (!y.hoja()) {
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
} 
    