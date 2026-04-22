package clienteJava;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 12345);

            Scanner teclado = new Scanner(System.in);
            Scanner entrada = new Scanner(socket.getInputStream());
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);

            // Thread separada para receber mensagens do servidor
            // sem bloquear o envio do usuário
            new Thread(() -> {
                while (entrada.hasNextLine()) {
                    System.out.println(entrada.nextLine());
                }
            }).start();

            // Envia comandos para o servidor
            while (teclado.hasNextLine()) {
                String msg = teclado.nextLine();
                saida.println(msg);

                // CORREÇÃO: encerra o cliente junto quando o usuário digita 0
                if (msg.equals("0")) {
                    System.out.println("Encerrando cliente...");
                    break;
                }
            }

            teclado.close();
            saida.close();
            socket.close();

        } catch (Exception e) {
            System.out.println("Erro na conexão com o servidor.");
        }
    }
}
