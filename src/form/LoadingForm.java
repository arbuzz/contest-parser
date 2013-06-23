package form;

import util.Util;

import javax.swing.*;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class LoadingForm {
    private JPanel panel1;
    private JLabel label1;

    public JPanel getPanel1() {
        return panel1;
    }

    public void log() throws Exception {
        String[] query = Util.getQuery();
        StringBuilder sb = new StringBuilder();
        for (String queryString : query) {
            sb.append(queryString)
                    .append(" ");
        }
        label1.setText(sb.toString());
    }
}
