package com.apleben.cryptography.CryptoClassDemo.tools;

import com.apleben.io.tools.FileTreeWalk;
import com.apleben.io.tools.FileTreeWalker;
import com.apleben.io.tools.UnixGlobFileFilter;
import com.apleben.utils.common.EasyCipher;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Encrypts a files in input directory using the custom ciphering algorithm.
 *
 * @author apupeikis
 */
public class CryptoClassTool {
    // the private key."legal protection insurance companies" from the German translate.
    private static final String privateKey = "Rechtsschutzversicherungsgesellschaften";
    private static JTextField keyTextField;
    private static JTextField dirTextField;
    private static String inputDir = "";
    private static String publicKey = "";

    private static JFrame createFrame() {
        final JFrame frame = new JFrame();

        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setLayout(new FormLayout("20px:g, 91px, 33px, 91px, 12px", "20px:g, 27px, p, 12px"));

        final JButton encryptButton = new JButton();
        encryptButton.setName("encryptButton");
        encryptButton.setText("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!inputDir.equals(dirTextField.getText())) inputDir = dirTextField.getText();
                if (!publicKey.equals(keyTextField.getText())) publicKey = keyTextField.getText();

                File path = new File(inputDir);

                if (!path.exists()) {
                    JOptionPane.showMessageDialog(frame, "The path does not exists!");
                    dirTextField.selectAll();
                    dirTextField.requestFocus();
                } else if (publicKey.equals("")) {
                    JOptionPane.showMessageDialog(frame, "The public key should not be null!");
                    keyTextField.requestFocus();
                } else {
                    encryptProcessing();
                    JOptionPane.showMessageDialog(frame, "Success!");
                    dirTextField.setText("");
                    keyTextField.setText("");
                }
            }
        });
        contentPane.add(encryptButton, new CellConstraints(2, 3, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));

        JButton closeButton = new JButton();
        closeButton.setName("closeButton");
        closeButton.setText("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        contentPane.add(closeButton, new CellConstraints(4, 3, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));

        JPanel panel = new JPanel();
        panel.setLayout(new FormLayout("12px, p, 3px, 12px, 20px:g, 12px, p", "p, 6px, p, 20px:g"));

        keyTextField = new JTextField();
        keyTextField.setColumns(8);
        keyTextField.setName("keyTextField");
        keyTextField.setToolTipText("your public key");
        keyTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publicKey = keyTextField.getText();
                encryptButton.requestFocus();
            }
        });
        panel.add(keyTextField, new CellConstraints(5, 3, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));

        JButton dirButton = new JButton();
        dirButton.setName("dirButton");
        dirButton.setText("...");
        dirButton.setToolTipText("open directory with class files");
        dirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select the *.class files directory");
                chooser.setFileFilter(new FileNameExtensionFilter("The directory with *.class files", "class"));
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setCurrentDirectory(new File("."));

                if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    String dir = chooser.getSelectedFile().getParent();
                    dir += "/";
                    dirTextField.setText(dir);
                    inputDir = dir;
                    keyTextField.requestFocus();
                }
            }
        });
        panel.add(dirButton, new CellConstraints(7, 1, 1, 1, CellConstraints.DEFAULT, CellConstraints.CENTER));

        dirTextField = new JTextField();
        dirTextField.setColumns(8);
        dirTextField.setName("dirTextField");
        dirTextField.setToolTipText("location of class files");
        dirTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dir = dirTextField.getText();
                File path = new File(dir);

                if (!path.exists()) {
                    JOptionPane.showMessageDialog(frame, "The path does not exists!");
                    dirTextField.selectAll();
                    dirTextField.requestFocus();
                } else {
                    inputDir = dir;
                    keyTextField.requestFocus();
                }
            }
        });
        panel.add(dirTextField, new CellConstraints(5, 1, 1, 1, CellConstraints.FILL, CellConstraints.CENTER));

        JLabel keyLable = new JLabel();
        keyLable.setName("keyLable");
        keyLable.setText("Public Key Phrase:");
        panel.add(keyLable, new CellConstraints(2, 3, 1, 1, CellConstraints.DEFAULT, CellConstraints.CENTER));

        JLabel dirLabel = new JLabel();
        dirLabel.setName("dirLabel");
        dirLabel.setText("Class Files Directory:");
        panel.add(dirLabel, new CellConstraints(1, 1, 3, 1, CellConstraints.DEFAULT, CellConstraints.CENTER));
        panel.setBorder(new TitledBorder("Properties"));
        panel.setPreferredSize(new Dimension(100, 100));
        contentPane.add(panel, new CellConstraints(1, 1, 5, 1, CellConstraints.FILL, CellConstraints.FILL));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Crypto Class Tool");
        frame.setBounds(new Rectangle(500, 0, 500, 313));
        frame.setLocationRelativeTo(null);

        return frame;
    }

    private static void encryptProcessing() {
        // creating easy cipher with the private key
        EasyCipher cipher = new EasyCipher(privateKey);
        // generating the secret pass phrase with the public key
        final String passPhrase = cipher.encrypt(publicKey);
        final int key = parseString(passPhrase);

        try {
            // Loads all class files found in the directory "inputDir"
            File classDir = new File(inputDir);
            FileTreeWalker walker = new FileTreeWalker(classDir,
                    new UnixGlobFileFilter("*.class"));
            walker.walk(new FileTreeWalk() {
                @Override
                public void walk(File path) throws IOException {
                    FileInputStream in = null;
                    FileOutputStream out = null;
                    FileChannel channel = null;
                    try {
                        // constructing an out file with ".pinpuk" suffixes instead of ".class"
                        String outFileName = path.getName().substring(0, path.getName().lastIndexOf('.')) + ".pinpuk";
                        String outFile = path.getParent() + "/" + outFileName;

                        in = new FileInputStream(path);
                        out = new FileOutputStream(outFile);
                        channel = in.getChannel();

                        int length = (int) channel.size();
                        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

                        for (int p = 0; p < length; p++) {
                            int ch = buffer.get(p);
                            byte c = (byte) (ch + key);
                            out.write(c);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (channel != null) {
                            channel.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }
                }
            });

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /*
     * Dummies and naive string parsing into the integer value
     */
    private static int parseString(final String passPhrase) {
        int result = 0;
        for (int i = 0; i < passPhrase.length(); i++) {
            int temp = (int) passPhrase.charAt(i);
            result += temp;
        }
        return result;
    }

    public static void main(final String... args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CryptoClassTool.createFrame().setVisible(true);
            }
        });
    }

}
