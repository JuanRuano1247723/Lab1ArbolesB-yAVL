public class Node {
    private int[] Id;
    private String[] Valor;
    private Node[] Hijos;
    private boolean isLeaf;
    private int numKeys;

    // Constructor
    public Node(int capacidad, boolean isLeaf) {
        this.Id = new int[capacidad];
        this.Valor = new String[capacidad];
        this.Hijos = new Node[capacidad + 1];
        this.isLeaf = isLeaf;
        this.numKeys = 0;
    }

    public int getId(int index) {
        return Id[index];
    }

    public void setId(int index, int id) {
        this.Id[index] = id;
    }

    public String getValor(int index) {
        return Valor[index];
    }

    public void setValor(int index, String valor) {
        this.Valor[index] = valor;
    }

    public Node getHijo(int index) {
        return Hijos[index];
    }

    public void setHijo(int index, Node hijo) {
        this.Hijos[index] = hijo;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }
}
