package util;

import com.google.gson.Gson;
import model.Contest;
import model.InitPacket;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This code is brought to you by
 *
 * @author Krivinchenko Oxana
 */
public class PageLoader {

    private static final Logger logger = LoggerFactory.getLogger("PageLogader");

    private static final String LINK = "http://www.rfbr.ru/rffi/ru/contest_search?query=&CONTEST_STATUS_ID=1&CONTEST_TYPE=-1&CONTEST_YEAR=-1&CONTEST_ITEMS=100";
    private static final String LINK_PREFIX = "http://www.rfbr.ru";

    private static final String LIST_ELEMENT_CLASS = "l-3 lfc d-block";
    private static final String ARTICLE_CLASS = "article";

    public static final String CONTESTS_FOLDER = "contests/";

    private static double complianceLowerBorder = 0;

    public static List<Contest> getRFBRContests() {
        InitPacket packet = InitPacket.getInstance();
        if (packet.contests == null || packet.contests.size() == 0) {
            packet.contests = getRFBRContestsFromWeb();
            packet.saveContests();
        }

        return packet.contests;
    }

    public static List<Contest> getRFBRContestsFromWeb() {
        try {
            Document doc = Jsoup.connect(LINK).get();
            List<Contest> contests = new ArrayList<Contest>();
            Elements listElements = doc.getElementsByAttributeValue("class", LIST_ELEMENT_CLASS);
            if (listElements.size() == 0) {
                logger.info("RFBR contests not found");
                return null;
            }
            Iterator<Element> iterator = listElements.iterator();
            while (iterator.hasNext()) {
                Element el = iterator.next();
                if (el.children().size() == 0)
                    continue;
                Element child = el.child(0);
                Contest contest = new Contest();
                contest.link = child.attr("href");
                contest.name = ((TextNode) child.childNode(0)).getWholeText();
                getContestInfo(contest);
                contests.add(contest);
            }

            return contests;
        } catch (Exception e) {
            logger.error("Error getting RFBR contest list", e);
        }
        return null;
    }

    public static Contest getContestInfo(Contest contest) {
        try {
            Document doc = Jsoup.connect(LINK_PREFIX + contest.link).get();
            Elements elements = doc.getElementsByAttributeValue("class", ARTICLE_CLASS);
            if (elements.size() == 0) {
                logger.error("RFBR contest " + contest.name + " article is unable to find");
                return contest;
            }
            StringBuilder articleText = new StringBuilder();

            for (Element articleElement : elements) {
                articleText.append(getElementValue(articleElement));
            }

            contest.article = articleText.toString();
            contest.saveContestInfo();
        } catch (Exception e) {
            logger.error("Error getting RFBR contest " + contest.name, e);
        }
        return contest;
    }

    public static String getElementValue(Element element) {
        StringBuilder wholeText = new StringBuilder();
        for (Element child : element.children()) {
            if (child.childNodes() != null) {
                for (Node node : child.childNodes()) {
                    if (node instanceof TextNode) {
                        TextNode textNode = (TextNode) node;
                        wholeText.append(textNode.getWholeText());
                        wholeText.append("\n");
                    } else if (node instanceof Element) {
                        Element nodeElement = (Element) node;
                        wholeText.append(getElementValue(nodeElement));
                        wholeText.append("\n");
                    }
                }
            }
        }
        return wholeText.toString();
    }

    public static void setComplianceLowerBorder(double border) {
        complianceLowerBorder = border;
    }

    public static double getComplianceLowerBorder() {
        return complianceLowerBorder;
    }
}
