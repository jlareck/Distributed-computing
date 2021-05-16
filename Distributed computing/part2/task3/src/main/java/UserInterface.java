

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class UserInterface extends JFrame{
    private static Client client = null;
    private static Directory cCnt = null;
    private static File cCt = null;

    private static boolean editMode = false;
    private static boolean directoryMode = true;

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
    private static JTextField tfFileSize = new JTextField(30);

    private static JFrame frame;

    UserInterface(){
        super("File system");
        frame = this;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                client.disconnect();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();

        // Menu
        menuPanel.add(btnAddDirectory);
        btnAddDirectory.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                directoryMode = true;
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
                directoryMode = false;
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
                cCnt =client.directoryFindByName((String) comboDirectory.getSelectedItem());
                directoryMode = true;
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
                cCt = client.fileFindByName((String)comboFile.getSelectedItem());
                directoryMode = false;
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
        filePanel.add(tfFileSize);
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
            if(directoryMode) {
                cCnt.setName(tfDirectoryName.getText());
                if (!client.directoryUpdate(cCnt)) {
                    JOptionPane.showMessageDialog(null, "Error: something went wrong!");
                }
            } else {
                cCt.setName(tfFileName.getText());
                Directory cnt = client.directoryFindByName(tfFileDirectoryName.getText());
                if(cnt == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such directory!");
                    return;
                }
                cCt.setDirectoryId(cnt.getId());
                cCt.setSize(Long.parseLong(tfFileSize.getText()));
                if (!client.fileUpdate(cCt)) {
                    JOptionPane.showMessageDialog(null, "Error: something went wrong!");
                }
            }
        } else {
            if (directoryMode) {
                var directory = new Directory();
                directory.setName(tfDirectoryName.getText());
                if(client.directoryInsert(directory)) {
                    comboDirectory.addItem(directory.getName());
                }
            } else {
                var file = new File();
                file.setName(tfFileName.getText());
                Directory cnt = client.directoryFindByName(tfFileDirectoryName.getText());
                if(cnt == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such directory!");
                    return;
                }
                file.setDirectoryId(cnt.getId());
                file.setSize(Long.parseLong(tfFileSize.getText()));
                if(client.fileInsert(file)) {
                    comboFile.addItem(file.getName());
                }
            }
        }
    }

    private static void delete() {
        if(editMode) {
            if(directoryMode) {
                var list = client.fileFindByDirectoryid(cCnt.getId());
                for(File c: list) {
                    comboFile.removeItem(c.getName());
                    client.fileDelete(c);
                }
                client.directoryDelete(cCnt);
                comboDirectory.removeItem(cCnt.getName());

            } else {
                client.fileDelete(cCt);
                comboFile.removeItem(cCt.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboDirectory.removeAllItems();
        comboFile.removeAllItems();
        var directories = client.directoryAll();
        var files = client.fileAll();
        for(Directory c: directories) {
            comboDirectory.addItem(c.getName());
        }
        for(File c: files) {
            comboFile.addItem(c.getName());
        }
    }

    private static void clearFields() {
        tfDirectoryName.setText("");
        tfFileName.setText("");
        tfFileDirectoryName.setText("");
        tfFileSize.setText("");
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
        Directory cnt = client.directoryFindById(cCt.getDirectoryId());
        tfFileName.setText(cCt.getName());
        tfFileDirectoryName.setText(cnt.getName());
        tfFileSize.setText(String.valueOf(cCt.getSize()));
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        client = new Client("localhost",12345);
        JFrame myWindow = new UserInterface();
        myWindow.setVisible(true);
    }
}