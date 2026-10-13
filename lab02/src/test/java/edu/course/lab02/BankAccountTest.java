package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    @Test
    void rejectsNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-10));
    }

    @Test
    void depositIncreasesBalanceForPositiveAmount() {
        BankAccount account = new BankAccount(100);
        account.deposit(50);
        assertEquals(150, account.getBalance());
    }

    @Test
    void depositRejectsNonPositiveAmount() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-20));
    }

    @Test
    void successfulWithdrawDecreasesBalance() {
        BankAccount account = new BankAccount(100);
        account.withdraw(40);
        assertEquals(60, account.getBalance());
    }

    @Test
    void withdrawRejectsNonPositiveAmount() {
        BankAccount account = new BankAccount(100);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0));
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-10));
    }

    @Test
    void withdrawThrowsExceptionWhenAmountExceedsBalance() {
        BankAccount account = new BankAccount(50);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(100));
    }
}
