package nl.liacs.link;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import nl.liacs.link.blocking.Blocker;
import nl.liacs.link.filter.Filter;
import nl.liacs.link.recordlinker.RecordLinker;
import nl.liacs.link.result.ResultsSet;

import org.apache.commons.configuration.ConfigurationException;

public class Link {

    @SuppressWarnings("null")
    public static void main(final String[] args) {
        final long startTime = System.currentTimeMillis();
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024 * 1024;
        /* Load configuration from resource file. */
        System.out.println("Event: loading configuration from `configuration.json`...");

        ConfigurationHandler handler = null;
        try {
            ArgumentsParser parser = new ArgumentsParser(args);
            handler = new ConfigurationHandler(new File(parser.getValue("--conf")));
        } catch (ConfigurationException e) {
            System.out.println("Something is wrong with the configuration file.");
        }

        System.out.println("Configuration file has been identified.");
        /* Instantiate the occurrences from JSON file. */
        System.out.println("Event: loading the occurrences...");
        Occurrences occurrences = handler.getOccurrences();
        System.out.println("Loaded " + occurrences.size() + " occurrences.");

        /* Get Linker type */
        RecordLinker linker = handler.getRecordLinker();
        /* Get set of linking attributes */
        String[] attributes = handler.getAttributes();
        for (String attr : attributes) {
            linker.addTask(attr, handler.getStatistics(attr), handler.getDistance(attr));
        }
        /* Perform Filtering */
        ArrayList<Filter> Filters = handler.getFilters();
        if (Filters != null) {
            System.out.println("Performing Filtering..!!");
            for (Filter filter : Filters) {
                filter.setOccurrences(occurrences);
                filter.filterOccurrences();
                occurrences = filter.getOccurrences();
            }
            System.out.println("Number of occurrences, after filtering has finished : " + occurrences.size());
        }
        if (occurrences.size() == 0) {
            System.out.println("Filtering returned 0 occurrences. Exiting..\n");
            System.exit(0);
        }
        Filters = null;
        
        /* Divide occurrences into blocks*/
        Blocker blocker = handler.getBlocker();
        blocker.setOccurrences(occurrences);
        blocker.createBlocks();

        System.out.println("Performing blocking.. ");
        System.out.println("Number of blocks created : " + blocker.getBlocksNumber() + "\n");


        float conThrs = handler.getConfidenceThreshold();
        int maxSize = handler.getResultsMaxSize(occurrences.size());
        ResultsSet results = new ResultsSet(conThrs,maxSize);
        
        ArrayList<Occurrences> occurrencesList;
        while ((occurrencesList = blocker.getNextBlocks()) != null) {

            /* Instantiate the linker */
            linker.setOccurrences(occurrencesList.get(0));
            if (occurrencesList.get(1) == null) {
                System.out.println("Number of occurrences in this block : " + occurrencesList.get(0).size());
                /* Perform record linkage. */
                System.out.println("Event: performing the entity resolution...");
                linker.link(results);
            } else {
                System.out.println("Number of occurrences in this block : " + (occurrencesList.get(0).size() + occurrencesList.get(1).size()));
                /* Perform record linkage. */
                System.out.println("Event: performing the entity resolution...");
                linker.link(results, occurrencesList.get(1));
            }
        }
        
        System.out.println("Event: storing linkage results..");
        System.out.println("The output file includes pairs of occurrences with confidence score equal or greater than " + conThrs + ".\n\n");
        String fileName = handler.getOutputFile();
        results.toJsonFile(fileName);

        /*Time statistics*/
        final long elapsedTimeMillis = System.currentTimeMillis() - startTime;
        final int MINUTES_IN_AN_HOUR = 60;
        final int SECONDS_IN_A_MINUTE = 60;

        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTimeMillis);
        int seconds = (int) (timeSeconds % SECONDS_IN_A_MINUTE);
        int totalMinutes = (int) (timeSeconds / SECONDS_IN_A_MINUTE);
        int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
        int hours = totalMinutes / MINUTES_IN_AN_HOUR;

        System.out.println(hours + " hours " + minutes + " minutes " + seconds + " seconds");
    }

}
