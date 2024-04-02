package ServidorMultihilo;
import java.io.IOException;
import java.net.ServerSocket;

public class ServidorMultihilo{

    public static void main(String[] args){
        // Contador de clientes
        int count = 0;
        
        // Establece el puerto a utilizar
        int puerto = 8080;
        // Crea un socket de servidor
        try (ServerSocket ss = new ServerSocket(puerto)){
            System.out.println("Servidor escuchado en el puerto: "+ puerto + "...");
            System.out.println("Clientes conectados: " + count);
            // El servidor va a escuchar conexiones hasta presionar Ctrl+C
            // Cada cliente se manda a un nuevo hilo por lo que siempre
            // estara dispuesto a recibir nuevos clientes
            while (true) {
                count++; // Incrementar el contador de clientes
                // El servidor envía un archivo grande a cada cliente
                HiloHandler cliente = new HiloHandler(ss.accept(), count);
                Thread h1 = new Thread(cliente);
                h1.start();
                System.out.println("Clientes conectados: " + count);
            }
        } catch(IOException ex){
            System.out.println(ex);
        }
    }
}