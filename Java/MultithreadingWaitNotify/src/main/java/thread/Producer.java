package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Producer implements Runnable {
    private Resource resource;

    public Producer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        Scanner myInput = new Scanner(System.in);
        while(true) {
            System.out.println("wczytaj liczbe: ");
            String text = myInput.nextLine();

            if(text.equals("e")) {
                break;
            }
            else if(isNumeric(text)){
                int numInt = Integer.parseInt(text);
                resource.put(numInt);
            }
        }
        myInput.close();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
