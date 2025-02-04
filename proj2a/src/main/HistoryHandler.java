package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;
import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    NGramMap map;

    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (String word : words) {
            TimeSeries parabola = map.weightHistory(word, startYear, endYear);
            labels.add(word);
            lts.add(parabola);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        return Plotter.encodeChartAsString(chart);
    }
}
