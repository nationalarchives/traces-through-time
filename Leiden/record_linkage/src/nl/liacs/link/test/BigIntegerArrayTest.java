/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.liacs.link.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import junit.framework.TestCase;
import nl.liacs.link.BigIntegerArray;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BigIntegerArrayTest extends TestCase {

    private BigIntegerArray bigArray;
    final private int[] classicArray;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public BigIntegerArrayTest() {
        /* The values can be changed, but do not include 10.0f, otherwise testIndexOutOfBoundsException() will fail. */
        this.classicArray = new int[]{0, 3, 2, 3, 2, 0, 4, 4, 1, 5};
    }

    // assigning the values
    @Override
    protected void setUp() {
        this.bigArray = new BigIntegerArray((long) classicArray.length);
    }

    @Override
    public void tearDown() {
        this.bigArray = null;  // send to GC after each test
    }

    /* Helper function that copies the default values (see constructor) to the BigArray. */
    public void copyFromClassic() {
        for (long i = 0; i < this.classicArray.length; i++) {
            this.bigArray.set(i, this.classicArray[(int) i]);
        }
    }

    /* Helper function that makes a copy of the default values into a primitive array. */
    public int[] getClassicCopy() {
        int[] copy = Arrays.copyOf(this.classicArray, this.classicArray.length);
        return copy;
    }

    public List<Integer> getClassicList() {
        List<Integer> copy = new ArrayList<>();
        for (int i = 0; i < this.classicArray.length; i++) {
            copy.add(this.classicArray[i]);
        }
        return copy;
    }

    public static long linearSearch(int key, BigIntegerArray array) {
        long index = -1;
        for (long i = 0; i < array.size(); i++) {
            if (Integer.compare(array.get(i), key) == 0) {
                index = i;
            }
        }
        return index;
    }

    @Test
    public void testSize() {
        assertEquals(this.classicArray.length, this.bigArray.size());
    }

    @Test
    public void testSetGet() {
        for (long i = 0; i < this.classicArray.length; i++) {
            this.bigArray.set(i, this.classicArray[(int) i]);
            assertEquals(this.classicArray[(int) i], this.bigArray.get(i));
        }
    }

    @Test
    public void testSort() {
        /* Copy values from classic array. */
        copyFromClassic();

        /* Sort the classicArray. Make a copy of the original, so we do not change classicArray. */
        int[] copy = Arrays.copyOf(this.classicArray, this.classicArray.length);
        Arrays.sort(copy);

        /* Sort the bigArray. */
        this.bigArray.sort();

        /* Perform the test: check equality on each element. */
        for (long i = 0; i < this.bigArray.size(); i++) {
            assertEquals("copy[" + i + "] == bigArray[" + i + "]", copy[(int) i], this.bigArray.get(i));
        }
    }

    @Test
    public void testIndexOutOfBoundsException() {
        thrown.expect(IndexOutOfBoundsException.class);
    }

    @Test
    public void testBinarySearchNotFound() {
        copyFromClassic();
        this.bigArray.sort();
        assertEquals("10 should not be found", -1, this.bigArray.search(10));
    }

    @Test
    public void testBinarySearchBeginning() {
        copyFromClassic();
        this.bigArray.sort();
        assertEquals("Element found at beginning of sorted array", linearSearch(0, this.bigArray), this.bigArray.search(0));
    }

    @Test
    public void testBinarySearchMiddle() {
        copyFromClassic();
        this.bigArray.sort();
        assertEquals("Element found at middle of sorted array", linearSearch(1, this.bigArray), this.bigArray.search(1));
    }

    @Test
    public void testBinarySearchEnd() {
        copyFromClassic();
        this.bigArray.sort();
        assertEquals("Element found at end of sorted array", linearSearch(5, this.bigArray), this.bigArray.search(5));
    }

    @Test
    public void testReverse() {
        /* Initialize elements in BigArray to the ones in classic. */
        copyFromClassic();
        this.bigArray.reverse();

        /* Then check for equality. */
        long bigIndex = 0;
        int classicIndex = this.classicArray.length - 1;
        while (bigIndex < this.bigArray.size()) {
            assertEquals("copy[" + bigIndex + "] == bigArray[" + classicIndex + "]", this.classicArray[classicIndex], this.bigArray.get(bigIndex));
            bigIndex++;
            classicIndex--;
        }
    }

    @Test
    public void testUnique() {
        /* Get classic array as a list. */
        List<Integer> classic = getClassicList();

        /* Sort it, then find the unique elements (sorted ascending). */
        Collections.sort(classic);
        List<Integer> unique = new ArrayList<>();
        int previous = -1;
        for (Integer current : classic) {
            if (current != previous) {
                unique.add(current);
                previous = current;
            }
        }

        copyFromClassic();
        this.bigArray.unique();

        /* unique() can only be correct if the number of unique elements found are equal. */
        assertEquals("Number of unique elements should be equal", unique.size(), this.bigArray.size());

        /* Then check if each element is equal (and array is sorted). */
        for (long i = 0; i < this.bigArray.size(); i++) {
            assertEquals("copy[" + i + "] == bigArray[" + i + "]", (int) unique.get((int) i), (int) this.bigArray.get(i));
        }
    }

    @Test
    public void testIterator() {
        copyFromClassic();
        int counter = 0;
        for (Integer num : this.bigArray) {
            assertEquals("copy[" + counter + "] == " + num, (int) this.classicArray[counter], (int) num);
            counter++;
        }
        assertEquals("Iterator should have looped through complete bigArray", this.classicArray.length, counter);
    }
}
