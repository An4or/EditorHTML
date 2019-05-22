package by.yakovtsev.editorhtml.actions;



import by.yakovtsev.editorhtml.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Sasha on 29.04.2018.
 */
public class RedoAction extends AbstractAction {
    private View view;

    public RedoAction(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
view.redo();
    }
}
