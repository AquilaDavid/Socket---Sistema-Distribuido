package servidor;

import java.net.Socket;

public class ClientManager {

    private final int LIMITE = 5;
    private int clientesAtivos = 0;

    public synchronized void executar(Socket cliente) {

        if (clientesAtivos >= LIMITE) {
            try {
                cliente.getOutputStream().write(
                        "Servidor lotado. Tente novamente mais tarde.\n".getBytes()
                );
                cliente.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        clientesAtivos++;
        System.out.println("Clientes ativos: " + clientesAtivos + "/" + LIMITE);

        new Thread(new ClientHandler(cliente, this)).start();
    }

    public synchronized void clienteFinalizado() {
        clientesAtivos--;
        System.out.println("Vaga liberada. Clientes ativos: " + clientesAtivos + "/" + LIMITE);
    }
}