package form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class SettingsForm {
    private JTextField complianceTextField;
    private JButton okButton;
    private JPanel panel1;
    private final Logger logger = LoggerFactory.getLogger(SettingsForm.class);

    public SettingsForm(final JFrame frame, final ContestListForm contestListForm) {
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (complianceTextField.getText() != null && !complianceTextField.getText().trim().equals("")) {
                        double border = Double.parseDouble(complianceTextField.getText());
                        if (border <= 0 || border >= 1) {
                            showError();
                        } else {
                            PageLoader.setComplianceLowerBorder(border);
                            contestListForm.refreshModel();
                            frame.dispose();
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error setting compliance border", e);
                    frame.dispose();
                } finally {
                }
            }
        });
    }

    public void showError() {
        JFrame frame = new JFrame("Ошибка");
        ErrorDialog dialog = new ErrorDialog("Значение коэффициента должно быть в интервале от 0 до 1", frame);
        frame.setContentPane(dialog.getPanel1());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(400, 100);
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
