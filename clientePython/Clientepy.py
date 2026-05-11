import socket
import threading

HOST = "10.10.136.173"
PORT = 12345

try:
    cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    cliente.connect((HOST, PORT))

    print("Conectado ao servidor!")

    
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

    
    while True:
        msg = input()
        cliente.sendall((msg + "\n").encode())

        if msg == "0":
            print("Encerrando cliente...")
            break

    cliente.close()

except Exception as e:
    print("Erro na conexão:", e)