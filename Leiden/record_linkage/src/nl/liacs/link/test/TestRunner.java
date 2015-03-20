/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BigFloatArrayTest.class);
        System.out.println("Running tests for class BigFloatArray.");
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(BigIntegerArrayTest.class);
        System.out.println("Running tests for class BigIntegerArray.");
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());

        result = JUnitCore.runClasses(MatricesOfMatricesTest.class);
        System.out.println("Running tests for class MatricesOfMatrices.");
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
        
        result = JUnitCore.runClasses(ResultsRankingTest.class);
        System.out.println("Running tests for class MatchRanking.");
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
