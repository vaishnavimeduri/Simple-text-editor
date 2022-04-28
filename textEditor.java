import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class textEditor extends JFrame implements ActionListener {
    JTextArea textarea;
    JScrollPane scrollPane;
    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenu editmenu;
    JMenuItem undoItem;
    JMenuItem redoItem;
    UndoManager undo1 = new UndoManager();

    textEditor() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Simple Text Editor");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

<<<<<<< HEAD
        textarea = new JTextArea();
        // textarea.setPreferredSize(new Dimension(450,450));
        textarea.getDocument().addUndoableEditListener(
=======
        textarea=new JTextArea();
       textarea.getDocument().addUndoableEditListener(
>>>>>>> 4690e9a8e53755c96bd766be3fccef5d97e993af
                new UndoableEditListener() {
                    public void undoableEditHappened(UndoableEditEvent e1) {
                        undo1.addEdit(e1.getEdit());
                    }
                });
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setFont(new Font("Arial", Font.PLAIN, 20));

        scrollPane = new JScrollPane(textarea);
        scrollPane.setPreferredSize(new Dimension(450, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel("Font: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textarea.setFont(
                        new Font(textarea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
            }
        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontBox = new JComboBox(fonts);
        fontBox.addActionListener((this));
        fontBox.setSelectedItem("Arial");

        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");

        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        editmenu = new JMenu("Edit");

        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");

        undoItem.addActionListener(this);
        redoItem.addActionListener(this);

        editmenu.add(undoItem);
        editmenu.add(redoItem);

        menuBar.add(editmenu);

        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fontColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "choose a color", Color.BLACK);
            textarea.setForeground(color);
        }
        if (e.getSource() == fontBox) {
            textarea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textarea.getFont().getSize()));
        }
        if (e.getSource() == openItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()) {
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine() + "\n";
                            textarea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {

                    e1.printStackTrace();
                } finally {
                    fileIn.close();
                }
            }

        }
        if (e.getSource() == saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter fileOut = null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textarea.getText());
                } catch (FileNotFoundException e1) {

                    e1.printStackTrace();
                } finally {
                    fileOut.close();
                }

            }
        }
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
        if (e.getSource() == undoItem) {
            try {
                undo1.undo();
            } catch (CannotRedoException cre) {
                cre.printStackTrace();
            }
        }
        if (e.getSource() == redoItem) {
            try {
                undo1.redo();
            } catch (CannotRedoException cre) {
                cre.printStackTrace();
            }
        }
    }
}