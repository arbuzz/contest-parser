package form;

import model.Contest;
import util.HttpClientHolder;
import util.PageLoader;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class ContestListForm {
    private JList list1;
    private JPanel panel1;

    private DefaultListModel model;

    public ContestListForm() {
    }

    public void loadContests() {
        List<Contest> contests = PageLoader.getRFBRContests();

        model = new DefaultListModel();
        for (Contest contest : contests) {
            model.addElement(contest);
        }

        list1.setModel(model);
        list1.addMouseListener(new ContestListMouseAdapter());
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public class ContestListMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2) {
                int index = list1.locationToIndex(mouseEvent.getPoint());
                Contest contest = (Contest) list1.getModel().getElementAt(index);

                JFrame frame = new JFrame("Конкурс");
                ContestInfoForm form = new ContestInfoForm(contest);
                frame.setContentPane(form.getPanel1());
                frame.pack();
                frame.setVisible(true);
            }
        }
    }
}
