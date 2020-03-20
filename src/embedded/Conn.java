package embedded;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Conn {
    private InputStream serialIn;
    private Integer tax;
    private String portCOM;
    private BufferedReader reader;
    private InputStreamReader isr;

    SerialPort port;

    public Conn(Integer tax, String portCOM) {
        this.tax = tax;
        this.portCOM = portCOM;
        this.initialize();
    }

    private void initialize(){
        try {
            CommPortIdentifier portId = null;
            try {
                portId = CommPortIdentifier.getPortIdentifier(this.portCOM);
            }catch (NoSuchPortException e){
                JOptionPane.showMessageDialog(null, "Porta COM não encontrada.",
                        "Porta COM", JOptionPane.PLAIN_MESSAGE);
            }
            assert portId != null;
            port = (SerialPort) portId.open("Conn", this.tax);
            serialIn = port.getInputStream();
            isr = new InputStreamReader(serialIn);
            port.setSerialPortParams(this.tax,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeIn(){
        try {
            serialIn.close();
            reader.close();
            isr.close();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "Não foi possível fechar porta COM.",
                    "Fechar porta COM", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public String receiveData(){
        String hex = null;

        reader = new BufferedReader(isr);
        try {
            hex = reader.readLine();;
            System.out.println(hex);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIn();
        }

        return hex;
    }

    /*public String receiveData(){
        byte[] buffer = new byte[1];
        int len = -1;
        String hex = "0";
        try {
            for (int i = 1; i < 9; i++) {
                len = serialIn.read(buffer);
                hex = hex + (new String(buffer, 0, len));
                System.out.print((new String(buffer, 0, len)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIn();
        }
        return hex;
    }*/

    /*public String receiveData(){
            byte[] buffer = new byte[1];
            int len = -1;
            String hex = "0";
            try {
                while ((len = serialIn.read(buffer)) > -1){
                    hex = hex + (new String(buffer, 0, len));
                    System.out.print((new String(buffer, 0, len)));
                    if (hex.length() == 9) return hex;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                closeIn();
            }
            return hex;
    }*/
}
