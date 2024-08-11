public class ArbolB {
    private Node root;
    private int t;

    // Constructor
    public ArbolB(int t) {
        this.t = t;
        this.root = new Node(2 * t - 1, true);
    }

    public Node searchB(int num) {
        Node temp = search(root, num);

        if (temp == null) {
            System.out.println("No se ha encontrado un nodo con el valor ingresado");
            return temp;
        } else {
            return temp;
        }
    }

    private Node search(Node actual, int key) {
        int i = 0; 

        while (i < actual.getNumKeys() && key > actual.getId(i)) {
            i++;
        }

        if (i < actual.getNumKeys() && key == actual.getId(i)) {
            return actual;
        }

        if (actual.hoja()) {
            return null;
        } else {
            return search(actual.getHijo(i), key);
        }
    }

    public void insert(int key, String valor) {
        Node r = root;

        if (r.getNumKeys() == ((2 * t) - 1)) {
            Node s = new Node(2 * t - 1, false);
            root = s;
            s.setHijo(0, r);
            split(s, 0, r);
            insertNonFull(s, key, valor);
        } else {
            insertNonFull(r, key, valor);
        }
    }

    private void split(Node x, int i, Node y) {
        Node z = new Node(2 * t - 1, y.hoja());
        z.setNumKeys(t - 1);

        for (int j = 0; j < (t - 1); j++) {
            z.setId(j, y.getId(j + t));
            z.setValor(j, y.getValor(j + t));
        }

        if (!y.hoja()) {
            for (int k = 0; k < t; k++) {
                z.setHijo(k, y.getHijo(k + t));
            }
        }

        y.setNumKeys(t - 1);

        for (int j = x.getNumKeys(); j >= i + 1; j--) {
            x.setHijo(j + 1, x.getHijo(j));
        }

        x.setHijo(i + 1, z);

        for (int j = x.getNumKeys(); j >= i; j--) {
            x.setId(j + 1, x.getId(j));
            x.setValor(j + 1, x.getValor(j));
        }

        x.setId(i, y.getId(t - 1));
        x.setValor(i, y.getValor(t - 1));
        x.setNumKeys(x.getNumKeys() + 1);
    }

    private void insertNonFull(Node x, int key, String valor) {
        int i = x.getNumKeys() - 1;

        if (x.hoja()) {
            while (i >= 0 && key < x.getId(i)) {
                x.setId(i + 1, x.getId(i));
                x.setValor(i + 1, x.getValor(i));
                i--;
            }

            x.setId(i + 1, key);
            x.setValor(i + 1, valor);
            x.setNumKeys(x.getNumKeys() + 1);
        } else {
            while (i >= 0 && key < x.getId(i)) {
                i--;
            }
            i++;
            Node child = x.getHijo(i);
            if (child.getNumKeys() == (2 * t - 1)) {
                split(x, i, child);
                if (key > x.getId(i)) {
                    i++;
                }
            }
            insertNonFull(x.getHijo(i), key, valor);
        }
    }

    public void showBTree() {
        print(root);
    }

    private void print(Node n) {
        for (int i = 0; i < n.getNumKeys(); i++) {
            System.out.print(n.getId(i) + " | " + n.getValor(i) + " ");
        }
        System.out.println();

        if (!n.hoja()) {
            for (int j = 0; j <= n.getNumKeys(); j++) {
                if (n.getHijo(j) != null) {
                    print(n.getHijo(j));
                }
            }
        }
    }
    public void deleteB(int key) {
        delete(root, key);

        if (root.getNumKeys() == 0) {
            if (!root.hoja()) {
                root = root.getHijo(0);
            } else {
                root = null;
            }
        }
    }

    private void delete(Node node, int key) {
        int idx = findKey(node, key);

        if (idx < node.getNumKeys() && node.getId(idx) == key) {
            if (node.hoja()) {
                removeFromLeaf(node, idx);
            } else {
                removeFromNonLeaf(node, idx);
            }
        } else {
            if (node.hoja()) {
                System.out.println("The key " + key + " does not exist in the tree.");
                return;
            }

            boolean flag = (idx == node.getNumKeys());

            if (node.getHijo(idx).getNumKeys() < t) {
                fill(node, idx);
            }

            if (flag && idx > node.getNumKeys()) {
                delete(node.getHijo(idx - 1), key);
            } else {
                delete(node.getHijo(idx), key);
            }
        }
    }

    private int findKey(Node node, int key) {
        int idx = 0;
        while (idx < node.getNumKeys() && node.getId(idx) < key) {
            idx++;
        }
        return idx;
    }

    private void removeFromLeaf(Node node, int idx) {
        for (int i = idx + 1; i < node.getNumKeys(); ++i) {
            node.setId(i - 1, node.getId(i));
            node.setValor(i - 1, node.getValor(i));
        }
        node.setNumKeys(node.getNumKeys() - 1);
    }

    private void removeFromNonLeaf(Node node, int idx) {
        int key = node.getId(idx);

        if (node.getHijo(idx).getNumKeys() >= t) {
            int pred = getPredecessor(node, idx);
            node.setId(idx, pred);
            delete(node.getHijo(idx), pred);
        } else if (node.getHijo(idx + 1).getNumKeys() >= t) {
            int succ = getSuccessor(node, idx);
            node.setId(idx, succ);
            delete(node.getHijo(idx + 1), succ);
        } else {
            merge(node, idx);
            delete(node.getHijo(idx), key);
        }
    }

    private int getPredecessor(Node node, int idx) {
        Node current = node.getHijo(idx);
        while (!current.hoja()) {
            current = current.getHijo(current.getNumKeys());
        }
        return current.getId(current.getNumKeys() - 1);
    }

    private int getSuccessor(Node node, int idx) {
        Node current = node.getHijo(idx + 1);
        while (!current.hoja()) {
            current = current.getHijo(0);
        }
        return current.getId(0);
    }

    private void fill(Node node, int idx) {
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

    private void borrowFromPrev(Node node, int idx) {
        Node child = node.getHijo(idx);
        Node sibling = node.getHijo(idx - 1);

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

    private void borrowFromNext(Node node, int idx) {
        Node child = node.getHijo(idx);
        Node sibling = node.getHijo(idx + 1);

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

    private void merge(Node node, int idx) {
        Node child = node.getHijo(idx);
        Node sibling = node.getHijo(idx + 1);

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

}