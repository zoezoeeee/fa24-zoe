package ngrams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import edu.princeton.cs.algs4.In;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;
    TreeMap<String, List<Pair>> wordMap = new TreeMap<>();
    TimeSeries countMap = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In inWords = new In(wordsFilename);
        while (inWords.hasNextLine()) {
            String line1 = inWords.readLine();
            String[] values1 = line1.split("\t");
            String word = values1[0];
            int year = Integer.parseInt(values1[1]);
            double count = Double.parseDouble(values1[2]);
            wordMap.putIfAbsent(word, new ArrayList<>());
            wordMap.get(word).add(new Pair(year, count));
        }

        In inCounts = new In(countsFilename);
        while (inCounts.hasNextLine()) {
            String line2 = inCounts.readLine();
            String[] values2 = line2.split(",");
            int year = Integer.parseInt(values2[0]);
            double count = Double.parseDouble(values2[1]);
            countMap.put(year, count);
        }
        inWords.close();
        inCounts.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries historyTS = new TimeSeries();

        // Loop through list of pairs
        for (Pair pair : wordMap.get(word)) {
            if (pair.year() >= startYear && pair.year() <= endYear) {
                historyTS.put(pair.year(), pair.count());
            }
        }
        return historyTS;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries historyTS = new TimeSeries();

        // Loop through list of pairs
        for (Pair pair : wordMap.get(word)) {
            historyTS.put(pair.year(), pair.count());
        }
        return historyTS;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countMap, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries count = countHistory(word, startYear, endYear);
        TimeSeries total = new TimeSeries(totalCountHistory(), count.firstKey(), count.lastKey());
        return count.dividedBy(total);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries count = countHistory(word);
        TimeSeries total = new TimeSeries(totalCountHistory(), count.firstKey(), count.lastKey());
        return count.dividedBy(total);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sumTS = new TimeSeries();
        for (String word : words) {
            sumTS = sumTS.plus(weightHistory(word, startYear, endYear));
        }
        return sumTS;    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sumTS = new TimeSeries();
        for (String word : words) {
            sumTS = sumTS.plus(weightHistory(word));
        }
        return sumTS;
    }


    private record Pair(Integer year, Double count) {
    }
}
