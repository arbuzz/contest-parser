package form;

import model.Contest;
import util.HttpClientHolder;
import util.PageLoader;
import util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class ContestListForm {
    private JList list1;
    private JPanel panel1;
    private JButton settingsButton;

    private DefaultListModel model;
    private List<Contest> contests;

    public ContestListForm() {
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame settings = new JFrame("Настройки");
                SettingsForm form = new SettingsForm(settings, ContestListForm.this);
                settings.setContentPane(form.getPanel1());
                settings.pack();
                settings.setVisible(true);
                settings.setSize(400, 100);
            }
        });
    }

    public void loadContests() {
        contests = PageLoader.getRFBRContests();

        String[] query = null;
        try {
            query = Util.getQuery();
        } catch (Exception e) {
            return;
        }
        for (Contest contest : contests) {
            contest = PageLoader.getContestInfo(contest);
            contest.calculateRate(query);
        }

        Collections.sort(contests, new Contest.RateComparator());
        refreshModel();

        list1.addMouseListener(new ContestListMouseAdapter());
    }

    public void refreshModel() {
        model = new DefaultListModel();
        for (Contest contest : contests) {
            if (contest.getCompliance() > PageLoader.getComplianceLowerBorder())
                model.addElement(contest);
        }

        list1.setModel(model);
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
