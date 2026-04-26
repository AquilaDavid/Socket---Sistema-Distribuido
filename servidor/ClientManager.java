package servidor;

import java.net.Socket;

public class ClientManager {

    private final int LIMITE = 5;
    private int clientesAtivos = 0;

    public synchronized void executar(Socket cliente) {

        String ip = cliente.getInetAddress().getHostAddress();

        if (clientesAtivos >= LIMITE) {
            try {
                System.out.println("[" + ip + "] REJEITADO (Servidor cheio)");

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
        System.out.println("[" + ip + "] ACEITO | Ativos: " +
                clientesAtivos + "/" + LIMITE);

        new Thread(new ClientHandler(cliente, this)).start();
    }

    public synchronized void clienteFinalizado(String ip) {
        clientesAtivos--;
        System.out.println("[" + ip + "] DESCONECTADO | Ativos: " +
                clientesAtivos + "/" + LIMITE);
    }
}