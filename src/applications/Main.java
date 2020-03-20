package applications;

import embedded.Conn;

import java.util.Locale;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        Conn test = new Conn(9600,"/dev/cu.usbmodem14101");
        long val = Converter(test.receiveData());

        System.out.println("\n"+val);

        System.exit(0 );

    }

    private static long Converter(String hex){
        String digits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        long val = 0;
        for (int i = 0; i < hex.length(); i++)
        {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
}


