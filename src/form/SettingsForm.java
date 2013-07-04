package form;

import model.InitPacket;
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
 * @author Krivinchenko Oxana
 */
public class SettingsForm {
    private JTextField complianceTextField;
    private JButton okButton;
    private JPanel panel1;
    private JTextField periodTextField;
    private JLabel lastUpdateLabel;
    private final Logger logger = LoggerFactory.getLogger(SettingsForm.class);

    public SettingsForm(final JFrame frame, final ContestListForm contestListForm) {
        complianceTextField.setText(Double.toString(PageLoader.getComplianceLowerBorder()));
        periodTextField.setText(Integer.toString(InitPacket.getInstance().updateFreq));
        lastUpdateLabel.setText("Последнее обновление: " + InitPacket.getInstance().lastUpdate);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (complianceTextField.getText() != null && !complianceTextField.getText().equals("")) {
                        double border = 0;
                        try {
                            border = Double.parseDouble(complianceTextField.getText());
                        } catch (Exception e1) {
                            logger.error("Error parsing compliance", e1);
                            showError("Неверное значение коэффициента");
                        }
                        if (border < 0 || border >= 1) {
                            showError("Значение коэффициента должно быть в интервале от 0 до 1");
                        } else {
                            PageLoader.setComplianceLowerBorder(border);
                            contestListForm.refreshModel();
                            frame.dispose();
                        }
                    }
                    if (periodTextField.getText() != null && !periodTextField.getText().equals("")) {
                        int period = 0;
                        try {
                            period = Integer.parseInt(periodTextField.getText());
                        } catch (Exception e1) {
                            logger.error("Error parsing period", e1);
                            showError("Неверное значение периода обновления");
                            return;
                        }
                        if (period < 1) {
                            showError("Период должен быть больше либо равен 1");
                            return;
                        } else {
                            InitPacket.getInstance().updateFreq = period;
                            InitPacket.getInstance().save();
                            frame.dispose();
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error applying settings", e);
                    frame.dispose();
                } finally {
                }
            }
        });
    }

    public void showError(String message) {
        JFrame frame = new JFrame("Ошибка");
        ErrorDialog dialog = new ErrorDialog(message, frame);
        frame.setContentPane(dialog.getPanel1());
        frame.pack();
        frame.setVisible(true);
        frame.setSize(400, 100);
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
