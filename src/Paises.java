public class Paises {
    private int id;
    private String nombrePais;


    public Paises (){
    }

    public Paises (int id, String nombrePais){
    this.id = id;
    this.nombrePais = nombrePais;

    }

    @Override
    public String toString() {
        return "Id: " + id + ", País: " + nombrePais ;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public int getId() {
        return id;
    }

    public String getNombrePais() {
        return nombrePais;
    }
}
