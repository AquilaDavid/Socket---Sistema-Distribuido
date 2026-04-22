package servidor;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket cliente;
    private ClientManager manager;

    public ClientHandler(Socket cliente, ClientManager manager) {
        this.cliente = cliente;
        this.manager = manager;
    }

    @Override
    public void run() {

        try {
            Scanner entrada = new Scanner(cliente.getInputStream());
            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

            while (true) {

                saida.println("===== MENU =====");
                saida.println("1 - Soma");
                saida.println("2 - Multiplicação");
                saida.println("3 - Maiúsculo");
                saida.println("4 - Inverter texto");
                saida.println("0 - Sair");
                saida.println("================");

                String opcao = entrada.nextLine();

                switch (opcao) {

                    case "1":
                        saida.println("Digite o primeiro número:");
                        int a = Integer.parseInt(entrada.nextLine());

                        saida.println("Digite o segundo número:");
                        int b = Integer.parseInt(entrada.nextLine());

                        saida.println("Resultado: " + (a + b) +
                                " | Cliente: " + cliente.getInetAddress().getHostAddress());
                        break;

                    case "2":
                        saida.println("Digite o primeiro número:");
                        int x = Integer.parseInt(entrada.nextLine());

                        saida.println("Digite o segundo número:");
                        int y = Integer.parseInt(entrada.nextLine());

                        saida.println("Resultado: " + (x * y) +
                                " | Cliente: " + cliente.getInetAddress().getHostAddress());
                        break;

                    case "3":
                        // CORREÇÃO: separado em duas etapas para garantir
                        // que o prompt chega antes de tentar ler a resposta
                        saida.println("Digite um texto:");
                        String texto = entrada.nextLine();
                        saida.println("Resultado: " + texto.toUpperCase());
                        break;

                    case "4":
                        saida.println("Digite um texto:");
                        String t = entrada.nextLine();
                        saida.println("Resultado: " + new StringBuilder(t).reverse());
                        break;

                    case "0":
                        saida.println("Conexão encerrada.");
                        cliente.close();
                        return;

                    default:
                        saida.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado: " +
                    cliente.getInetAddress().getHostAddress());
        } finally {
            // Sempre libera a vaga, com erro ou sem
            manager.clienteFinalizado();
        }
    }
}
