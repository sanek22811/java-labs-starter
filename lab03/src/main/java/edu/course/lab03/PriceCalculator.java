package edu.course.lab03;

public final class PriceCalculator {

    private final DiscountPolicy discountPolicy;

    public PriceCalculator(DiscountPolicy discountPolicy) {
        if (discountPolicy == null) {
            throw new IllegalArgumentException("Discount policy cannot be null");
        }
        this.discountPolicy = discountPolicy;
    }

    public double calculate(double price) {
        return this.discountPolicy.apply(price);
    }
}