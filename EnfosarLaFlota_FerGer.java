import java.util.Scanner;

public class HundirLaFlota {

    private static final int TAMANO_TABLERO = 8;
    private static final int NUM_BARCOS = 5;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("¡BIENVENIDO A HUNDIR LA FLOTA!\n");

        // Configuración de jugadores
        String jugador1 = obtenerNombreJugador(1);
        String jugador2 = obtenerNombreJugador(2);

        // Inicialización de tableros
        char[][] tableroJugador1 = crearTablero();
        char[][] tableroJugador2 = crearTablero();

        // Posicionamiento de barcos
        posicionarBarcos(jugador1, tableroJugador1);
        posicionarBarcos(jugador2, tableroJugador2);

        // Juego principal
        jugarPartida(jugador1, jugador2, tableroJugador1, tableroJugador2);

        scanner.close();
    }

    private static String obtenerNombreJugador(int numeroJugador) {
        System.out.print("Introduce el nombre del Jugador " + numeroJugador + ": ");
        return scanner.nextLine();
    }

    private static char[][] crearTablero() {
        char[][] tablero = new char[TAMANO_TABLERO][TAMANO_TABLERO];
        for (int i = 0; i < TAMANO_TABLERO; i++) {
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                tablero[i][j] = '~'; // '~' representa agua
            }
        }
        return tablero;
    }

    private static void posicionarBarcos(String nombreJugador, char[][] tablero) {
        System.out.println("\n" + nombreJugador + ", es hora de posicionar tus barcos:");
        mostrarTablero(tablero, false);

        for (int i = 1; i <= NUM_BARCOS; i++) {
            boolean posicionValida;
            do {
                System.out.println("Posición del barco " + i + " de " + NUM_BARCOS);
                System.out.print("Ingresa coordenadas (fila,columna) entre 1-" + TAMANO_TABLERO + ": ");
                String coordenadas = scanner.nextLine();

                posicionValida = validarCoordenadas(coordenadas, tablero, true);

                if (posicionValida) {
                    String[] partes = coordenadas.split(",");
                    int fila = Integer.parseInt(partes[0]) - 1;
                    int columna = Integer.parseInt(partes[1]) - 1;
                    tablero[fila][columna] = 'B'; // 'B' representa barco
                    mostrarTablero(tablero, false);
                }
            } while (!posicionValida);
        }
        System.out.println("¡Todos los barcos han sido posicionados!\n");
    }

    private static boolean validarCoordenadas(String coordenadas, char[][] tablero, boolean esPosicionamiento) {
        try {
            String[] partes = coordenadas.split(",");
            if (partes.length != 2) {
                throw new IllegalArgumentException();
            }

            int fila = Integer.parseInt(partes[0]);
            int columna = Integer.parseInt(partes[1]);

            if (fila < 1 || fila > TAMANO_TABLERO || columna < 1 || columna > TAMANO_TABLERO) {
                System.out.println("Error: Las coordenadas deben estar entre 1 y " + TAMANO_TABLERO);
                return false;
            }

            if (esPosicionamiento && tablero[fila - 1][columna - 1] == 'B') {
                System.out.println("Error: Ya hay un barco en esa posición");
                return false;
            }

            return true;

        } catch (Exception e) {
            System.out.println("Error: Formato incorrecto. Usa fila,columna (ej: 3,5)");
            return false;
        }
    }

    private static void jugarPartida(String jugador1, String jugador2, 
                                   char[][] tablero1, char[][] tablero2) {
        System.out.println("\n¡COMIENZA LA PARTIDA!\n");
        boolean juegoTerminado = false;
        int turno = 0;

        while (!juegoTerminado && turno < NUM_BARCOS * 2) {
            // Turno del jugador 1
            juegoTerminado = realizarTurno(jugador1, jugador2, tablero2);
            if (juegoTerminado) {
                System.out.println("\n¡" + jugador1 + " ha ganado la partida!");
                break;
            }

            // Turno del jugador 2
            juegoTerminado = realizarTurno(jugador2, jugador1, tablero1);
            if (juegoTerminado) {
                System.out.println("\n¡" + jugador2 + " ha ganado la partida!");
                break;
            }

            turno++;
        }

        if (!juegoTerminado) {
            determinarGanadorPorPuntos(jugador1, jugador2, tablero1, tablero2);
        }
    }

    private static boolean realizarTurno(String atacante, String defensor, char[][] tablero) {
        System.out.println("\nTurno de " + atacante + " (atacando a " + defensor + ")");
        mostrarTablero(tablero, true);

        boolean disparoValido;
        int fila = 0, columna = 0;

        do {
            System.out.print("Ingresa coordenadas para disparar (fila,columna): ");
            String coordenadas = scanner.nextLine();
            disparoValido = validarCoordenadas(coordenadas, tablero, false);

            if (disparoValido) {
                String[] partes = coordenadas.split(",");
                fila = Integer.parseInt(partes[0]) - 1;
                columna = Integer.parseInt(partes[1]) - 1;
            }
        } while (!disparoValido);

        if (tablero[fila][columna] == 'B') {
            System.out.println("¡IMPACTO! Has hundido un barco.");
            tablero[fila][columna] = 'X'; // 'X' representa barco hundido
            return !quedanBarcos(tablero);
        } else {
            System.out.println("¡AGUA! No había ningún barco.");
            if (tablero[fila][columna] != 'X') {
                tablero[fila][columna] = 'O'; // 'O' representa disparo al agua
            }
            return false;
        }
    }

    private static boolean quedanBarcos(char[][] tablero) {
        for (char[] fila : tablero) {
            for (char celda : fila) {
                if (celda == 'B') {
                    return true;
                }
            }
        }
        return false;
    }

    private static void determinarGanadorPorPuntos(String jugador1, String jugador2, 
                                                  char[][] tablero1, char[][] tablero2) {
        int barcosJugador1 = contarBarcosRestantes(tablero1);
        int barcosJugador2 = contarBarcosRestantes(tablero2);

        System.out.println("\n¡Se han agotado los turnos!");
        System.out.println("Barcos restantes de " + jugador1 + ": " + barcosJugador1);
        System.out.println("Barcos restantes de " + jugador2 + ": " + barcosJugador2);

        if (barcosJugador1 > barcosJugador2) {
            System.out.println("¡" + jugador1 + " gana por tener más barcos restantes!");
        } else if (barcosJugador2 > barcosJugador1) {
            System.out.println("¡" + jugador2 + " gana por tener más barcos restantes!");
        } else {
            System.out.println("¡Empate! Ambos jugadores tienen la misma cantidad de barcos.");
        }
    }

    private static int contarBarcosRestantes(char[][] tablero) {
        int contador = 0;
        for (char[] fila : tablero) {
            for (char celda : fila) {
                if (celda == 'B') {
                    contador++;
                }
            }
        }
        return contador;
    }

    private static void mostrarTablero(char[][] tablero, boolean esAtaque) {
        System.out.print("  ");
        for (int i = 1; i <= TAMANO_TABLERO; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < TAMANO_TABLERO; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < TAMANO_TABLERO; j++) {
                char celda = tablero[i][j];
                if (esAtaque && celda == 'B') {
                    System.out.print("~ "); // No mostrar barcos enemigos
                } else {
                    System.out.print(celda + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
