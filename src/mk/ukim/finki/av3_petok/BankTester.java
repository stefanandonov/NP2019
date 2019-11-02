package mk.ukim.finki.av3_petok;

import java.util.ArrayList;
import java.util.List;

class InsufficientFundsException extends Exception {

    public InsufficientFundsException(double balance, double amount) {
        super(String.format("You only have %.2f, but you want to withdraw %.2f",
                balance,
                amount)
        );
    }



}

interface InterestBearingAccount {
    void addInterest();
}

enum TYPE {
    INTEREST,
    NON_INTEREST
}

abstract class Account {
    String holderName;
    int number;
    double balance;

    public Account(String holderName, int number, double balance) {
        this.holderName = holderName;
        this.number = number;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit (double amount) {
        balance+=amount;
    }

    public void withdraw (double amount) throws InsufficientFundsException {
        if (this.balance < amount)
            throw new InsufficientFundsException(this.balance, amount);

        this.balance-=amount;
    }

    abstract TYPE getType ();
}

class InterestCheckingAccount extends Account implements InterestBearingAccount {

    private static double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String holderName, int number, double balance) {
        super(holderName, number, balance);
    }

    @Override
    TYPE getType() {
        return TYPE.INTEREST;
    }

    @Override
    public void addInterest() {
        super.deposit(this.getBalance()*INTEREST_RATE);
    }

    public static double getInterestRate() {
        return INTEREST_RATE;
    }

    public static void setInterestRate(double interestRate) {
        INTEREST_RATE = interestRate;
    }
}

class PlatinumCheckingAccount extends InterestCheckingAccount {

    public PlatinumCheckingAccount(String holderName, int number, double balance) {
        super(holderName, number, balance);
    }

    @Override
    public void addInterest() {
        super.deposit(this.balance*InterestCheckingAccount.getInterestRate()*2.0);
//        super.addInterest();
//        super.addInterest();
    }
}

class NonInterestCheckingAccount extends Account {

    public NonInterestCheckingAccount(String holderName, int number, double balance) {
        super(holderName, number, balance);
    }

    @Override
    TYPE getType() {
        return TYPE.NON_INTEREST;
    }
}

class Bank {
    String bankName;
    List<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        accounts = new ArrayList<>();
    }

    public void addAccount (Account account) {
        accounts.add(account);
    }

    public void addInterest() {

        for (Account account : accounts) {
            if (account.getType()==TYPE.INTEREST) {
                InterestBearingAccount iba = (InterestBearingAccount) account;
                iba.addInterest();
            }
        }

        //java 8
//        accounts.stream()
//                .filter(account -> account.getType()==TYPE.INTEREST)
//                .forEach(account -> {
//                    InterestBearingAccount iba = (InterestBearingAccount) account;
//                    iba.addInterest();
//                });
    }

    public double totalAssets () {
        double sum = 0;
        for (Account account : accounts) {
            sum+=account.getBalance();
        }
        return sum;

        //Java 8
//        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }
}

public class BankTester {
}
