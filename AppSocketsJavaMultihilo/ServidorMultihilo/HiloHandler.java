package ServidorMultihilo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;

public class HiloHandler implements Runnable {
    private final Socket ch;
    PrintWriter out;
    BufferedReader in;
    int clienteId;

    // En el constructor creamos los flujos
    public HiloHandler(Socket ch, int clienteId) throws IOException{
        // Recibe el socket para escuchar conexiones de cliente
        this.ch = ch;
        this.clienteId = clienteId; // Guardar el ID del cliente
        // Establece el stream de salida
        out = new PrintWriter(ch.getOutputStream(), true);
        // Establece el stream de entrada
        in = new BufferedReader(new InputStreamReader(ch.getInputStream()));

        System.out.println("Conexión recibida del cliente #" + clienteId + ": " + ch.getInetAddress().getHostAddress());
    }

    @Override
        public void run(){
            try{
                System.out.println("Cliente #" + clienteId + " inició la recepción de archivos...");
                String pathArchivo = Paths.get("ServidorMultihilo\\archivote.csv").toAbsolutePath().toString();
                File file_in = new File(pathArchivo);
                // Pasar las lineas del archivo al cliente
                FileReader fr;
                fr = new FileReader(file_in);
                BufferedReader br = new BufferedReader(fr);
                String linealeida;
                while ((linealeida = br.readLine()) != null){
                    out.println(linealeida);
                }

                // Enviamos al cliente que el archivo ha sido trasmitiddo
                out.println("EOF");
                br.close();
                fr.close();

                // Leemos la despedida del cliente
                // Código de la recepción de archivos...
                System.out.println("Cliente #" + clienteId + " ha terminado la recepción de archivos.");

                out.close();
                in.close();
                ch.close();
            }catch (IOException ex){
                System.out.println(ex);
            }
        }
}
