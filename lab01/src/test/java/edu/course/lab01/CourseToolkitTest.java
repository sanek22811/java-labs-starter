package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseToolkitTest {

    // --- Тесты isEven (включая подготовительную часть) ---

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);
        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);
        assertFalse(result);
    }

    @Test
    void returnTrueWhenNumberIsZero() {
        assertTrue(CourseToolkit.isEven(0));
    }

    @Test
    void returnsTrueForNegativeEvenNumber() {
        assertTrue(CourseToolkit.isEven(-8));
    }

    // --- Тесты isPrime ---

    @Test
    void isPrimeReturnsFalseForNumbersLessThanTwo() {
        assertFalse(CourseToolkit.isPrime(-5));
        assertFalse(CourseToolkit.isPrime(0));
        assertFalse(CourseToolkit.isPrime(1));
    }

    @Test
    void isPrimeReturnsTrueForSmallPrimes() {
        assertTrue(CourseToolkit.isPrime(2));
        assertTrue(CourseToolkit.isPrime(3));
        assertTrue(CourseToolkit.isPrime(13));
    }

    @Test
    void isPrimeReturnsFalseForCompositeNumbers() {
        assertFalse(CourseToolkit.isPrime(4));
        assertFalse(CourseToolkit.isPrime(15));
        assertFalse(CourseToolkit.isPrime(100));
    }

    @Test
    void isPrimeReturnsFalseForSquareOfPrime() {
        assertFalse(CourseToolkit.isPrime(49));
        assertFalse(CourseToolkit.isPrime(121));
    }

    // --- Тесты isPalindrome ---

    @Test
    void isPalindromeReturnsTrueForValidPalindromes() {
        assertTrue(CourseToolkit.isPalindrome(""));
        assertTrue(CourseToolkit.isPalindrome("a"));
        assertTrue(CourseToolkit.isPalindrome("racecar"));
        assertTrue(CourseToolkit.isPalindrome("12321"));
    }

    @Test
    void isPalindromeReturnsFalseForNonPalindromes() {
        assertFalse(CourseToolkit.isPalindrome("hello"));
        assertFalse(CourseToolkit.isPalindrome("Racecar"));
        assertFalse(CourseToolkit.isPalindrome("ab ba "));
    }

    @Test
    void isPalindromeThrowsExceptionOnNull() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.isPalindrome(null));
    }

    // --- Тесты average ---

    @Test
    void averageCalculatesCorrectValueForPositiveNumbers() {
        int[] data = {1, 2, 3, 4};
        assertEquals(2.5, CourseToolkit.average(data), 1e-9);
    }

    @Test
    void averageCalculatesCorrectValueForNegativeAndMixedNumbers() {
        int[] data = {-10, 10, -5, 5};
        assertEquals(0.0, CourseToolkit.average(data), 1e-9);

        int[] negativeOnly = {-2, -4, -6};
        assertEquals(-4.0, CourseToolkit.average(negativeOnly), 1e-9);
    }

    @Test
    void averageDoesNotMutateInputArray() {
        int[] original = {5, 1, 9};
        int[] copy = original.clone();
        CourseToolkit.average(original);
        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }

    @Test
    void averageThrowsExceptionOnNullOrEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(null));
        assertThrows(IllegalArgumentException.class, () -> CourseToolkit.average(new int[0]));
    }
}