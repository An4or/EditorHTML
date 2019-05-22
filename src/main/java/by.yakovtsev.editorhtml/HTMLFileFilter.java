package by.yakovtsev.editorhtml;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Sasha on 30.04.2018.
 */
public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
        return true;
        else if (f.getName().toLowerCase().endsWith(".html") || f.getName().toLowerCase().endsWith(".htm"))
            return true;
        return false;
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
