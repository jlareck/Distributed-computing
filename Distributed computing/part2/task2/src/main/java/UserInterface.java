
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class UserInterface extends JFrame{
    private static Directory cCnt = null;
    private static File cCt = null;

    private static boolean editMode = false;
    private static boolean DirectoryMode = true;

    private static JButton btnAddDirectory = new JButton("Add Directory");
    private static JButton btnAddFile= new JButton("Add File");
    private static JButton btnEdit= new JButton("Edit Data");
    private static JButton btnCancel= new JButton("Cancel");
    private static JButton btnSave= new JButton("Save");
    private static JButton btnDelete= new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box filePanel = Box.createVerticalBox();
    private static Box directoryPanel = Box.createVerticalBox();

    private static JComboBox comboDirectory = new JComboBox();
    private static JComboBox comboFile = new JComboBox();

    private static JTextField tfDirectoryName = new JTextField(30);
    private static JTextField tfFileName = new JTextField(30);
    private static JTextField tfFileDirectoryName = new JTextField(30);
    private static JTextField tfFilesize = new JTextField(30);

    private static JFrame frame;

    UserInterface(){
        super("World Map");
        frame = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                DBConnection.closeConnection();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();

        // Menu
        menuPanel.add(btnAddDirectory);
        btnAddDirectory.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                DirectoryMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                directoryPanel.setVisible(true);
                filePanel.setVisible(false);
                actionPanel.setVisible(true);
                //setContentPane(box);
                pack();
            }
        });
        menuPanel.add(btnAddFile);
        btnAddFile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                DirectoryMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                directoryPanel.setVisible(false);
                filePanel.setVisible(true);
                actionPanel.setVisible(true);
                //setContentPane(box);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                directoryPanel.setVisible(false);
                filePanel.setVisible(false);
                actionPanel.setVisible(true);
                //setContentPane(box);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Directory:"));
        comboPanel.add(comboDirectory);
        comboDirectory.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String name = (String)comboDirectory.getSelectedItem();
                cCnt =DirectoryDAO.findByName((String) comboDirectory.getSelectedItem());
                DirectoryMode = true;
                directoryPanel.setVisible(true);
                filePanel.setVisible(false);
                fillDirectoryFields();
                //setContentPane(box);
                pack();
            }
        });
        comboPanel.add(new JLabel("File:"));
        comboPanel.add(comboFile);
        comboFile.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String name = (String)comboFile.getSelectedItem();
                cCt = FileDAO.findByName((String)comboFile.getSelectedItem());
                DirectoryMode = false;
                directoryPanel.setVisible(false);
                filePanel.setVisible(true);
                fillFileFields();
                //setContentPane(box);
                pack();
            }
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        // File Fields
        filePanel.add(new JLabel("Name:"));
        filePanel.add(tfFileName);
        filePanel.add(new JLabel("Directory Name:"));
        filePanel.add(tfFileDirectoryName);
        filePanel.add(new JLabel("Size:"));
        filePanel.add(tfFilesize);
        filePanel.setVisible(false);

        // Directory Fields
        directoryPanel.add(new JLabel("Name:"));
        directoryPanel.add(tfDirectoryName);
        directoryPanel.setVisible(false);

        // Action Bar
        actionPanel.add(btnSave);
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                delete();
            }
        });
        actionPanel.add(btnCancel);
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                directoryPanel.setVisible(false);
                filePanel.setVisible(false);
                actionPanel.setVisible(false);
                //setContentPane(box);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(directoryPanel);
        box.add(filePanel);
        box.add(actionPanel);
        setContentPane(box);
        pack();
    }

    private static void save() {
        if(editMode) {
            if(DirectoryMode) {
                cCnt.setName(tfDirectoryName.getText());
                if (!DirectoryDAO.update(cCnt)) {
                    JOptionPane.showMessageDialog(null, "Error: something went wrong!");
                }
            } else {
                cCt.setName(tfFileName.getText());
                Directory cnt = DirectoryDAO.findByName(tfFileDirectoryName.getText());
                if(cnt == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such Directory!");
                    return;
                }
                cCt.setDirectoryId(cnt.getId());
                cCt.setSize(Long.parseLong(tfFilesize.getText()));
                if (!FileDAO.update(cCt)) {
                    JOptionPane.showMessageDialog(null, "Error: something went wrong!");
                }
            }
        } else {
            if (DirectoryMode) {
                var Directory = new Directory();
                Directory.setName(tfDirectoryName.getText());
                if(DirectoryDAO.insert(Directory)) {
                    comboDirectory.addItem(Directory.getName());
                }
            } else {
                var File = new File();
                File.setName(tfFileName.getText());
                Directory cnt = DirectoryDAO.findByName(tfFileDirectoryName.getText());
                if(cnt == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such Directory!");
                    return;
                }
                File.setDirectoryId(cnt.getId());
                File.setSize(Long.parseLong(tfFilesize.getText()));
                if(FileDAO.insert(File)) {
                    comboFile.addItem(File.getName());
                }
            }
        }
    }

    private static void delete() {
        if(editMode) {
            if(DirectoryMode) {
                var list = FileDAO.findByDirectoryId(cCnt.getId());
                for(File c: list) {
                    comboFile.removeItem(c.getName());
                    FileDAO.delete(c);
                }
                DirectoryDAO.delete(cCnt);
                comboDirectory.removeItem(cCnt.getName());

            } else {
                FileDAO.delete(cCt);
                comboFile.removeItem(cCt.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboDirectory.removeAllItems();
        comboFile.removeAllItems();
        var countries = DirectoryDAO.findAll();
        var cities = FileDAO.findAll();
        for(Directory c: countries) {
            comboDirectory.addItem(c.getName());
        }
        for(File c: cities) {
            comboFile.addItem(c.getName());
        }
    }

    private static void clearFields() {
        tfDirectoryName.setText("");
        tfFileName.setText("");
        tfFileDirectoryName.setText("");
        tfFilesize.setText("");
        cCnt = null;
        cCt = null;
    }

    private static void fillDirectoryFields() {
        if (cCnt == null)
            return;
        tfDirectoryName.setText(cCnt.getName());
    }
    private static void fillFileFields() {
        if(cCt == null)
            return;
        Directory cnt = DirectoryDAO.findById(cCt.getDirectoryId());
        tfFileName.setText(cCt.getName());
        tfFileDirectoryName.setText(cnt.getName());
        tfFilesize.setText(String.valueOf(cCt.getSize()));
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        JFrame myWindow = new UserInterface();
        myWindow.setVisible(true);
    }
}