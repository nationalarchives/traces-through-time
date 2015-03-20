/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.result;

import nl.liacs.link.Occurrence;

/**
 * ResultEntry packs rows of the ranking together. Allows for mixed types and
 * assures all fields have been initialised.
 * 
 * @author Kleanthi
 * @author Benjamin
 */
public class Match implements Comparable<Match> {

    
    private final Occurrence oc1;
    private final Occurrence oc2;
    private final float confidence;
    
    public Match(Occurrence oc1, Occurrence oc2, float confidence) {
        assert (oc1 != null && oc2 != null);
        this.oc1 = oc1;
        this.oc2 = oc2;
        this.confidence = confidence;
    }

    public Occurrence getFirstOccurrence() {
        return this.oc1;
    }

    public Occurrence getSecondOccurrence() {
        return this.oc2;
    }

    public float getConfidence() {
        return this.confidence;
    }

    @Override
    public int compareTo(Match other) {
        if (this.getConfidence() < other.getConfidence()) {
            return -1;
        } else if (this.getConfidence() > other.getConfidence()) {
            return 1;
        } else {
            return 0;
        }
    }
}
