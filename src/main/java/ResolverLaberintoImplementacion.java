import java.util.ArrayList;
import java.util.logging.Logger;

public class ResolverLaberintoImplementacion implements ResolverLaberintoInterface {
    private final static Logger LOGGER = Logger.getLogger("ResolverLaberintoImplementacion");
    public static final int VALOR_PROHIBIDO = -1;
    public static final int VALOR_DE_NO_CAMINO = Integer.MAX_VALUE;

    @Override
    public ArrayList<Posicion> resolverLaberinto(int[][] arg0, int arg1, int arg2, int arg3, int arg4) {

        Posicion[][] tableroNodos = InicializarNodos(arg0);
        Posicion nodoOrigen = tableroNodos[arg1][arg2];
        Posicion nodoDestino = tableroNodos[arg3][arg4];

        ArrayList<Posicion> recorrido = new ArrayList<>();

        recorrido.add(nodoOrigen);
        ArrayList<Posicion> mejorCamino = Camino(tableroNodos, nodoOrigen, nodoDestino, recorrido);
        mejorCamino.remove(nodoOrigen);
        mejorCamino.remove(nodoDestino);
        if (costoCamino(mejorCamino) == VALOR_DE_NO_CAMINO) {
            LOGGER.info("No hay caminos posibles");
        }
        return mejorCamino;

    }

    private ArrayList<Posicion> MaxMaxino() {
        ArrayList<Posicion> mejorCamino = new ArrayList<>();
        Posicion nodo = new Posicion();
        nodo.setValor(VALOR_DE_NO_CAMINO);
        mejorCamino.add(nodo);
        return mejorCamino;
    }

    //NUEVA MATRIZ CON NODOS
    private Posicion[][] InicializarNodos(int[][] tablero) {
        int row = tablero.length;
        int col = tablero[0].length;
        Posicion[][] tableroNodos = new Posicion[row][col];

        int valor;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                valor = tablero[i][j];
                Posicion nodo = new Posicion();
                nodo.setX(i);
                nodo.setY(j);
                nodo.setValor(valor);
                tableroNodos[i][j] = nodo;
            }
        }
        return tableroNodos;
    }


    private ArrayList<Posicion> Camino(Posicion[][] tableroNodos, Posicion nodoActual, Posicion nodoDestino, ArrayList<Posicion> recorrido) {
        if (nodoActual == nodoDestino) {
            return recorrido;
        }
        /*if (costoCamino(recorrido) > costoCamino(mejorCamino)) {
            return false;
        }*/
        ArrayList<Posicion> adyacentes = Adyacentes(tableroNodos, nodoActual, recorrido);
        ArrayList<Posicion> mejorCamino = MaxMaxino();
        for (Posicion adyacenteActual : adyacentes) {
            recorrido.add(adyacenteActual);
            ArrayList<Posicion> camino = Camino(tableroNodos, adyacenteActual, nodoDestino, recorrido);
            if (costoCamino(camino) < costoCamino(mejorCamino)) {
                mejorCamino.clear();
                mejorCamino.addAll(camino);
            }
            recorrido.remove(adyacenteActual);
        }
        return mejorCamino;
    }

    private int costoCamino(ArrayList<Posicion> recorrido) {
        int costoCamino = 0;
        for (Posicion posicion : recorrido) {
            costoCamino += posicion.getValor();
        }
        return costoCamino;
    }

    //OBTENCION DE NODOS ADYACENTES
    private ArrayList<Posicion> Adyacentes(Posicion[][] tableroNodos, Posicion nodo, ArrayList<Posicion> recorrido) {
        ArrayList<Posicion> nodosAdyacentes = new ArrayList<>();
        int x = nodo.getX();
        int y = nodo.getY();
        int row = tableroNodos.length - 1;
        int col = tableroNodos[0].length - 1;

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
    }

    //SENTIDO SUR U ESTE (ABAJO O DERECHA)
    private boolean isAdyacenteNodoSE(int posicion, int tope) {
        return posicion + 1 <= tope;
    }

    //CONSTRAINT DE VALOR PROHIBIDO
    private boolean isValorProhibido(Posicion posicionSiguiente) {
        return posicionSiguiente.getValor() == VALOR_PROHIBIDO;
    }

    //CONSTRAINT DE VALOR YA VISITANDO EN EL RECORRIDO
    private boolean isNodoRecorrido(ArrayList<Posicion> recorrido, Posicion posicionSiguiente) {
        return recorrido.contains(posicionSiguiente);

    }


}
