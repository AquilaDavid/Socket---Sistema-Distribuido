package servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static volatile boolean rodando = true;

    public static void main(String[] args) throws Exception {

        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor rodando na porta 12345...");
        System.out.println("Digite 0 para encerrar o servidor.");

        ClientManager manager = new ClientManager();

        new Thread(new PainelControle(servidor)).start();

        while (rodando) {
            try {
                Socket cliente = servidor.accept();

                System.out.println("[SERVIDOR] Nova conexão: " +
                        cliente.getInetAddress().getHostAddress());

                manager.executar(cliente);

            } catch (Exception e) {
                if (rodando) e.printStackTrace();
            }
        }

        System.out.println("Servidor encerrado.");
    }
}