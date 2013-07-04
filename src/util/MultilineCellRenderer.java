package util;

import model.Contest;

import javax.swing.*;
import java.awt.*;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class MultilineCellRenderer implements ListCellRenderer {
    private JPanel p;
    private JPanel iconPanel;
    private JLabel l;
    private JTextArea ta;

    public MultilineCellRenderer() {
        p = new JPanel();
        p.setLayout(new BorderLayout());

        // icon
        iconPanel = new JPanel(new BorderLayout());
        l = new JLabel("icon"); // <-- this will be an icon instead of a
        // text
        iconPanel.add(l, BorderLayout.NORTH);
        p.add(iconPanel, BorderLayout.WEST);

        // text
        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        p.add(ta, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(final JList list,
                                                  final Object value, final int index, final boolean isSelected,
                                                  final boolean hasFocus) {
        Contest contest = (Contest) value;

        ta.setText(contest.name);
        int width = list.getWidth();
        // this is just to lure the ta's internal sizing mechanism into action
        if (width > 0)
            ta.setSize(width, Short.MAX_VALUE);
        return p;

    }
}
