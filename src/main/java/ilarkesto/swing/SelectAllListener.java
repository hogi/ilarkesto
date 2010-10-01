package ilarkesto.swing;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class SelectAllListener implements FocusListener {

    public static final SelectAllListener INSTANCE = new SelectAllListener();

    public void focusGained(FocusEvent e) {
        Component c = e.getComponent();
        if (c == null) return;
        if (c instanceof JTextField) {
            ((JTextField) c).selectAll();
            return;
        }
    }

    public void focusLost(FocusEvent e) {}

}
