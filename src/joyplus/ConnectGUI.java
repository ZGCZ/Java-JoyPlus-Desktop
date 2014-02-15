package joyplus;

import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class ConnectGUI extends JFrame {
    int gameId;
    
    public ConnectGUI(int gameId) {
        this.gameId = gameId;
        setTitle("Connect");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        createUI();
    }
    
    private void createUI() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);

        String serverIP = JoyPlus.getLocalNetworkIP();
        JLabel iplabel = new JLabel("IP: " + serverIP + ":31415 ID: " + gameId);
        iplabel.setBounds(10, 60, 300, 30);
        
        panel.add(iplabel);
        
        encode(panel, serverIP, gameId);
    }
    
    public void encode(JPanel panel, String ip, int gameId) {
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();
        byte[] b = null;
        try {
            // Convert a string to UTF-8 bytes in a ByteBuffer
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("{\"ip\":\"" + ip +
                    "\", \"port\": \"31415\", \"id\": \"" + gameId + "\"}"));
            b = bbuf.array();
        } catch (CharacterCodingException e) {
            System.out.println(e.getMessage());
        }

        String data;
        try {
            data = new String(b, "UTF-8");
            // get a byte matrix for the data
            BitMatrix matrix = null;
            int h = 100;
            int w = 100;
            com.google.zxing.Writer writer = new MultiFormatWriter();
            try {
                Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(2);
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                matrix = writer.encode(data,
                com.google.zxing.BarcodeFormat.QR_CODE, w, h, hints);
            } catch (com.google.zxing.WriterException e) {
                System.out.println(e.getMessage());
            }

            // change this path to match yours (this is my mac home folder, you can use: c:\\qr_png.png if you are on windows)
            String filePath = "qr.png";
            File file = new File(filePath);
            try {
                MatrixToImageWriter.writeToFile(matrix, "PNG", file);
                System.out.println("printing to " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            
            JLabel pic = new JLabel();
            pic.setSize(300, 300);
            ImageIcon test = new ImageIcon("qr.png");   
            pic.setIcon(test);  
            panel.add(pic);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
