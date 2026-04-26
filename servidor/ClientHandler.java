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

        // 🔥 IDENTIFICADOR ÚNICO (IP + PORTA)
        String idCliente = cliente.getInetAddress().getHostAddress() + ":" + cliente.getPort();

        try {
            Scanner entrada = new Scanner(cliente.getInputStream());
            PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

            log(idCliente, "Conectado");

            while (true) {

                saida.println("===== MENU =====");
                saida.println("1 - Soma");
                saida.println("2 - Multiplicação");
                saida.println("3 - Maiúsculo");
                saida.println("4 - Inverter texto");
                saida.println("0 - Sair");
                saida.println("================");

                String opcao = entrada.nextLine();
                log(idCliente, "Opção escolhida: " + opcao);

                if (!opcao.matches("[0-4]")) {
                    saida.println("Opção inválida!");
                    log(idCliente, "Erro: opção inválida");
                    continue;
                }

                switch (opcao) {

                    case "1":
                        saida.println("Digite o primeiro número:");
                        int a = lerNumero(entrada, saida, idCliente, "A");

                        saida.println("Digite o segundo número:");
                        int b = lerNumero(entrada, saida, idCliente, "B");

                        int soma = a + b;
                        saida.println("Resultado: " + soma);
                        log(idCliente, "Soma: " + a + " + " + b + " = " + soma);
                        break;

                    case "2":
                        saida.println("Digite o primeiro número:");
                        int x = lerNumero(entrada, saida, idCliente, "X");

                        saida.println("Digite o segundo número:");
                        int y = lerNumero(entrada, saida, idCliente, "Y");

                        int mult = x * y;
                        saida.println("Resultado: " + mult);
                        log(idCliente, "Multiplicação: " + x + " * " + y + " = " + mult);
                        break;

                    case "3":
                        saida.println("Digite um texto (letras e espaços):");
                        String texto = entrada.nextLine();
                        log(idCliente, "Texto recebido: " + texto);

                        if (texto.trim().isEmpty() || !texto.matches("^[a-zA-ZÀ-ÿ ]+$")) {
                            saida.println("Entrada inválida!");
                            log(idCliente, "Erro: texto inválido");
                            continue;
                        }

                        String upper = texto.toUpperCase();
                        saida.println("Resultado: " + upper);
                        log(idCliente, "Maiúsculo: " + upper);
                        break;

                    case "4":
                        saida.println("Digite um texto (letras e espaços):");
                        String t = entrada.nextLine();
                        log(idCliente, "Texto recebido: " + t);

                        if (t.trim().isEmpty() || !t.matches("^[a-zA-ZÀ-ÿ ]+$")) {
                            saida.println("Entrada inválida!");
                            log(idCliente, "Erro: texto inválido");
                            continue;
                        }

                        String invertido = new StringBuilder(t).reverse().toString();
                        saida.println("Resultado: " + invertido);
                        log(idCliente, "Invertido: " + invertido);
                        break;

                    case "0":
                        saida.println("Conexão encerrada.");
                        log(idCliente, "Desconectou");
                        cliente.close();
                        return;
                }
            }

        } catch (Exception e) {
            log(idCliente, "Desconectado inesperadamente");
        } finally {
            manager.clienteFinalizado(idCliente);
        }
    }

    private int lerNumero(Scanner entrada, PrintWriter saida, String idCliente, String nome) {
        while (true) {
            try {
                String valor = entrada.nextLine();
                int numero = Integer.parseInt(valor);
                log(idCliente, nome + ": " + numero);
                return numero;
            } catch (Exception e) {
                saida.println("Digite um número válido:");
                log(idCliente, "Erro: número inválido");
            }
        }
    }

    private void log(String id, String msg) {
        System.out.println("[" + id + "] " + msg);
    }
}