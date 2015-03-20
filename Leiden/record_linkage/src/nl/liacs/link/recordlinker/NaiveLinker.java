package nl.liacs.link.recordlinker;

import java.util.ArrayList;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Occurrences;
import nl.liacs.link.StatisticsFile;
import nl.liacs.link.distance.Distance;
import nl.liacs.link.result.Match;
import nl.liacs.link.result.Results;

/**
 * Based on the probabilities of finding certain information (requires
 * statistics).
 *
 * @author Kleanthi
 * @author Benjamin
 *
 */
public class NaiveLinker extends RecordLinker {

    ArrayList<Task> tasks = new ArrayList<Task>();

    public class Task {

        String fieldName;
        StatisticsFile statistics;
        Distance<String> distance;

        Task(String fieldName, StatisticsFile statisticsFile, Distance<String> distance) {
            this.fieldName = fieldName;
            this.statistics = statisticsFile;
            this.distance = distance;
        }
    }

    public void addTask(String fieldName, StatisticsFile statisticsFile, Distance<String> distance) {
        tasks.add(new Task(fieldName, statisticsFile, distance));
    }
    
    
    /**
     * Linking process between pairs of occurrences for the same file
     */
    public void link(Results results) {
        for (int i = 0; i < this.occurrences.size() - 1; i++) {
            for (int j = i + 1; j < this.occurrences.size(); j++) {
                Occurrence oc1 = this.occurrences.get(i);
                Occurrence oc2 = this.occurrences.get(j);
                float confidence = computeConfidence(oc1, oc2);
                results.add(new Match(oc1, oc2, confidence));
                
            }
        }
    }
    
    /**
     * Linking process between pairs of occurrences from different files
     */
    public void link(Results results, Occurrences occurr2) {
        for (int i = 0; i < this.occurrences.size() ; i++) {
            for (int j = 0; j < occurr2.size(); j++) {
                Occurrence oc1 = this.occurrences.get(i);
                Occurrence oc2 = occurr2.get(j);
                float confidence = computeConfidence(oc1, oc2);
                results.add(new Match(oc1, oc2, confidence));
            }
        }
    }
    /**
     * Compute probability for specific field values
     *
     * @param s1, value of first occurrence's field
     * @param s2, value of second occurrence's field
     * @param statistics, statistics file name for the field
     * @param distance,	Distance<String> metric for the field
     * @return probability (a <float> number)
     */
    private float computeProbability(String s1, String s2, StatisticsFile statistics, Distance<String> distance) {
        float probability;

        if (distance.isEqual(s1, s2)) {
            if (s1.isEmpty() || s2.isEmpty())
            {  // ignores the field in confidence computation
                probability = 1.0f;
            }
            else if (statistics.containsKey(s1) || statistics.containsKey(s2))
            {
                // non-empty, use a priori statistics
                probability = 0.0f;
                if(s1.equals(s2))
                    probability = statistics.get(s1);
                else
                {
                    if (statistics.containsKey(s1))
                    	probability += statistics.get(s1); 
                    
                    if (statistics.containsKey(s2)) 
                    	probability += statistics.get(s2);
			    }
            } 
            else 
                probability = 0.0001f;  // we have no a priori knowledge of the field's statistics
            
        } 
        else 
            probability = 0.0f;   // fields are not similar enough according to Distance(threshold)
        
        return probability;

    }

    /**
     * Compute confidence score for a pair of occurrences by comparing the
     * values of the corresponding fields
     *
     * @param oc, first occurrence
     * @param oc2, second occurrence
     * @return Confidence score (a <float> number)
     */
    private float computeConfidence(Occurrence oc, Occurrence oc2) {
        
        float score = 1.0f;
        for (Task task : tasks) {
            score *= computeProbability(oc.getString(task.fieldName),
                    oc2.getString(task.fieldName),
                    task.statistics,
                    task.distance);

        }
		
        /* Compute the number of matches in the population of size `POPULATION_SIZE`. */
        float confidence;

        if (score == 0.0f || score == 1.0f) {
            confidence = 0.0f;
        } else {
            confidence = POPULATION_SIZE * score; 
            confidence = 1.0f / confidence;
        }
        return confidence;

    }

}
