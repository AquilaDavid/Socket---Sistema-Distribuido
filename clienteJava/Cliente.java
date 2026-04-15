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

            // Thread para receber mensagens do servidor
            new Thread(() -> {
                while (entrada.hasNextLine()) {
                    System.out.println(entrada.nextLine());
                }
            }).start();

            // Enviar dados para o servidor
            while (true) {
                String msg = teclado.nextLine();
                saida.println(msg);
            }

        } catch (Exception e) {
            System.out.println("Erro na conexão com o servidor.");
        }
    }
}