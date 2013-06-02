import model.Contest;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PageLoader;

import java.io.InputStream;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger("main");

    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();

            logger.info("Starting getting list of RFBR contests");
            List<Contest> contests = PageLoader.getRFBRContests();
            logger.info("Success! Got " + contests.size() + " RFBR contests");

            logger.info("Started getting detailed information");
            int i = 0;
            for (Contest contest : contests) {
                logger.info(++i + "...");
                contest = PageLoader.getContestInfo(contest);
            }

            logger.info("Successfully finished!");
        } catch (Exception e) {
            logger.error("Error while executing Main", e);
        }
    }
}
