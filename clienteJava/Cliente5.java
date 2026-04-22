package clienteJava;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente5 {
    public static void main(String[] args) throws Exception {

        // Cada instância recebe um número pra identificar no console
        String id = args.length > 0 ? args[0] : "?";

        Socket socket = new Socket("localhost", 12345);
        Scanner entrada = new Scanner(socket.getInputStream());
        PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

        System.out.println("[Cliente " + id + "] Conectado!");

        // Fica em loop respondendo automaticamente o menu
        while (entrada.hasNextLine()) {
            String linha = entrada.nextLine();
            System.out.println("[Cliente " + id + "] << " + linha);

            // Quando o menu aparecer, escolhe uma opção automaticamente
            if (linha.contains("================")) {
                saida.println("3"); // escolhe Maiúsculo
            } else if (linha.contains("Digite um texto")) {
                saida.println("teste do cliente " + id);
            }
        }

        socket.close();
    }
}
