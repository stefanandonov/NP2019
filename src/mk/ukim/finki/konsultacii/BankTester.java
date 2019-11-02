package konsultacii;

import java.util.*;
import java.util.stream.Collectors;

class AmountParser {
    public static double parseAmount(String stringAmount) {
        String amountWithoutDollar = stringAmount.substring(0, stringAmount.length() - 1);
        return Double.parseDouble(amountWithoutDollar);
    }

    public static String amountToString(double amount) {
        return String.format("%.2f$", amount);
    }
}

class Account {
    String name;
    long id;
    double balance;

    Account(String name, String balance) {
        this.name = name;
        this.balance = AmountParser.parseAmount(balance);
        this.id = new Random().nextLong();
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getBalance() {
        return AmountParser.amountToString(this.balance);
    }

    public void setBalance(String newBalance) {
        this.balance = AmountParser.parseAmount(newBalance);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nBalance: %s",
                name,
                AmountParser.amountToString(this.balance)
        );
    }
}

enum TYPE {
    FLAT_AMOUNT,
    PLAT_PERCENT
}

abstract class Transaction {
    long fromId;
    long toId;
    String description;
    double amount;

    Transaction(long fromId, long toId, String description, String amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = AmountParser.parseAmount(amount);
    }

    public long getFromId() {
        return fromId;
    }

    public long getToId() {
        return toId;
    }

    String getAmount() {
        return AmountParser.amountToString(amount);
    }

    abstract double getProvision();

    abstract TYPE getType();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return fromId == that.fromId&&toId == that.toId &&
                Double.compare(that.amount, amount) == 0 &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }

    public String getDescription() {
        return description;
    }
}

class FlatAmountProvisionTransaction extends Transaction {

    double flatProvision;

    FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatProvision = AmountParser.parseAmount(flatProvision);
    }

    @Override
    double getProvision() {
        return flatProvision;
    }

    @Override
    TYPE getType() {
        return TYPE.FLAT_AMOUNT;
    }


}

class FlatPercentProvisionTransaction extends Transaction {

    int centsPerDolar;

    FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDolar) {
        super(fromId, toId, "FlatPercent", amount);
        this.centsPerDolar = centsPerDolar;
    }

    @Override
    double getProvision() {
        return (int) amount * (centsPerDolar / 100.0);
    }

    @Override
    TYPE getType() {
        return TYPE.PLAT_PERCENT;
    }

}

class Bank {
    String name;
    List<Account> accounts;
    double totalAmountTransferred = 0.0;
    double totalProvisionCharged = 0.0;

    Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = Arrays.asList(accounts);
    }

    boolean makeTransaction(Transaction t) {

        long fromId = t.fromId;
        long toId = t.toId;

        double amount = t.amount;

        Account fromAccount = null;
        Account toAccount = null;

        for (Account account : accounts) {
            if (account.id == fromId) {
                fromAccount = account;
            }
            if (account.id == toId) {
                toAccount = account;
            }
        }

        if (fromAccount == null || toAccount == null)
            return false;

        if (fromAccount.balance < amount)
            return false;

        //todo check without iterations
        for (Account account : accounts) {
            if (account.id == fromId) {
                account.setBalance(AmountParser.amountToString(account.balance - t.amount - t.getProvision()));
            }
            if (account.id == toId) {
                account.setBalance(AmountParser.amountToString(account.balance + t.amount));
            }
        }
        totalAmountTransferred += t.amount;
        totalProvisionCharged += t.getProvision();
        return true;
    }

    String totalTransfers() {
        return AmountParser.amountToString(totalAmountTransferred);
    }

    String totalProvision() {
        return AmountParser.amountToString(totalProvisionCharged);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Name: %s\n\n", name));

        for (Account account : accounts) {
            sb.append(account.toString()).append("\n");
        }

        return sb.toString();

        // Java 8
//        String.format("Name: %s\n\n%s",
//                name,
//                accounts.stream()
//                        .map(Account::toString)
//                        .collect(Collectors.joining("\n"))
//        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Double.compare(bank.totalAmountTransferred, totalAmountTransferred) == 0 &&
                Double.compare(bank.totalProvisionCharged, totalProvisionCharged) == 0 &&
                name.equals(bank.name) &&
                accounts.containsAll(bank.accounts) && bank.accounts.containsAll(accounts);
        //accounts.equals(bank.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, accounts, totalAmountTransferred, totalProvisionCharged);
    }

    public Account[] getAccounts() {
        Account [] accountsArray = new Account[accounts.size()];

        for (int i=0;i<accounts.size();i++)
            accountsArray[i]=accounts.get(i);

        return accountsArray;
    }
}

public class BankTester {

    public static void main(String[] args) {
//        Scanner jin = new Scanner(System.in);
//        String test_type = jin.nextLine();
//        switch (test_type) {
//            case "typical_usage":
//                testTypicalUsage(jin);
//                break;
//            case "equals":
//                testEquals();
//                break;
//        }
//        jin.close();

        System.out.println(AmountParser.amountToString(2.17));
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", "20.00$");
        Account a2 = new Account("Andrej", "20.00$");
        Account a3 = new Account("Andrej", "30.00$");
        Account a4 = new Account("Gajduk", "20.00$");
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1) && !a1.equals(a2) && !a2.equals(a1) && !a3.equals(a1)
                && !a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, "20.00$", 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    String amount = jin.nextLine();
                    String parameter = jin.nextLine();
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + t.getAmount());
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + bank.totalProvision());
                    System.out.println("Total transfers: " + bank.totalTransfers());
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, Integer.parseInt(o));
        }
        return null;
    }


}
