import java.util.ArrayList;
import java.util.logging.Logger;

public class ResolverLaberintoImplementacion implements ResolverLaberintoInterface {
    private final static Logger LOGGER = Logger.getLogger("ResolverLaberintoImplementacion");
    public static final int VALOR_PROHIBIDO = -1;
    public static final int VALOR_DE_NO_CAMINO = 10000000;

    @Override
    public ArrayList<Posicion> resolverLaberinto(int[][] arg0, int arg1, int arg2, int arg3, int arg4) {

        Posicion[][] tableroNodos = InicializarNodos(arg0);
        Posicion nodoOrigen = tableroNodos[arg1][arg2];
        Posicion nodoDestino = tableroNodos[arg3][arg4];

        ArrayList<Posicion> recorrido = new ArrayList<Posicion>();

        ArrayList<Posicion> mejorCamino = new ArrayList<Posicion>();
        Posicion nodo = new Posicion();
        nodo.setValor(VALOR_DE_NO_CAMINO);
        mejorCamino.add(nodo);
        ArrayList<Posicion> recorrido2 = new ArrayList<Posicion>();

        ArrayList<Posicion> visitados = new ArrayList<Posicion>();
        Camino(tableroNodos, nodoOrigen, nodoDestino, recorrido, mejorCamino);
        mejorCamino.remove(nodoOrigen);
        mejorCamino.remove(nodoDestino);
        if (costoCamino(mejorCamino) == VALOR_DE_NO_CAMINO) {
            LOGGER.info("No hay caminos posibles");
        }
        return mejorCamino;

    }

    //NUEVA MATRIZ CON NODOS
    private Posicion[][] InicializarNodos(int[][] tablero) {
        int row = tablero.length;
        int col = tablero[0].length;
        Posicion[][] tableroNodos = new Posicion[row][col];

        int valor = 0;
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


    private boolean Camino(Posicion[][] tableroNodos, Posicion nodoActual, Posicion nodoDestino, ArrayList<Posicion> recorrido, ArrayList<Posicion> mejorCamino) {
        if (!recorrido.contains(nodoActual)) {
            recorrido.add(nodoActual);
        }
/*
        if (nodoActual == nodoDestino) {
            return true;
        }
*/

        if (nodoActual.x == nodoDestino.x && nodoActual.y == nodoDestino.y) {
            return true;
        }

        ArrayList<Posicion> adyacentes = Adyacentes(tableroNodos, nodoActual, recorrido);
        boolean existeAdyacenteValido = false;
        for (int i = 0; i < adyacentes.size(); i++) {
            boolean isCaminoValido = Camino(tableroNodos, adyacentes.get(i), nodoDestino, recorrido, mejorCamino);
            existeAdyacenteValido |= isCaminoValido;
            if (isCaminoValido && costoCamino(recorrido) < costoCamino(mejorCamino)) {
                mejorCamino.clear();
                mejorCamino.addAll(recorrido);
            }
            recorrido.remove(adyacentes.get(i));
        }
        return existeAdyacenteValido;
    }

    private int costoCamino(ArrayList<Posicion> recorrido) {
        int costoCamino = 0;
        for (int k = 0; k < recorrido.size(); k++) {
            costoCamino += recorrido.get(k).getValor();
        }
        return costoCamino;
    }

    //OBTENCION DE NODOS ADYACENTES
    private ArrayList<Posicion> Adyacentes(Posicion[][] tableroNodos, Posicion nodo, ArrayList<Posicion> recorrido) {
        ArrayList<Posicion> nodosAdyacentes = new ArrayList<Posicion>();
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
