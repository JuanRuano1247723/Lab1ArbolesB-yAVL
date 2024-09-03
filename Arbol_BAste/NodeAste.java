public class NodeAste {
    private int[] Id;
    private String[] Valor;
    private NodeAste[] Hijos;
    private boolean isLeaf;
    private int numKeys;

    // Constructor
    public NodeAste(int capacidad, boolean isLeaf) {
        this.Id = new int[capacidad];
        this.Valor = new String[capacidad];
        this.Hijos = new NodeAste[capacidad + 1];
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

    public NodeAste getHijo(int index) {
        return Hijos[index];
    }

    public void setHijo(int index, NodeAste hijo) {
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
