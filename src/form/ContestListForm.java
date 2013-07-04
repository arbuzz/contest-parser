package form;

import model.Contest;
import util.HttpClientHolder;
import util.MultilineCellRenderer;
import util.PageLoader;
import util.Util;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class ContestListForm {
    private JPanel panel1;
    private JButton settingsButton;
    private JTable table1;

    private List<Contest> contests;

    public ContestListForm() {
        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame settings = new JFrame("Настройки");
                SettingsForm form = new SettingsForm(settings, ContestListForm.this);
                settings.setContentPane(form.getPanel1());
                settings.pack();
                settings.setVisible(true);
                settings.setSize(400, 150);
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

        table1.setModel(new ContestTableModel(contests));
        table1.addMouseListener(new ContestListMouseAdapter());
    }

    public void refreshModel() {
        List<Contest> contests = new ArrayList<Contest>();

        for (Contest contest : this.contests) {
            if (contest.getCompliance() >= PageLoader.getComplianceLowerBorder())
                contests.add(contest);
        }

        table1.setModel(new ContestTableModel(contests));
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public class ContestListMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2) {
                int row = table1.getSelectedRow();

                Contest contest = (Contest) table1.getModel().getValueAt(row, 0);

                JFrame frame = new JFrame("Конкурс");
                ContestInfoForm form = new ContestInfoForm(contest);
                frame.setContentPane(form.getPanel1());
                frame.pack();
                frame.setVisible(true);
            }
        }
    }

    public class ContestTableModel implements TableModel {

        private List<Contest> items;

        public ContestTableModel(List<Contest> items) {
            this.items = items;
        }

        @Override
        public int getRowCount() {
            return items.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public String getColumnName(int i) {
            if (i == 0)
                return "Название";
            else if (i == 1)
                return "Коэффициент";
            else
                return "Проводится до";
        }

        @Override
        public Class<?> getColumnClass(int i) {
            if (i == 0)
                return Contest.class;
            return String.class;
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Contest contest = items.get(row);
            if (col == 0) {
                return contest;
            } if (col == 1) {
                return Double.toString(contest.getCompliance());
            } else {
                return contest.date;
            }
        }

        @Override
        public void setValueAt(Object o, int i, int i1) {

        }

        @Override
        public void addTableModelListener(TableModelListener tableModelListener) {

        }

        @Override
        public void removeTableModelListener(TableModelListener tableModelListener) {

        }
    }
}
