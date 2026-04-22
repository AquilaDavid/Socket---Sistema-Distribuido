package servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // CORREÇÃO: volatile garante que todas as threads
    // enxergam o valor atualizado imediatamente
    public static volatile boolean rodando = true;

    public static void main(String[] args) throws Exception {

        ServerSocket servidor = new ServerSocket(12345);
        System.out.println("Servidor rodando na porta 12345...");
        System.out.println("Digite 0 para encerrar o servidor.");

        ClientManager manager = new ClientManager();

        // CORREÇÃO: usa a classe PainelControle que foi criada
        // em vez de duplicar a lógica aqui inline
        new Thread(new PainelControle(servidor, manager)).start();

        while (rodando) {
            try {
                Socket cliente = servidor.accept();

                System.out.println("Nova conexão: " +
                        cliente.getInetAddress().getHostAddress());

                manager.executar(cliente);

            } catch (Exception e) {
                if (rodando) e.printStackTrace();
                // se !rodando, a exceção é esperada (ServerSocket foi fechado)
            }
        }

        System.out.println("Servidor encerrado.");
    }
}
