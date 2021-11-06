import java.util.ArrayList;


public class principal {


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ResolverLaberintoImplementacion resolverProblema = new ResolverLaberintoImplementacion();

        //Obtener �stos valores por pantalla, se inicializan para que compile
        int origenX = 1;
        int origenY = 0;
        int destX = 3;
        int destY = 3;
        int[][] tablero = new int[4][5];
        //PRIMERA FILA
        tablero[0][0] = 3;
        tablero[0][1] = 2;
        tablero[0][2] = 1;
        tablero[0][3] = 3;
        tablero[0][4] = 1;
        //SEGUNDA FILA
        tablero[1][0] = 5; //ORIGEN
        tablero[1][1] = -1;
        tablero[1][2] = 4;
        tablero[1][3] = -1;
        tablero[1][4] = 2;
        //TERCERA FILA
        tablero[2][0] = -1;
        tablero[2][1] = 8;
        tablero[2][2] = 4;
        tablero[2][3] = 5;
        tablero[2][4] = 1;
        //CUARTA FILA
        tablero[3][0] = 5;
        tablero[3][1] = 6;
        tablero[3][2] = -1;
        tablero[3][3] = 2; //DESTINO
        tablero[3][4] = -1;

        ArrayList<Posicion> resultado = resolverProblema.resolverLaberinto(tablero, origenX, origenY, destX, destY);
        imprimirResultado(resultado);

    }


    private static void imprimirResultado(ArrayList<Posicion> resultado) {
        int sumaTotal = 0;
        System.out.println("Secuencia de Movimientos");
        for (int i = 0; i < resultado.size(); i++) {
            System.out.print(" = " + resultado.get(i).x + " - " + resultado.get(i).y + " - " + resultado.get(i).valor);
            sumaTotal += resultado.get(i).valor;
        }
        System.out.println("");
        System.out.println("Suma total: " + sumaTotal);
    }

}



