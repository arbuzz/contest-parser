package form;

import model.Contest;
import util.PageLoader;

import javax.swing.*;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class ContestInfoForm {
    private JPanel panel1;
    private JTextArea textArea1;

    private Contest contest;

    public ContestInfoForm(Contest contest) {
        this.contest = contest;

        textArea1.append("Загрузка...");
        textArea1.setEditable(false);

        load();
    }

    private void load() {
        contest = PageLoader.getContestInfo(contest);

        textArea1.setText(contest.article);
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
