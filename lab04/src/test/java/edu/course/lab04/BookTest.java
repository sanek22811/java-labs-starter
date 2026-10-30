package edu.course.lab04;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    @Test
    void findsValueInMapByDifferentInstanceWithSameIsbn() {
        Book book1 = new Book("978-0134685991", "Effective Java");
        Book book2 = new Book("978-0134685991", "Effective Java 3rd Edition");

        Map<Book, String> catalog = new HashMap<>();
        catalog.put(book1, "Programming Shelf");

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
        assertEquals("Programming Shelf", catalog.get(book2));
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        Book book1 = new Book("978-0134685991", "Effective Java");
        Book book2 = new Book("978-0132350884", "Clean Code");

        assertNotEquals(book1, book2);
    }

    @Test
    void constructorRejectsInvalidIsbnOrTitle() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "Title"));
        assertThrows(IllegalArgumentException.class, () -> new Book("   ", "Title"));
        assertThrows(IllegalArgumentException.class, () -> new Book("123", null));
        assertThrows(IllegalArgumentException.class, () -> new Book("123", "   "));
    }
}
