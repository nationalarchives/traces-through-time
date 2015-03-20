/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.test;

import junit.framework.TestCase;
import nl.liacs.link.matrices.MatricesOfMatrices;
import org.junit.Test;

public class MatricesOfMatricesTest extends TestCase {

    final float[][] classicMatrix;
    MatricesOfMatrices matrix;

    public MatricesOfMatricesTest() {
        this.classicMatrix = new float[][]{
            {0.0f, 0.24f, 0.31f},
            {0.24f, 0.0f, 0.5f},
            {0.31f, 0.5f, 0.0f}
        };
    }

    // assigning the values
    @Override
    protected void setUp() {
        this.matrix = new MatricesOfMatrices(3);
    }

    @Override
    public void tearDown() {
        this.matrix = null;
    }

    @Test
    public void testSetGet() {
        for (int i = 0; i < this.matrix.size() - 1; i++) {
            for (int j = i + 1; j < this.matrix.size(); j++) {
                this.matrix.set(this.classicMatrix[i][j], i, j);
                assertEquals("Elements should be equal", this.matrix.get(i, j), this.classicMatrix[i][j]);
            }
        }
    }

    @Test
    public void testSymmetric() {
        int data = 1;
        for (int i = 0; i < this.matrix.size() - 1; i++) {
            for (int j = i + 1; j < this.matrix.size(); j++) {
                this.matrix.set((float) data, i, j);  // stored in upper triangular matrix
                assertEquals("Elements should be equal", this.matrix.get(j, i), (float) data);  // retrieved in lower
                data++;
            }
        }
    }
    
    @Test
    public void testGetRow() {
        for (int i = 0; i < this.matrix.size() - 1; i++) {
            for (int j = i + 1; j < this.matrix.size(); j++) {
                this.matrix.set(this.classicMatrix[i][j], i, j);
            }
        }
        
        for (int i = 0; i < this.matrix.size(); i++) {
            /* Get row from classic matrix. */
            float[] classicRow = new float[this.matrix.size()];
            System.arraycopy(this.classicMatrix[i], 0, classicRow, 0, this.matrix.size());

            /* Get row from implemented matrix. */
            float[] matrixRow = this.matrix.getRow(i);

            /* Compare. */
            for (int j = 0; j < matrix.size(); j++) {
                assertEquals("Elements should be equal", matrixRow[j], classicRow[j]);
            }
        }
    }
    
        @Test
        public void testGetColumn() {
        for (int i = 0; i < this.matrix.size() - 1; i++) {
            for (int j = i + 1; j < this.matrix.size(); j++) {
                this.matrix.set(this.classicMatrix[i][j], i, j);
            }
        }
        
        for (int i = 0; i < this.matrix.size(); i++) {
            /* Get column from classic matrix. */
            float[] classicColumn = new float[this.matrix.size()];
            for (int j = 0; j < this.matrix.size(); j++) {
                classicColumn[j] = this.classicMatrix[j][i];
            }

            /* Get column from implemented matrix. */
            float[] matrixColumn = this.matrix.getColumn(i);

            /* Compare. */
            for (int j = 0; j < matrix.size(); j++) {
                assertEquals("Elements should be equal", matrixColumn[j], classicColumn[j]);
            }
        }
    }
}
