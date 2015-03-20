/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.result;

/** 
 * Results abstract class
 * @author Kleanthi
 * @author Benjamin
 */
public interface Results {
    void add(Match result);
    void print();
    String toJson();
    void toJsonFile(String outputFile);
}