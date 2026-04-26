import socket
import threading

HOST = "localhost"
PORT = 12345

try:
    cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    cliente.connect((HOST, PORT))

    print("Conectado ao servidor!")

    # 🔥 Thread para RECEBER mensagens (igual ao Java)
    def receber():
        while True:
            try:
                data = cliente.recv(1024)
                if not data:
                    break
                print(data.decode(), end="")
            except:
                break

    thread_receber = threading.Thread(target=receber)
    thread_receber.start()

    # 🔥 Loop para ENVIAR mensagens (igual ao teclado do Java)
    while True:
        msg = input()
        cliente.sendall((msg + "\n").encode())

        if msg == "0":
            print("Encerrando cliente...")
            break

    cliente.close()

except Exception as e:
    print("Erro na conexão:", e)