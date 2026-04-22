package servidor;

import java.net.ServerSocket;
import java.util.Scanner;

public class PainelControle implements Runnable {

    private ServerSocket servidor;
    private ClientManager manager;

    public PainelControle(ServerSocket servidor, ClientManager manager) {
        this.servidor = servidor;
        this.manager = manager;
    }

    @Override
    public void run() {

        Scanner teclado = new Scanner(System.in);

        while (Main.rodando) {
            String comando = teclado.nextLine();

            if (comando.equals("0")) {
                System.out.println("Encerrando servidor...");
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
