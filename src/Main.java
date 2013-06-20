import form.ContestListForm;
import model.Contest;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PageLoader;

import javax.swing.*;
import java.io.InputStream;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger("main");

    private static JFrame listFrame;

    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();

            ContestListForm form = new ContestListForm();

            listFrame = new JFrame("Конкурсы");
            listFrame.setContentPane(form.getPanel1());
            listFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            listFrame.pack();
            listFrame.setVisible(true);

            form.loadContests();

//            logger.info("Starting getting list of RFBR contests");
//            List<Contest> contests = PageLoader.getRFBRContests();
//            logger.info("Success! Got " + contests.size() + " RFBR contests");
//
//            logger.info("Started getting detailed information");
//            int i = 0;
//            for (Contest contest : contests) {
//                logger.info(++i + "...");
//                contest = PageLoader.getContestInfo(contest);
//            }
//
//            logger.info("Successfully finished!");

        } catch (Exception e) {
            logger.error("Error while executing Main", e);
        }
    }
}
