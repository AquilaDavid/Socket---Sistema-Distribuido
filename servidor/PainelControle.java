package servidor;

import java.net.ServerSocket;
import java.util.Scanner;

public class PainelControle implements Runnable {

    private ServerSocket servidor;

    public PainelControle(ServerSocket servidor) {
        this.servidor = servidor;
    }

    @Override
    public void run() {

        Scanner teclado = new Scanner(System.in);

        while (Main.rodando) {
            String comando = teclado.nextLine();

            if (comando.equals("0")) {
                System.out.println("[SERVIDOR] Encerrando...");
                Main.rodando = false;

                try {
                    servidor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        teclado.close();
    }
}