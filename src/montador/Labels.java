package montador;

public class Labels {
    
   private String name;
   private int position;

    public Labels(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getNome() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }

    public int getPos() {
        return position;
    }

    public void setPos(int position) {
        this.position = position;
    }
}