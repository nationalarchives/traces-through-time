/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.test;

import junit.framework.TestCase;
import nl.liacs.link.Occurrence;
import nl.liacs.link.OccurrenceBase;
import nl.liacs.link.result.Match;
import nl.liacs.link.result.ResultsRanking;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ResultsRankingTest extends TestCase {

    private ResultsRanking ranking;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public ResultsRankingTest() {
    }

    // assigning the values
    @Override
    protected void setUp() {
        this.ranking = new ResultsRanking();

        OccurrenceBase base1 = new OccurrenceBase();
        base1.forename = "John";
        base1.surname = "Smith";
        Occurrence oc1 = new Occurrence(base1);
        
        OccurrenceBase base2 = new OccurrenceBase();
        base2.forename = "John";
        base2.provenance = "London";
        Occurrence oc2 = new Occurrence(base2); 
        
        this.ranking.add(new Match(oc1, oc2, 0.01f));
    }

    @Override
    public void tearDown() {
        this.ranking = null;  // send to GC after each test
    }

    @Test
    public void testJson() {
        System.out.println(this.ranking.toJson());
    }
}
