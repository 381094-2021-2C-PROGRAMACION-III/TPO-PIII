import java.util.ArrayList;
import java.util.logging.Logger;

public class ResolverLaberintoImplementacion implements ResolverLaberintoInterface {
    private final static Logger LOGGER = Logger.getLogger("ResolverLaberintoImplementacion");
    public static final int VALOR_PROHIBIDO = -1;
    public static final int VALOR_DE_NO_CAMINO = Integer.MAX_VALUE;

    @Override
    public ArrayList<Posicion> resolverLaberinto(int[][] arg0, int arg1, int arg2, int arg3, int arg4) {

        Posicion[][] tableroNodos = InicializarNodos(arg0); //Cuadratico
        Posicion nodoOrigen = tableroNodos[arg1][arg2]; //Constante
        Posicion nodoDestino = tableroNodos[arg3][arg4]; //Constante

        ArrayList<Posicion> recorrido = new ArrayList<>(); //Constante

        recorrido.add(nodoOrigen); //Constante
        ArrayList<Posicion> mejorCamino = Camino(tableroNodos, nodoOrigen, nodoDestino, recorrido); //RECURSIVO
        mejorCamino.remove(nodoOrigen); //Constante
        mejorCamino.remove(nodoDestino); //Constante
        if (costoCamino(mejorCamino) == VALOR_DE_NO_CAMINO) { //Constante
            LOGGER.info("No hay caminos posibles");
        }
        return mejorCamino;
    }

    private ArrayList<Posicion> MaxMaxino() { //Constante
        ArrayList<Posicion> mejorCamino = new ArrayList<>(); //Constante
        Posicion nodo = new Posicion(); //Constante
        nodo.setValor(VALOR_DE_NO_CAMINO); //Constante
        mejorCamino.add(nodo); //Constante
        return mejorCamino; //Constante
    }

    //NUEVA MATRIZ CON NODOS
    private Posicion[][] InicializarNodos(int[][] tablero) { //Cuadratico
        int row = tablero.length; //Constante
        int col = tablero[0].length; //Constante
        Posicion[][] tableroNodos = new Posicion[row][col]; //Constante

        int valor; //Constante
        for (int i = 0; i < row; i++) { //Cuadratico
            for (int j = 0; j < col; j++) { //Lineal
                valor = tablero[i][j]; //Constante
                Posicion nodo = new Posicion(); //Constante
                nodo.setX(i); //Constante
                nodo.setY(j); //Constante
                nodo.setValor(valor); //Constante
                tableroNodos[i][j] = nodo; //Constante
            }
        }
        return tableroNodos;
    }


    private ArrayList<Posicion> Camino(Posicion[][] tableroNodos, Posicion nodoActual, Posicion nodoDestino, ArrayList<Posicion> recorrido) {
        //Condicion de Corte
        if (nodoActual == nodoDestino) { //Constante
            return recorrido;
        }

        ArrayList<Posicion> adyacentes = Adyacentes(tableroNodos, nodoActual, recorrido); //Lineal
        ArrayList<Posicion> mejorCamino = MaxMaxino(); //Constante
        for (Posicion adyacenteActual : adyacentes) { //Lineal
            recorrido.add(adyacenteActual); //Constante
            ArrayList<Posicion> camino = Camino(tableroNodos, adyacenteActual, nodoDestino, recorrido);
            if (costoCamino(camino) < costoCamino(mejorCamino)) { //Lineal
                mejorCamino.clear(); //Constante
                mejorCamino.addAll(camino); //Lineal
            }
            recorrido.remove(adyacenteActual); //Constante
        }
        return mejorCamino;
    }

    private int costoCamino(ArrayList<Posicion> recorrido) {//Lineal
        int costoCamino = 0; //Constante
        for (Posicion posicion : recorrido) { //Lineal
            costoCamino += posicion.getValor(); //Constante
        }
        return costoCamino;
    }

    //OBTENCION DE NODOS ADYACENTES
    private ArrayList<Posicion> Adyacentes(Posicion[][] tableroNodos, Posicion nodo, ArrayList<Posicion> recorrido) { //Lineal
        ArrayList<Posicion> nodosAdyacentes = new ArrayList<>(); //Constante
        int x = nodo.getX(); //Constante
        int y = nodo.getY(); //Constante
        int row = tableroNodos.length - 1; //Constante
        int col = tableroNodos[0].length - 1; //Constante

        //AGREGO NODO SUPERIOR
        if (isAdyacenteNodoNO(x) && !isNodoRecorrido(recorrido, tableroNodos[x - 1][y]) && !isValorProhibido(tableroNodos[x - 1][y])) {
            nodosAdyacentes.add(tableroNodos[x - 1][y]);
        }
        //AGREGO NODO INFERIOR
        if (isAdyacenteNodoSE(x, row) && !isNodoRecorrido(recorrido, tableroNodos[x + 1][y]) && !isValorProhibido(tableroNodos[x + 1][y])) {
            nodosAdyacentes.add(tableroNodos[x + 1][y]);
        }
        //AGREGO NODO LATERAL IZQUIERDO
        if (isAdyacenteNodoNO(y) && !isNodoRecorrido(recorrido, tableroNodos[x][y - 1]) && !isValorProhibido(tableroNodos[x][y - 1])) {
            nodosAdyacentes.add(tableroNodos[x][y - 1]);
        }
        //AGREGO NODO LATERAL DERECHO
        if (isAdyacenteNodoSE(y, col) && !isNodoRecorrido(recorrido, tableroNodos[x][y + 1]) && !isValorProhibido(tableroNodos[x][y + 1])) {
            nodosAdyacentes.add(tableroNodos[x][y + 1]);
        }
        //AGREGO NODO DIAGONAL SUPERIOR IZQUIERDO
        if (isAdyacenteNodoNO(x) && isAdyacenteNodoNO(y) && !isNodoRecorrido(recorrido, tableroNodos[x - 1][y - 1]) && !isValorProhibido(tableroNodos[x - 1][y - 1])) {
            nodosAdyacentes.add(tableroNodos[x - 1][y - 1]);
        }
        //AGREGO NODO DIAGONAL SUPERIOR DERECHO
        if (isAdyacenteNodoNO(x) && isAdyacenteNodoSE(y, col) && !isNodoRecorrido(recorrido, tableroNodos[x - 1][y + 1]) && !isValorProhibido(tableroNodos[x - 1][y + 1])) {
            nodosAdyacentes.add(tableroNodos[x - 1][y + 1]);
        }
        //AGREGO NODO DIAGONAL INFERIOR IZQUIERDO
        if (isAdyacenteNodoSE(x, row) && isAdyacenteNodoNO(y) && !isNodoRecorrido(recorrido, tableroNodos[x + 1][y - 1]) && !isValorProhibido(tableroNodos[x + 1][y - 1])) {
            nodosAdyacentes.add(tableroNodos[x + 1][y - 1]);
        }
        //AGREGO NODO DIAGONAL INFERIOR DERECHO
        if (isAdyacenteNodoSE(x, row) && isAdyacenteNodoSE(y, col) && !isNodoRecorrido(recorrido, tableroNodos[x + 1][y + 1]) && !isValorProhibido(tableroNodos[x + 1][y + 1])) {
            nodosAdyacentes.add(tableroNodos[x + 1][y + 1]);
        }
        return nodosAdyacentes;
    }

    //SENTIDO NORTE U OESTE (ARRIBA O IZQUIERDA)
    private boolean isAdyacenteNodoNO(int posicion) {
        return posicion - 1 >= 0;
    } //Constante

    //SENTIDO SUR U ESTE (ABAJO O DERECHA)
    private boolean isAdyacenteNodoSE(int posicion, int tope) {
        return posicion + 1 <= tope;
    } //Constante

    //CONSTRAINT DE VALOR PROHIBIDO
    private boolean isValorProhibido(Posicion posicionSiguiente) {
        return posicionSiguiente.getValor() == VALOR_PROHIBIDO; //Constante
    }

    //CONSTRAINT DE VALOR YA VISITANDO EN EL RECORRIDO
    private boolean isNodoRecorrido(ArrayList<Posicion> recorrido, Posicion posicionSiguiente) {
        return recorrido.contains(posicionSiguiente); //Lineal

    }


}
