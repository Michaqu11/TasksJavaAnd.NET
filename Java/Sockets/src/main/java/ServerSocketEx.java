import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSocketEx {
    public static void main(String[] args) {
        Integer nThreads;
        if (args.length > 0){
            nThreads = Integer.parseInt(args[0]);
        }
        else{
            nThreads = 8;
        }

        System.out.println("Server is running on port " + 9797 + " and able to manage " + nThreads + " clients");
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        try(ServerSocket serverSocket = new ServerSocket(9797)){
            while(true){
                try{
                    final Socket socket = serverSocket.accept();
                    executorService.submit(() -> {
                        try (InputStream is = socket.getInputStream();
                             OutputStream os = socket.getOutputStream()) {
                            System.out.println("a0s");
                            ObjectOutputStream oos = new ObjectOutputStream(os);
                            //PrintStream Output = new PrintStream(os);
                            System.out.println("a1s");
                            //Output.print("ready");
                            oos.writeObject("ready");
                            ObjectInputStream ois = new ObjectInputStream(is);
                            System.out.println("a2s");
                            //BufferedReader Input = new BufferedReader(new InputStreamReader(is)); //odczyt
                            Object buf = ois.readObject();
                            System.out.println(buf);
                            oos.writeObject("ready");

                            for(int i = 0;i< (int) buf ;i++){
                                Message message = (Message) ois.readObject();
                                System.out.println(message.getNumber() + " " + message.getContent());
                                //System.out.println(message.getContent());
                            }
                            oos.writeObject("finished");
                            //Message message = (Message) ois.readObject();
                            //System.out.println(message.getNumber());
                            //System.out.println(message.getContent());

                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    });
                } catch(IOException e){
                    System.out.println("Problem with accepting client. Shutting down.");
                    System.exit(1);
                }

            }
        } catch(IOException e){
            System.out.println("Problem happend!");
            System.exit(1);
        }

    }
}



