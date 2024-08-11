public class Node {
    private int[] Id;
    private String[] Valor;
    private Node[] Hijos;
    private int height;

    // Constructor
    public Node(int capacidad) {
        this.Id = new int[capacidad];
        this.Valor = new String[capacidad];
        this.Hijos = new Node[2 * capacidad];
        this.height = 1;
    }

    public void imprimir() {
        System.out.print("[");
        for (int i = 0; i < Id.length; i++) {
            if (i < Id.length - 1) {
                System.out.print(Id[i] + " | " + Valor[i] + " ");
            } else {
                System.out.print(Id[i] + " | " + Valor[i]);
            }
        }
        System.out.print("]");
    }

    public String getValor(int index) {
        return Valor[index];
    }

    public void setValor(int index, String valor) {
        this.Valor[index] = valor;
    }

    public int getId(int index) {
        return Id[index];
    }

    public void setId(int index, int id) {
        this.Id[index] = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node getHijo(int index) {
        return Hijos[index];
    }

    public void setHijo(int index, Node hijo) {
        this.Hijos[index] = hijo;
    }
}
