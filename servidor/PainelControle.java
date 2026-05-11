package servidor;

import java.net.ServerSocket;
import java.util.Scanner;

public class PainelControle implements Runnable {

    private ServerSocket servidor;
    private ServerController controller;

    public PainelControle(ServerSocket servidor, ServerController controller) {
        this.servidor = servidor;
        this.controller = controller;
    }

    @Override
    public void run() {

        Scanner teclado = new Scanner(System.in);

        while (controller.isRunning()) {
            String comando = teclado.nextLine();

            if (comando.equals("0")) {
                System.out.println("[SERVIDOR] Encerrando...");
                controller.stop();

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