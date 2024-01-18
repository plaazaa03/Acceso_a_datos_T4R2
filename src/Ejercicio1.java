import org.neodatis.odb.*;
import org.neodatis.odb.core.query.criteria.And;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.values.ValuesCriteriaQuery;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Ejercicio1 {
    private static ODB odb;
    public static void main(String[] args) {
        odb = ODBFactory.open("C://Users//usuario//Desktop//EQUIPOS.db");

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("========MENU========");
            System.out.println("1.Crear paises");
            System.out.println("2.Insertar Nuevo Jugador");
            System.out.println("3.Mostrar todos los paises por pantalla");
            System.out.println("4.Mostrar todos los jugadores por pantalla");
            System.out.println("5.Realizar Consultas Simples");
            System.out.println("6.Realizar Consultas Complejas");
            System.out.println("7.Modificar nombre de un país");
            System.out.println("8.Salir del programa");

            opcion = scanner.nextInt();

            switch (opcion){
                case 1:
                    CrearPaises();
                    break;
                case 2:
                    AñadirNuevoJugador();
                    break;
                case 3:
                    MostarPaisesPorPantalla();
                    break;
                case 4:
                    MostrarJugadoresPorPantalla();
                    break;
                case 5:
                    RealizarConsultasSimples();
                    break;
                case 6:
                    RealizarConsultasComplejas();
                    break;
                case 7:
                    ModificarNombrePais();
                    break;
                case 8:
                    System.out.println("Saliendo del programa. Gracias!");
                    scanner.close();
                    break;

            }


        } while (opcion != 7);
        odb.close();
        scanner.close();

    }


    private static void CrearPaises() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca un identificador para el pais: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduzca el nombre del país: ");
        String nombrePais = scanner.nextLine();

        Objects<Paises> paises = odb.getObjects(Paises.class);
        for (Paises pais : paises){
            if (pais.getNombrePais().equals(nombrePais)){
                System.out.println("El pais ya existe.");
                return;
            }
        }

        Paises crearPais = new Paises(id, nombrePais);
        odb.store(crearPais);
        odb.commit();
        System.out.println("Pais añadido con exito.");

    }

    private static void AñadirNuevoJugador() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el nombre del Jugador: ");
        String nombre = scanner.nextLine();

        Objects<Jugadores> jugadores = odb.getObjects(Jugadores.class);
        for (Jugadores jugador : jugadores){
            if (jugador.getNombre().equals(nombre)){
                System.out.println("Este jugador ya existe");
                return;
            }
        }

        System.out.println("Introduce el deporte que practica: ");
        String deporte = scanner.nextLine();
        System.out.println("Introduce su ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.println("Introduce su edad: ");
        Integer edad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduzca el nombre del pais del jugador: ");
        String nombrePais = scanner.nextLine();

        Objects<Paises> paises = odb.getObjects(Paises.class);
        Paises paisesAsociados = null;
        for (Paises paises1 : paises){
            if (paises1.getNombrePais().equals(nombrePais)){
                paisesAsociados = paises1;
                break;
            }
        }

        if (paisesAsociados == null){
            System.out.println("El pais no esxite.");
            System.out.println("Porfavor antes de nada crea el pais.");
            return;
        }

        Jugadores nuevoJugador = new Jugadores(nombre, deporte, ciudad, edad, paisesAsociados);
        odb.store(nuevoJugador);
        odb.commit();
        System.out.println("El jugador a sido creado con exito.");
    }

    private static void MostarPaisesPorPantalla() {
        Objects<Paises> paises = odb.getObjects(Paises.class);
        for (Paises paises1 : paises){
            System.out.println("========");
            System.out.println("Id: "+ paises1.getId());
            System.out.println("Pais: " + paises1.getNombrePais());
            System.out.println("========");
        }
    }

    private static void MostrarJugadoresPorPantalla() {
        Objects<Jugadores> jugadores = odb.getObjects(Jugadores.class);
        for (Jugadores jugadores1 : jugadores){
            System.out.println("=======");
            System.out.println("Nombre: "+ jugadores1.getNombre());
            System.out.println("Deporte: "+ jugadores1.getDeporte());
            System.out.println("Ciudad: "+ jugadores1.getCiudad());
            System.out.println("Edad: "+ jugadores1.getEdad());
            System.out.println("Pais: "+ jugadores1.getPaises().getNombrePais());
            System.out.println("=======");
        }
    }

    private static void RealizarConsultasSimples() {
        Objects<Jugadores> jugadores = odb.getObjects(Jugadores.class);

        for (Jugadores jugadores1 : jugadores) {
            if (jugadores1.getDeporte().equals("baloncesto") || jugadores1.getPaises().equals("España")){
                System.out.println(jugadores1);
            }
        }

        Objects<Paises> paises = odb.getObjects(Paises.class);

        for (Paises paises1 : paises){
            if (paises1.getNombrePais().equals("E%")){
                System.out.println(paises1);
            }
        }
    }

    private static void RealizarConsultasComplejas(){

        System.out.println("=============================Consultas Complejas=========================================");

        // Edad media jugadores
        System.out.println("==========================================================================");
        System.out.println("Mostrando obtener edad media de jugador: ");
        Values valorMedia = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).avg("edad"));

        ObjectValues mediaValor = valorMedia.nextValues();
        BigDecimal media = (BigDecimal) mediaValor.getByAlias("edad");
        System.out.println("Edad media de todos los jugadores: " + media);


        // Edad maxima y minima de jugadores

        System.out.println("Mostrando la edad minima y maxima de los jugadores: ");
        Values valorMinimo = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).min("edad"));
        Values valorMaximo = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).max("edad"));

        ObjectValues max  = valorMaximo.nextValues();
        BigDecimal maximo = (BigDecimal) max.getByAlias("edad");
        ObjectValues min = valorMinimo.nextValues();
        BigDecimal minimo = (BigDecimal) min.getByAlias("edad");

        System.out.println("La edad maxima es: "+ maximo);
        System.out.println("La edad minima es: "+ minimo);


        // Obtener por cada pais numero de jugadores

        System.out.println("==========================================================================");
        System.out.println("Mostrando por cada pais el numero de jugadores: ");
        Values valorContar = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).field("paises.nombrePais", "nombrePais").count("nombre").groupBy("paises.nombrePais"));


        while (valorContar.hasNext()){
            ObjectValues contar = (ObjectValues) valorContar.nextValues();
            System.out.println("El numero de jugadores del pais " + contar.getByAlias("nombrePais") + " es --> " + contar.getByAlias("nombre"));

        }

        // Mostrar para cada deporte la edad del jugador mas joven que realiza ese deporte

        System.out.println("==========================================================================");
        System.out.println("Mostrando por cada deporte la edad del jugador con la edad mas baja: ");
        Values valorJugMin = odb.getValues(new ValuesCriteriaQuery(Jugadores.class).min("edad").field("deporte").groupBy("deporte"));

        while (valorJugMin.hasNext()){
            ObjectValues minDeporte = (ObjectValues) valorJugMin.nextValues();
            System.out.println("La edad minima de los jugadores que practican " + minDeporte.getByAlias("deporte") + " es --> " + minDeporte.getByAlias("edad"));

        }


        // Mostrar el nombre de los jugadores mayores de 12 que sean españoles

        System.out.println("==========================================================================");
        System.out.println("Mostrando el nombre de los jugadores Españoles que sean mayores de 12 años: ");
        Values valorJug12 = odb.getValues(new ValuesCriteriaQuery(Jugadores.class, new And().add(Where.like("paises.nombrePais","España")).add(Where.gt("edad", 12))).field("nombre").field("edad"));

        while (valorJug12.hasNext()){
            ObjectValues jug12Max = (ObjectValues) valorJug12.nextValues();
            System.out.println("El nombre de los jugadores españoles con mas de 12 años son --> " + jug12Max.getByAlias("nombre"));

        }
        System.out.println("");
        
        
    }

    private static void ModificarNombrePais() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce el id del pais: ");
        Integer idPais = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Introduce el nombre del pais: ");
        String nombrePais = scanner.nextLine();

        Objects<Paises> paises = odb.getObjects(Paises.class);
        for (Paises pais : paises) {
            if (pais.getId() == idPais){
                System.out.println("Introduce el nuevo nombre para el país: ");
                String nuevoNombrePais = scanner.nextLine();

                pais.setNombrePais(nuevoNombrePais);

                odb.store(pais);
                odb.commit();
                System.out.println("Pais modificado con exito.");
                return;
            }

        }
        System.out.println("No se encontro el pais solicitado.");
    }


}