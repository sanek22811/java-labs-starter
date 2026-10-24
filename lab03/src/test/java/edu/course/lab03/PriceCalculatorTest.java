package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceCalculatorTest {

    @Test
    void noDiscountLeavesPriceUnchanged() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertEquals(150.0, calculator.calculate(150.0), 1e-9);
    }

    @Test
    void percentDiscountCalculatesCorrectPrice() {
        PriceCalculator calculator = new PriceCalculator(new PercentDiscount(20.0));
        assertEquals(80.0, calculator.calculate(100.0), 1e-9);
    }

    @Test
    void rejectsNegativePrice() {
        PriceCalculator calculator = new PriceCalculator(new NoDiscount());
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(-10.0));
    }

    @Test
    void percentDiscountRejectsInvalidPercentage() {
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(-5.0));
        assertThrows(IllegalArgumentException.class, () -> new PercentDiscount(105.0));
    }

    @Test
    void calculatorRejectsNullPolicy() {
        assertThrows(IllegalArgumentException.class, () -> new PriceCalculator(null));
    }

    @Test
    void allowsPolicyInterchangeability() {
        DiscountPolicy noDiscount = new NoDiscount();
        DiscountPolicy percentDiscount = new PercentDiscount(50.0);

        PriceCalculator calc1 = new PriceCalculator(noDiscount);
        PriceCalculator calc2 = new PriceCalculator(percentDiscount);

        assertEquals(200.0, calc1.calculate(200.0), 1e-9);
        assertEquals(100.0, calc2.calculate(200.0), 1e-9);
    }
}