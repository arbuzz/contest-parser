package model;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PageLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Olshanikov Konstantin
 */
public class InitPacket {

    private static final Logger logger = LoggerFactory.getLogger(InitPacket.class);

    public static final String LIST_FILE_NAME = "list.json";

    public List<Contest> contests;

    public String lastUpdate;

    public int updateFreq;

    private static DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    private static InitPacket instance;

    public static InitPacket getInstance() {
        if (instance == null) {
            try {
                Gson gson = new Gson();
                File file = new File(LIST_FILE_NAME);
                if (file.exists()) {
                    FileReader reader = new FileReader(file);
                    instance = gson.fromJson(reader, InitPacket.class);
                }

                if (instance != null && instance.contests != null) {
                    for (Contest contest : instance.contests) {
                        if (!contest.getArticleFromFile()) {
                            PageLoader.getContestInfo(contest);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error deserializing InitPacket", e);
                instance = new InitPacket();
            }
            if (instance == null) {
                instance = new InitPacket();
            } else {
                try {
                    Date date = format.parse(instance.lastUpdate);
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, -instance.updateFreq);
                    if (cal.getTime().after(date)) {
                        reload();
                    }
                } catch (Exception e) {
                    reload();
                }
            }
        }
        return instance;
    }

    private static void reload() {
        logger.info("Reloading!");
        if (instance == null) {
            instance = new InitPacket();
        }
        instance.contests = PageLoader.getRFBRContestsFromWeb();
        instance.save();
        if (instance.contests != null) {
            for (Contest contest : instance.contests) {
                PageLoader.getContestInfo(contest);
            }
        }
    }

    public void save() {
        File file = new File(LIST_FILE_NAME);

        Gson gson = new Gson();
        this.lastUpdate = format.format(new Date());
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(this, writer);
            writer.close();
        } catch (Exception e) {
            logger.error("Error writing to file contests", e);
        }
    }
}
