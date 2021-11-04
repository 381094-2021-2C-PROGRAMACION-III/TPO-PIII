import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	ResolverLaberintoImplementacion resolverProblema = new ResolverLaberintoImplementacion();
	
	//Obtener éstos valores por pantalla, se inicializan para que compile 
	int origenX=0;
	int origenY=0;
	int destX=0;
	int destY=0;
	int [][] tablero = new int[3][4];
	
	
    ArrayList<Posicion> resultado = resolverProblema.resolverLaberinto(tablero, origenX, origenY, destX, destY);
    imprimirResultado(resultado);

}



private static void imprimirResultado (ArrayList<Posicion> resultado) {
	int sumaTotal = 0;
	System.out.println("Secuencia de Movimientos");
	for (int i=0; i<resultado.size();i++) {
		System.out.print(" = "+resultado.get(i).x+" - "+resultado.get(i).y+ " - "+resultado.get(i).valor);
		sumaTotal += resultado.get(i).valor;
	}
	System.out.println("");
	System.out.println("Suma total: "+sumaTotal);
}

}
