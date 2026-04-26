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

                // 🔥 VALIDAÇÃO DA OPÇÃO
                if (!opcao.matches("[0-4]")) {
                    saida.println("Opção inválida! Digite entre 0 e 4.");
                    continue;
                }

                switch (opcao) {

                    case "1":
                        saida.println("Digite o primeiro número:");
                        int a = lerNumero(entrada, saida);

                        saida.println("Digite o segundo número:");
                        int b = lerNumero(entrada, saida);

                        saida.println("Resultado: " + (a + b));
                        break;

                    case "2":
                        saida.println("Digite o primeiro número:");
                        int x = lerNumero(entrada, saida);

                        saida.println("Digite o segundo número:");
                        int y = lerNumero(entrada, saida);

                        saida.println("Resultado: " + (x * y));
                        break;

                    case "3":
                        saida.println("Digite um texto (apenas letras):");
                        String texto = entrada.nextLine();

                        if (texto.trim().isEmpty()) {
                            saida.println("Texto não pode ser vazio.");
                            continue;
                        }

                        if (!texto.matches("^[a-zA-ZÀ-ÿ]+$")) {
                            saida.println("Entrada inválida! Digite apenas letras.");
                            continue;
                        }

                        saida.println("Resultado: " + texto.toUpperCase());
                        break;

                    case "4":
                        saida.println("Digite um texto (apenas letras):");
                        String t = entrada.nextLine();

                        if (t.trim().isEmpty()) {
                            saida.println("Texto não pode ser vazio.");
                            continue;
                        }

                        if (!t.matches("^[a-zA-ZÀ-ÿ]+$")) {
                            saida.println("Entrada inválida! Digite apenas letras.");
                            continue;
                        }

                        saida.println("Resultado: " + new StringBuilder(t).reverse());
                        break;

                    case "0":
                        saida.println("Conexão encerrada.");
                        cliente.close();
                        return;
                }
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado: " +
                    cliente.getInetAddress().getHostAddress());
        } finally {
            manager.clienteFinalizado();
        }
    }

    // 🔥 MÉTODO DE VALIDAÇÃO DE NÚMERO
    private int lerNumero(Scanner entrada, PrintWriter saida) {
        while (true) {
            try {
                String valor = entrada.nextLine();
                return Integer.parseInt(valor);
            } catch (Exception e) {
                saida.println("Valor inválido! Digite um número:");
            }
        }
    }
}