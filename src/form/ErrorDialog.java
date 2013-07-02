package form;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class ErrorDialog {
    private JLabel errorText;
    private JButton okButton;
    private JPanel panel1;

    public ErrorDialog(String error, final JFrame frame) {
        errorText.setText(error);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
