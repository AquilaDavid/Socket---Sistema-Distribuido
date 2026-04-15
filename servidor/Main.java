package servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static boolean rodando = true;

    public static void main(String[] args) throws Exception {

        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor rodando na porta 12345...");

        ClientManager manager = new ClientManager();

        // 🔥 Painel (encerra tudo com 0)
        new Thread(() -> {
            try {
                while (true) {
                    int cmd = System.in.read();

                    if (cmd == '0') {
                        System.out.println("Encerrando servidor...");

                        rodando = false;
                        servidor.close();

                        System.exit(0); // 🔥 encerra tudo
                    }
                }
            } catch (Exception e) {}
        }).start();

        while (rodando) {
            try {
                Socket cliente = servidor.accept();

                System.out.println("Cliente conectado: " +
                        cliente.getInetAddress().getHostAddress());

                manager.executar(cliente);

            } catch (Exception e) {
                if (rodando) e.printStackTrace();
            }
        }
    }
}