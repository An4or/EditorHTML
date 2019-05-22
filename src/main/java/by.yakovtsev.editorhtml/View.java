package by.yakovtsev.editorhtml;

import by.yakovtsev.editorhtml.listeners.FrameListener;
import by.yakovtsev.editorhtml.listeners.TabbedPaneChangeListener;
import by.yakovtsev.editorhtml.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sasha on 25.04.2018.
 */
public class View extends JFrame implements ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    public View(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public Controller getController() {
        return controller;
    }



    public void setController(Controller controller) {
        this.controller = controller;
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
switch (e.getActionCommand()){
    case "Новый":
        controller.createNewDocument();
        break;
    case "Открыть":
        controller.openDocument();
        break;
    case "Сохранить":
        controller.saveDocument();
        break;
    case "Сохранить как...":
        controller.saveDocumentAs();
        break;
    case "Выход":
        controller.exit();
        break;
    case "О программе":
        showAbout();
        break;

}
    }

    public void init() {
        initGui();
        addWindowListener(new FrameListener(this));
        setVisible(true);
    }

    public void exit() {
        controller.exit();
    }

    public void initMenuBar() {
JMenuBar menuBar = new JMenuBar();
MenuHelper.initFileMenu(this, menuBar);
MenuHelper.initEditMenu(this, menuBar);
MenuHelper.initStyleMenu(this, menuBar);
MenuHelper.initAlignMenu(this, menuBar);
MenuHelper.initColorMenu(this, menuBar);
MenuHelper.initFontMenu(this, menuBar);
MenuHelper.initHelpMenu(this, menuBar);
getContentPane().add(menuBar, BorderLayout.NORTH);
    }

    public void initEditor() {
        htmlTextPane.setContentType("text/html");
        JScrollPane scrollPane = new JScrollPane(htmlTextPane);
        tabbedPane.add("HTML",scrollPane);
        JScrollPane scrollPane2 = new JScrollPane(plainTextPane);
        tabbedPane.add("Текст", scrollPane2);
        tabbedPane.setPreferredSize(new Dimension(600, 400));
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

    }

    public void initGui() {
        initMenuBar();
        initEditor();
        pack();
    }

    public void selectedTabChanged() {
        if (tabbedPane.getSelectedIndex() == 0){
            controller.setPlainText(plainTextPane.getText());
        }else if (tabbedPane.getSelectedIndex() == 1){
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }

    public boolean canUndo(){
        return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    public void undo(){
        try{
            undoManager.undo();
        }catch (Exception e){
           ExceptionHandler.log(e);
        }

    }

    public void redo(){
        try{
        undoManager.redo();
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    public boolean isHtmlTabSelected(){
        return tabbedPane.getSelectedIndex() == 0;
    }

    public void selectHtmlTab(){
       tabbedPane.setSelectedIndex(0);
       resetUndo();
    }

    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }

    public void showAbout(){
        JOptionPane.showMessageDialog(getContentPane(), "It hard to be God", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
