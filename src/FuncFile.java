import javax.swing.text.AbstractDocument;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FuncFile {
    GUI gui;
    String fileName;
    String filePath;

    public FuncFile(GUI gui) {
        this.gui = gui;
    }

    public void newFile(){
        gui.textPane.setText("");
        gui.setTitle("Untitled");
        fileName = null;
        filePath = null;
    }

    public void open() {
        FileDialog fileDialog = new FileDialog(gui, "Open", FileDialog.LOAD);
        fileDialog.setVisible(true);

        if(fileDialog.getFile() != null) {
            fileName = fileDialog.getFile();
            filePath = fileDialog.getDirectory();
            gui.setTitle(fileName);
        }

        FileReader reader = null;

        try {
            reader = new FileReader(filePath + fileName);

            gui.textPane.read(reader,filePath + fileName);
            StyledDocument styleDoc = gui.textPane.getStyledDocument();
            if (styleDoc instanceof AbstractDocument) {
                gui.doc = (AbstractDocument) styleDoc;
            } else {
                System.err.println("Text pane's document isn't an AbstractDocument!");
                System.exit(-1);
            }
            gui.addListeners();

        } catch (Exception e) {
            System.out.println("FILE NOT OPENED!");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException exception) {
                    System.err.println("Error closing reader");
                    exception.printStackTrace();
                }
            }
        }
    }

    public void save() {
        if (fileName == null) {
            saveAs();
        } else {
            try {
                FileWriter fw = new FileWriter(filePath + fileName);
                fw.write(gui.textPane.getText());
                fw.close();
            } catch (Exception e) {
                System.out.println("SOMETHING WRONG!");
            }
        }
    }

    public void saveAs() {
        FileDialog fileDialog = new FileDialog(gui, "Save As", FileDialog.SAVE);
        fileDialog.setVisible(true);

        if (fileDialog.getFile() != null) {
            fileName = fileDialog.getFile();
            filePath = fileDialog.getDirectory();
            gui.setTitle(fileName);
        }

        try {
            FileWriter fw = new FileWriter(filePath + fileName);
            fw.write(gui.textPane.getText());
            fw.close();
        } catch (Exception e) {
            System.out.println("SOMETHING WRONG!");
        }
    }

    public void exit(){
        System.exit(0);
    }
}
