public class NodeAVL {
    private int id;
    private String valor;
    private NodeAVL left;
    private NodeAVL right;
    private int height;

    // Constructor
    public NodeAVL(int id, String valor) {
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

    public NodeAVL getLeft() {
        return left;
    }

    public NodeAVL getRight() {
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

    public void setLeft(NodeAVL left) {
        this.left = left;
    }

    public void setRight(NodeAVL right) {
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

    public NodeAVL rotateRight() {
        NodeAVL newRoot = this.left;
        NodeAVL temp = newRoot.getRight();

        newRoot.setRight(this);
        this.setLeft(temp);

        this.updateHeight();
        newRoot.updateHeight();

        return newRoot; 
    }

    public NodeAVL rotateLeft() {
        NodeAVL newRoot = this.right;
        NodeAVL temp = newRoot.getLeft();

        newRoot.setLeft(this);
        this.setRight(temp);

        this.updateHeight();
        newRoot.updateHeight();

        return newRoot; 
    }

    public NodeAVL rotateLeftRight() {
        this.setLeft(this.getLeft().rotateLeft());
        return this.rotateRight();
    }

    public NodeAVL rotateRightLeft() {
        this.setRight(this.getRight().rotateRight());
        return this.rotateLeft();
    }
}
