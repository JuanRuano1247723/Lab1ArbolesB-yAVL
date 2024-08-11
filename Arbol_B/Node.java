public class Node {
    private int[] Id;
    private String[] Valor;
    private Node[] Hijos;
    private boolean Hoja;
    private int numKeys;

    // Constructor
    public Node(int capacidad, boolean hoja) {
        this.Id = new int[((2 * capacidad) - 1)];
        this.Valor = new String[((2 * capacidad) - 1)];
        this.Hijos = new Node[capacidad*2];
        this.Hoja = hoja;
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

    public boolean hoja() {
        return Hoja;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }

    public void insertInLeaf(int id, String valor) {
        int i = 0;
        while (i < numKeys && Id[i] < id) {
            i++;
        }

        for (int j = numKeys; j > i; j--) {
            Id[j] = Id[j - 1];
            Valor[j] = Valor[j - 1];
        }

        Id[i] = id;
        Valor[i] = valor;
        numKeys++;
    }
}