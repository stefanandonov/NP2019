package mk.ukim.finki.av3;

import java.util.ArrayList;
import java.util.List;

class InsufficientFundsException extends Exception {

    InsufficientFundsException(double balance, double amount) {
        super(String.format("You have tried to withdraw %.2f, but you only have %.2f",
                amount,
                balance)
        );
    }

}

interface InterestBearingAccount {
    void addInterest();
}


enum TYPE {
    WITH_INTEREST,
    WITHOUT_INTEREST
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

    public String getHolderName() {
        return holderName;
    }

    public int getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (balance < amount) {
            throw new InsufficientFundsException(this.balance, amount);
        }
        balance -= amount;
    }

    public abstract TYPE getType();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("holderName='").append(holderName).append('\'');
        sb.append(", number=").append(number);
        sb.append(", balance=").append(balance);
        sb.append('}');
        return sb.toString();
    }
}

class InterestCheckingAccount extends Account implements InterestBearingAccount {

    public static double INTEREST_RATE = .03; // 3%

    public InterestCheckingAccount(String holderName, int number, double balance) {
        super(holderName, number, balance);
    }

    @Override
    public TYPE getType() {
        return TYPE.WITH_INTEREST;
    }

    @Override
    public void addInterest() {
        super.deposit(getBalance() * 3 / 100.0);
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
        super.deposit(getBalance() * (InterestCheckingAccount.getInterestRate() * 2));
    }


}

class NonInterestCheckingAccount extends Account {

    public NonInterestCheckingAccount(String holderName, int number, double balance) {
        super(holderName, number, balance);
    }

    @Override
    public TYPE getType() {
        return TYPE.WITHOUT_INTEREST;
    }
}

class Bank {
    List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();

    }

    public void addAccount(Account newAccount) {
        accounts.add(newAccount);

    }

    public void addInterest() {

        for (Account account : accounts) {
            if (account.getType().equals(TYPE.WITH_INTEREST)) {
                InterestBearingAccount iba = (InterestBearingAccount) account;
                iba.addInterest();
            }
        }

        //Java 8 STreams API solution
//        accounts.stream()
//                .filter(account -> account.getType().equals(TYPE.WITH_INTEREST))
//                .forEach(account -> {
//                    InterestBearingAccount iba = (InterestBearingAccount) account;
//                    iba.addInterest();
//                });
    }

    public double totalAssets () {
        double sum = 0.0;
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
