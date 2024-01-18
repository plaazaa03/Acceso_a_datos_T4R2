public class Jugadores {
    private String nombre;
    private String deporte;
    private String ciudad;
    private int edad;

    private Paises paises;

    public Jugadores (){

    }
    public Jugadores(String nombre, String deporte, String ciudad, Integer edad,Paises nombrePais) {
        this.nombre = nombre;
        this.deporte = deporte;
        this.ciudad = ciudad;
        this.edad = edad;
        this.paises = nombrePais;

    }



    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDeporte() {
        return deporte;
    }
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Deporte: " + deporte + ", Ciudad: " + ciudad + ", Edad: " + edad;
    }

    public Paises getPaises() {
        return paises;
    }
    public void setPaises(Paises paises) {
        this.paises = paises;
    }
}
