/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import nl.liacs.link.Occurrence;
import nl.liacs.link.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author Benjamin
 * @author Kleanthi
 */

/* NOTE: This class implementation assumes each Occurrence is assigned a unique ID, externally. 
 * Please make sure that each occurrence in the input set has an identifier.
 * The following functions do not check the uniqueness. 
*/

public class ResultsSet implements Results {
    protected final HashMap<Occurrence, ArrayList<Match>> entries;
    private final float threshold;
    private final int maxSize;

    /**
     * Unconstrained ranking.
     */
    public ResultsSet() {
        this.entries = new HashMap<>();
        this.threshold = -1;
        this.maxSize = Integer.MAX_VALUE;
    }

    /**
     * Constrain the ranking using a threshold.
     *
     * @param threshold
     */
    public ResultsSet(float threshold) {
        this.entries = new HashMap<>();
        this.maxSize = Integer.MAX_VALUE;
        this.threshold = threshold;
    }

    /**
     * Constrain the ranking using a maximum size.
     * @param maxSize 
     */
    public ResultsSet(int maxSize) {
        this.entries = new HashMap<>();
        this.threshold = Float.MIN_VALUE;
        this.maxSize = maxSize;
    }

    /**
     * Constrain the ranking using both a threshold and a minimum size.
     * @param threshold
     * @param maxSize 
     */
    public ResultsSet(float threshold, int maxSize) {
        this.entries = new HashMap<>();
        this.threshold = threshold;
        this.maxSize = maxSize;
    }

    @Override
    public void add(Match result) {
        if (result == null) {
            throw new IllegalArgumentException("Expected an occurrence but received a null-pointer.");
        } else if (result.getConfidence()> this.threshold &&
                this.entries.size() < this.maxSize){
            
            Occurrence oc1 = result.getFirstOccurrence();
            ArrayList<Match> m1;
            if (this.entries.containsKey(oc1)) {
                m1 = this.entries.get(oc1);
            } else {
                m1 = new ArrayList<>();
            }
            m1.add(result);
            
            Occurrence oc2 = result.getSecondOccurrence();
            ArrayList<Match> m2;
            if (this.entries.containsKey(oc2)) {
                m2 = this.entries.get(oc2);
            } else {
                m2 = new ArrayList<>();
            }
            m2.add(result);
            
            this.entries.put(oc1, m1);
            this.entries.put(oc2, m2);
            
        }
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Iterator<Entry<Occurrence, ArrayList<Match>>> iterator() {
        return this.entries.entrySet().iterator();
    }
    
    @Override
    public String toJson() {
        List<Person> persons = new ArrayList<>();
        Iterator<Entry<Occurrence, ArrayList<Match>>> it;
        for (it = this.iterator(); it.hasNext(); ) {
            Entry<Occurrence, ArrayList<Match>> entry = it.next();
            Occurrence oc1 = entry.getKey();
            Person p1 = new Person(oc1);
            
            List<Match> matches = entry.getValue();
            for (Match match : matches) {
                Occurrence oc2;
                if (match.getFirstOccurrence().sameEntity(oc1)) {
                    oc2 = match.getSecondOccurrence();
                } else if (match.getSecondOccurrence().sameEntity(oc1)) {
                    oc2 = match.getFirstOccurrence();
                } else {
                    oc2 = null;  // Should not get here.
                }
                Person p2 = new Person(oc2);
                p1.addSameAs(p2, match.getConfidence());
            }
            persons.add(p1);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(persons);
    }
    
    @Override
    public void toJsonFile(String File) {
        List<Person> persons = new ArrayList<>();
        Iterator<Entry<Occurrence, ArrayList<Match>>> it;
        for (it = this.iterator(); it.hasNext(); ) {
            Entry<Occurrence, ArrayList<Match>> entry = it.next();
            Occurrence oc1 = entry.getKey();
            Person p1 = new Person(oc1);
            
            List<Match> matches = entry.getValue();
            for (Match match : matches) {
                Occurrence oc2;
                if (match.getFirstOccurrence().sameEntity(oc1)) {
                    oc2 = match.getSecondOccurrence();
                } else if (match.getSecondOccurrence().sameEntity(oc1)) {
                    oc2 = match.getFirstOccurrence();
                } else {
                    oc2 = null;  // Should not get here.
                }
                Person p2 = new Person(oc2);
                p1.addSameAs(p2, match.getConfidence());
            }
            //persons.add(p1);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
			File outputFile = new File(File);
			Writer fileWriter;
			try {
				fileWriter = new FileWriter(outputFile,true);
				try {
					fileWriter.write(gson.toJson(p1));
				} catch (IOException e) {

                e.printStackTrace();
				} finally {
					if (fileWriter != null) {
						try {
							fileWriter.close();
						} catch (IOException e) {

							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
