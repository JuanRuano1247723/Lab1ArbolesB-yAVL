public class Node {
    private int id;
    private String valor;
    private Node left;
    private Node right;
    private int height;

    // Constructor
    public Node(int id, String valor) {
        this.id = id;
        this.valor = valor;
        this.height = 1; 
    }

    public int getId() {
        return id;
    }

    public String getValor() {
        return valor;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getHeight() {
        return height;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void updateHeight() {
        int leftHeight = (left == null) ? 0 : left.getHeight();
        int rightHeight = (right == null) ? 0 : right.getHeight();
        height = Math.max(leftHeight, rightHeight) + 1;
    }

    public int getBalance() {
        int leftHeight = (left == null) ? 0 : left.getHeight();
        int rightHeight = (right == null) ? 0 : right.getHeight();
        return leftHeight - rightHeight;
    }

    public Node rotateRight() {
        Node newRoot = this.left;
        Node temp = newRoot.getRight();

        newRoot.setRight(this);
        this.setLeft(temp);

        this.updateHeight();
        newRoot.updateHeight();

        return newRoot; 
    }

    public Node rotateLeft() {
        Node newRoot = this.right;
        Node temp = newRoot.getLeft();

        newRoot.setLeft(this);
        this.setRight(temp);

        this.updateHeight();
        newRoot.updateHeight();

        return newRoot; 
    }

    public Node rotateLeftRight() {
        this.setLeft(this.getLeft().rotateLeft());
        return this.rotateRight();
    }

    public Node rotateRightLeft() {
        this.setRight(this.getRight().rotateRight());
        return this.rotateLeft();
    }
}
