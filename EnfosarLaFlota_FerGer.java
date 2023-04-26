import java.util.*;

public class EnfosarLaFlota_FerGer {

	static int Mida = 8;
	
	public static Scanner teclattext = new Scanner (System.in);
	public static Scanner teclatnum = new Scanner (System.in);
	
	
	public static String DemanarNomDelJugador(int jugador){
	   	  String NomDelJugador; 
	      System.out.print("Entra el Nom del Jugador "+jugador+": ");
	      NomDelJugador = teclattext.nextLine();
	      return NomDelJugador;
	}
	
	//Omplir El Joc Amb El Mar
	public static void OmplirLaMatriuAmbMar(char[][] MatriuUsuari){
	      for (int fila = 0; fila < MatriuUsuari.length; fila++) {
	         for (int columna = 0; columna < MatriuUsuari[fila].length;columna++) {
	        	 		MatriuUsuari[fila][columna] = '~';
	         }
	      }
	   }

   //Posar El vaixells
   public static void posarVaixells(char joc[][], String posicio) {
	   		String separar [] = posicio.split(",");
			int posicion1 = Integer.parseInt(separar[0]) - 1;
			int posicion2 = Integer.parseInt(separar[1]) - 1;
			joc[posicion1][posicion2] = 'v';
   }
   
   
   
   //Per Llançar Els Misils a Els Vaixells
	public static void llensarMisil (char joc[][], String posicio) {
		
		String separar [] = posicio.split(",");
		int posicio1 = Integer.parseInt(separar[0]) - 1;
		int posicio2 = Integer.parseInt(separar[1]) - 1;
		if (joc[posicio1][posicio2] == 'v') {
			System.out.println("El Misil Ha Tocat Un Vaixell!!!!!");
			joc[posicio1][posicio2] = 'm';
		} else {
			System.out.println("El Misil No ha Tocat Cap Vaixell!!!!");
		}
	}

	// Comproba si el Numeros Introdüit esta en El Rang
	public static boolean comprovaPosicions(String posicio, char joc[][], boolean esPosicio) {
			
		boolean posicioCorrecta = true;	
			try {
				
				String parts [] = posicio.split(",");
				int posicion1 = Integer.parseInt(parts[0]);
				int posicion2 = Integer.parseInt(parts[1]);
				
				if (posicion1 < 1 || posicion1 < 1) {
						System.out.println("Una de les Posicions Té un Nombre Més Petit del Demanat");
						System.out.println("Posa Un Altre"+"\n");
						posicioCorrecta = false;
				} else if ( posicion1 > Mida || posicion2 > Mida ) {
					System.out.println("Una de les Posicions té un nombre Més Gran del Demanat");
					System.out.println("Posa Un Altre"+"\n");
					posicioCorrecta = false;
		
				}
				
				if ( joc [posicion1 - 1][posicion2 - 1] == 'v' && esPosicio == true) {
					posicioCorrecta = false;
					System.out.println("En la Posició Ja Hi Ha un vaixell");
					System.out.println("Prova amb una altra Posició."+"\n");
				}
	
				} catch (NumberFormatException e) {
					
					System.out.println("No Hi ha Coma o Hi Ha un Espai");
					System.out.println("Torna a Posar Amb el Format Demanat."+"\n");
					posicioCorrecta = false;
				
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Només S'ha Detectat un Nombre");
					System.out.println("Torna a Posar"+"\n");
					posicioCorrecta = false;
				}
	
				return posicioCorrecta;
	}
	
	//Per Mostrar La Matriu
	public static void mostraMatriu(char joc [][]) {
		for (int fila = 0; fila < joc.length; fila++) {
			for (int colum = 0; colum < joc[fila].length;colum++) {
				System.out.print(joc[fila][colum]+ " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

	// Si Ha enfonsat Tots Els Vaixells Per Acabar La Partida.
	public static boolean haGuanyat (char joc [][]) {
				
				boolean guanyaLaPartida = false;			
				int comptador = 0;

				for (int fila = 0; fila < joc.length; fila++) {
						for (int colum = 0; colum < joc[fila].length; colum++) {
							if (joc[fila][colum] == 'v') {
								comptador++;
						}
					}
				}
				if (comptador == 0) {
					guanyaLaPartida = true;
				}
			return guanyaLaPartida;
		}
	
	// Comprova Quin Juagador té més Vaixells per veure qui ha Guanya
	public static int comprovaGuanyador(char joc [][]) {
			int comptadorvaixells = 0;
				for (int fila = 0; fila < joc.length; fila++) {
					for (int colum = 0; colum < joc[fila].length; colum++) {
						if (joc[fila][colum] == 'v') {
							comptadorvaixells++;
						}
					}
				}
				return comptadorvaixells;
			}

public static void main(String[] args) {
		
		String jugador1, jugador2;
		char joc1 [][] = new char [Mida][Mida];
		char joc2 [][] = new char [Mida][Mida];
		String posicio;
		boolean posicioCorrecta;
		
		//Posar Noms a Els Jugadors
		jugador1 = DemanarNomDelJugador(1);
		jugador2 = DemanarNomDelJugador(2);
		
		//Omplim La Matriu amb m (Mar)
		OmplirLaMatriuAmbMar(joc1);
		OmplirLaMatriuAmbMar(joc2);

		System.out.println("Posicionament Del Jugador "+jugador1);
		for (int i = 0; i < 5; i++) {
			do {
				System.out.println ("Posicio del vaixell " +(i + 1));
				System.out.println("Posa La Posició on Vols Posar El Vaixell. (Exemples: 0,0 | 1,5 | 5,6)");
				System.out.println("La Posició tenen que Ser (màxim "+Mida+"): ");
				posicio = teclattext.nextLine();
				posicioCorrecta = comprovaPosicions(posicio, joc1, true);
			
			} while (posicioCorrecta == false);
		
			posarVaixells(joc1, posicio);
		}

		System.out.println("Posicionament Del Jugador "+jugador2);
		for (int i = 0; i < 5; i++) {
			do {
					System.out.println("Posicio del vaixell "+(i + 1));
					System.out.println("Posa La Posició on Vols Posar El Vaixell. (Exemples: 0,0 | 1,5 | 5,6 )");
					System.out.println("La posició tenen que Ser"+" (màxim "+Mida+"): ");
					posicio = teclattext.nextLine();
					posicioCorrecta = comprovaPosicions(posicio, joc2, true);
				} while (posicioCorrecta == false);
				
			posarVaixells(joc2, posicio);
		}

		
		System.out.println("\n"+"Comença La Partida!"+"\n");
		
		// Llançar Els Misils Fins a 5 Cops
		for (int i = 0; i < 5; i++) {
				do {
						System.out.println((i+1)+" Tirada "+" del "+jugador1+"\n");
						System.out.println("Tauler de "+jugador2);
						mostraMatriu(joc2);
						System.out.println("Posa la Posició per Atacar El Vaixell de "+jugador2+" (Exemples: 0,0 | 1,5 | 5,6 )");
						System.out.println("La posició tenen que Ser"+" (màxim "+Mida+"): ");
						posicio = teclattext.nextLine();
						posicioCorrecta = comprovaPosicions(posicio, joc1, false);
					} while (posicioCorrecta == false);
				llensarMisil(joc2, posicio);
				boolean guanyaLaPartida = haGuanyat(joc2);
				
				if (guanyaLaPartida == true) {
					break;
				}
	
				do {
						System.out.println((i+1)+" Tirada "+" del "+jugador2+"\n");
						System.out.println("Tauler de "+jugador1);
						mostraMatriu(joc1);
						System.out.println("Posa la Posició per Atacar El Vaixell de "+jugador1+" (Exemples: 0,0 | 1,5 | 5,6 )");
						System.out.println("La posició tenen que Ser"+" (màxim "+Mida+"): ");
						posicio = teclattext.nextLine();
						posicioCorrecta = comprovaPosicions(posicio, joc1, false);
					} while (posicioCorrecta == false);
						llensarMisil(joc1, posicio);
						guanyaLaPartida = haGuanyat(joc1);
				
				if (guanyaLaPartida == true) {
					break;
				}
			
		}
			
			// Determina Qui ha Guanyat.
			
			System.out.println("\n"+"La Partida Ha Acabat!!!!"+"\n");
			
			int comptadorvaixells1,comptadorvaixells2;
			
			comptadorvaixells1 = comprovaGuanyador(joc1);
			comptadorvaixells2 = comprovaGuanyador(joc2);
			
			if (comptadorvaixells1 > comptadorvaixells2) {
				System.out.println("El "+jugador1+" Ha Guanyat Amb " + comptadorvaixells1 + " Vaixells!");
			} else if (comptadorvaixells1 < comptadorvaixells2) {
				System.out.println("El "+jugador2+" Ha Guanyat Amb " + comptadorvaixells2 + " Vaixells!");
			} else {
				System.out.println("Hi Ha Empat Amb " + comptadorvaixells1 + " Vaixells!");
			}
		
	}
}
	