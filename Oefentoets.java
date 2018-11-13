package oefentoets;

/**
 *
 * @author Maarten
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Oefentoets extends JFrame
        implements ActionListener {
    
    private JLabel bestand, informatie;
    private JButton openButton1, openButton2, analyseButton;
    private JFileChooser fileChooser;
    private JTextField nameField1, nameField2;
    private JTextArea textArea;
    private BufferedReader inFile1, inFile2;
    private JPanel panel;

    private int X = 400;
    private int Y = 80;
 
    public static void main(String[] args) { 
        Oefentoets frame = new Oefentoets();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.createGUI();
        frame.setVisible(true);
    }
 
    private void createGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());
        
        bestand = new JLabel("Bestand:");
         
        nameField1 = new JTextField(25);
        
        nameField2 = new JTextField(25);
        
        openButton1 = new JButton("Browse");
        openButton1.addActionListener(this);
        
        openButton2 = new JButton("Browse");
        openButton2.addActionListener(this);
        
        analyseButton = new JButton("Analyse");
        analyseButton.addActionListener(this);
        
        informatie = new JLabel("Informatie:");
 
        textArea = new JTextArea(10, 40);
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(X,Y));
        panel.setBackground(Color.white);
                
        window.add(bestand);
        window.add(nameField1);
        window.add(nameField2);
        window.add(openButton1);
        
        window.add(openButton2);
        
        window.add(analyseButton);
        window.add(textArea);
        //window.add(panel);     
        
    }
 
    public void readFiles() throws IOException {
        String[] names = {nameField1.getText(), nameField2.getText()};
        String line1, line2;
        int i = 0;

        try {
            inFile1 = new BufferedReader(new FileReader(names[0]));
            inFile2 = new BufferedReader(new FileReader(names[0]));
            textArea.setText("");
            
            while ((line1 = inFile1.readLine()) != null) {
                line2 = inFile2.readLine();
                
                System.out.println(line1);
                System.out.println(line1);
                
                if (!line1.equals(line2)) {
                    String[] columns1 = line1.split("\t");
                    String[] columns2 = line2.split("\t");

                    for (int n = 0; n < 4; n ++) {
                        if (columns1[n].equals(columns2[n]) == false){
                            textArea.append("\nBestanden komen niet overeen op regel" + (i + 1));
                            textArea.append("\nVerschil in kolom: " + (n + 1));
                        }
                    }
                }
                i++;
            }
            inFile1.close();
            inFile2.close();    
       
        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,
                    "De bestanden komen niet overeen in lengte: " + e.toString());
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "File Error: " + e.toString());
        }  
    }
  
    public void actionPerformed(ActionEvent event) {
        File selectedFile;
        int reply;
        if (event.getSource() == openButton1) {
            fileChooser = new JFileChooser();
            reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                nameField1.setText(selectedFile.getAbsolutePath());
            }
        }
        else if (event.getSource() == openButton2) {
            fileChooser = new JFileChooser();
            reply = fileChooser.showOpenDialog(this);
            if (reply == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                nameField2.setText(selectedFile.getAbsolutePath());
            }
        }
        else if (event.getSource() == analyseButton) {
            if (nameField1.getText() != ""){
                try {
                    readFiles();
                } catch (IOException ex) {
                    Logger.getLogger(Oefentoets.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       }
    }
}

