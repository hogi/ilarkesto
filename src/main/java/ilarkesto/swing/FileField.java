package ilarkesto.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileField extends JPanel {

    public static void main(String[] args) {
        FileField ff = createForDirectory();
        Swing.showInJFrame(ff);
    }

    private JFileChooser fileChooser;
    private JTextField textField;

    public FileField() {
        super(new BorderLayout());

        textField = new JTextField(30);
        JButton selectFileButton = new JButton("...");
        selectFileButton.addActionListener(new SelectActionListener());

        add(textField, BorderLayout.CENTER);
        add(selectFileButton, BorderLayout.EAST);

        fileChooser = new JFileChooser();
    }

    public static FileField createForDirectory() {
        FileField f = new FileField();
        f.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return f;
    }

    public static FileField createForFile() {
        FileField f = new FileField();
        f.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return f;
    }

    public void select() {
        File file = getFile();
        if (file != null) fileChooser.setSelectedFile(file);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showDialog(getFileChooser(), "OK")) {
            setFile(fileChooser.getSelectedFile());
        }
    }

    public void setFile(File file) {
        textField.setText(file==null ? null : file.getPath());
    }

    public File getFile() {
        String path = getPath();
        return path == null ? null : new File(path);
    }

    public String getPath() {
        String path = textField.getText().trim();
        if (path.length() == 0) return null;
        return path;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    class SelectActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            select();
        }
    }

}
