public class ArbolAVL {
    private Node root;
    private int capacidad;

    // Constructor
    public ArbolAVL(int capacidad) {
        this.capacidad = capacidad;
        this.root = null;
    }

    private int height(Node N) {
        return (N == null) ? 0 : N.getHeight();
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    private Node rightRotate(Node y) {
        Node x = y.getHijo(0);
        Node T2 = x.getHijo(1);

        x.setHijo(1, y);
        y.setHijo(0, T2);

        y.setHeight(max(height(y.getHijo(0)), height(y.getHijo(1))) + 1);
        x.setHeight(max(height(x.getHijo(0)), height(x.getHijo(1))) + 1);

        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.getHijo(1);
        Node T2 = y.getHijo(0);

        y.setHijo(0, x);
        x.setHijo(1, T2);

        x.setHeight(max(height(x.getHijo(0)), height(x.getHijo(1))) + 1);
        y.setHeight(max(height(y.getHijo(0)), height(y.getHijo(1))) + 1);

        return y;
    }

    private int getBalance(Node N) {
        return (N == null) ? 0 : height(N.getHijo(0)) - height(N.getHijo(1));
    }

    private Node insert(Node node, int key, String valor) {

        if (node == null) {
            Node newNode = new Node(capacidad);
            newNode.setId(0, key);
            newNode.setValor(0, valor);
            return newNode;
        }

        if (key < node.getId(0)) {
            node.setHijo(0, insert(node.getHijo(0), key, valor));
        } else if (key > node.getId(0)) {
            node.setHijo(1, insert(node.getHijo(1), key, valor));
        } else {

            return node;
        }

        node.setHeight(1 + max(height(node.getHijo(0)), height(node.getHijo(1))));

        int balance = getBalance(node);

        if (balance > 1 && key < node.getHijo(0).getId(0)) {
            return rightRotate(node);
        }

        if (balance < -1 && key > node.getHijo(1).getId(0)) {
            return leftRotate(node);
        }

        if (balance > 1 && key > node.getHijo(0).getId(0)) {
            node.setHijo(0, leftRotate(node.getHijo(0)));
            return rightRotate(node);
        }

        if (balance < -1 && key < node.getHijo(1).getId(0)) {
            node.setHijo(1, rightRotate(node.getHijo(1)));
            return leftRotate(node);
        }

        return node;
    }

    public void insert(int key, String valor) {
        root = insert(root, key, valor);
    }

    public void inOrder(Node node) {
        if (node != null) {
            inOrder(node.getHijo(0));
            System.out.print(node.getId(0) + " | " + node.getValor(0) + " ");
            inOrder(node.getHijo(1));
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    public Node searchB(int key) {
        return search(root, key);
    }

    private Node search(Node node, int key) {
        if (node == null || node.getId(0) == key) {
            return node;
        }

        if (node.getId(0) > key) {
            return search(node.getHijo(0), key);
        }

        return search(node.getHijo(1), key);
    }

    private Node delete(Node root, int key) {
        if (root == null) {
            return root;
        }

        if (key < root.getId(0)) {
            root.setHijo(0, delete(root.getHijo(0), key));
        } else if (key > root.getId(0)) {
            root.setHijo(1, delete(root.getHijo(1), key));
        } else {
            if ((root.getHijo(0) == null) || (root.getHijo(1) == null)) {
                Node temp = (root.getHijo(0) != null) ? root.getHijo(0) : root.getHijo(1);

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp; 
                }
            } else {
                Node temp = minValueNode(root.getHijo(1));

                root.setId(0, temp.getId(0));
                root.setValor(0, temp.getValor(0));

                root.setHijo(1, delete(root.getHijo(1), temp.getId(0)));
            }
        }

        if (root == null) {
            return root;
        }

        root.setHeight(max(height(root.getHijo(0)), height(root.getHijo(1))) + 1);

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.getHijo(0)) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.getHijo(0)) < 0) {
            root.setHijo(0, leftRotate(root.getHijo(0)));
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.getHijo(1)) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.getHijo(1)) > 0) {
            root.setHijo(1, rightRotate(root.getHijo(1)));
            return leftRotate(root);
        }

        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.getHijo(0) != null) {
            current = current.getHijo(0);
        }
        return current;
    }

    public void deleteB(int key) {
        root = delete(root, key);
    }
}