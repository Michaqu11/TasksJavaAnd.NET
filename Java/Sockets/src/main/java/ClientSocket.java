import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
    public static void main(String[] args) {
        System.out.println("Klient: Próba podłączenia do serwera");
        try (Socket client = new Socket("localhost", 9797)) {
                try (OutputStream os = client.getOutputStream();
                     InputStream is = client.getInputStream()) {
                    ObjectInputStream ois = new ObjectInputStream(is);
                    //BufferedReader Input = new BufferedReader(new InputStreamReader(is));
                    //PrintStream Output = new PrintStream(os);
                    //ObjectOutputStream oos = new ObjectOutputStream(os);
                    Object buf = ois.readObject();
                    if(buf !=null){
                        System.out.println("Serwer jest: [ "+buf+" ]");
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        int num = 7;
                        oos.writeObject(num);
                        buf = ois.readObject();
                        if(buf !=null) {
                            System.out.println("Serwer jest znowu: [ " + buf + " ]");
                            for (int i = 0; i < num; i++) {
                                Message message = new Message(i + 1, "pies");
                                oos.writeObject(message);
                            }
                            buf = ois.readObject();
                            System.out.println("Serwer mowi: [ " + buf + " ]");
                        }
                    }
                    //Output.println(7);
                   // Message message = new Message(1, "pies");
                    //oos.writeObject(message);
                    /*try {
                        String buf = Input.readLine();
                        if(buf !=null){
                            System.out.println("Klient: Odpowiedź serwera [ "+buf+" ]");
                        }
                        else
                            System.out.println("Klient: Brak odpowiedzi z serwera.");*/
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}