//package mk.ukim.finki.konsultacii;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//class AmountParser {
//
//    public static double parseAmount (String amount) {
//        return Double.parseDouble(amount.substring(0,amount.length()-1));
//    }
//
//    public static String amountToString (double amount) {
//        return String.format("%.2f$", amount);
//    }
//
//}
//
//class Account {
//    private String name;
//    private long id;
//    //private String balance;
//    private double balance;
//
//    public Account(String name, String balance) {
//        this.name = name;
//        this.balance = AmountParser.parseAmount(balance);
//        Random r = new Random();
//        id = r.nextInt();
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setBalance(double balance) {
//        this.balance = balance;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Name: "+name+"\n"+"Balance: "+balance);
//    }
//}
//
//abstract class Transaction {
//    private long fromId;
//    private long toId;
//    private String description;
//    private double amount;
//
//    public Transaction() { //se pojavi greska there is no default constructor in class Transaction zatoa e napisan
//    }
//
//    public Transaction(long fromId, long toId, String description, String amount) {
//        this.fromId = fromId;
//        this.toId = toId;
//        this.description = description;
//        this.amount = AmountParser.parseAmount(amount);
//    }
//
//    public long getFromId() {
//        return fromId;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public long getToId() {
//        return toId;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    abstract double getProvision(double amount);
//}
//
//class FlatAmountProvisionTransaction extends Transaction {
//    private double flatAmount;
//
//    public FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatAmount) {
//        super(fromId, toId, "FlatAmount",amount);
//        this.flatAmount = AmountParser.parseAmount(flatAmount);
//    }
//
//    @Override
//    double getProvision(double amount) {
//        return flatAmount;
//    }
//
//    public double getFlatAmount() {
//        return flatAmount;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }
//}
//
//class FlatPercentProvisionTransaction extends Transaction {
//    private int percent;
//
//    public FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDolar) {
//        super(fromId, toId, "FlatPercent",amount);
//        this.percent = centsPerDolar;
//    }
//
//    public int getPercent() {
//        return percent;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return super.equals(obj);
//    }
//
//    @Override
//    double getProvision(double amount) {
//        return percent/100.0*(int)amount;
//    }
//}
//
//class Bank {
//    private String name;
//    private Account[] accounts;
//    private String totalProvisions;
//    private String totalTransfers;
//
//    public Bank(String name, Account[] accounts) {
//        this.name = name;
//        this.accounts = accounts;
//        this.totalProvisions="0.00$";
//        this.totalTransfers="0.00$";
//    }
//
//    public String totalTransfers(){
//        return totalTransfers;
//    }
//
//    public String totalProvision(){
//        return totalProvisions;
//    }
//
//    public Account[] getAccounts() {
//        return accounts;
//    }
//
//    @Override
//    public String toString() {
//        System.out.println("Name: Banka na RM"+"\n");
//        for(Account a : accounts){
//            System.out.println(a);
//        }
//        return "";
//    }
//
//    public Account[] findAccounts(Transaction t){
//        Account []acc = new Account[2];
//        for(Account a:accounts){
//            if(a.getId()==t.getFromId()){
//                acc[0]=a;
//            }
//            if(a.getId()==t.getToId()){
//                acc[1]=a;
//            }
//        }
//        return acc;
//    }
//
//    public String checkClass(Transaction t){
//        boolean isAmount = FlatAmountProvisionTransaction.class.equals(t.getClass());
//        if(isAmount){
//            return "Amount";
//        }
//        else {
//            return "Percent";
//        }
//    }
//
//    public double [] firstAfterState(Account [] acts, Transaction t){
//        double [] array = new double[3];
//        double first = Double.parseDouble(acts[0].getBalance().substring(0,4));
//        double am = Double.parseDouble(t.getAmount().substring(0,4));
//        array[0]=first;
//        array[1]=am;
//        if(checkClass(t).equals("Amount")){
//            FlatAmountProvisionTransaction fapt = (FlatAmountProvisionTransaction)t;
//            double provision = Double.parseDouble(fapt.getFlatAmount().substring(0,4));
//            array[2]=provision;
//            return array;
//        }
//        else {
//            FlatPercentProvisionTransaction fppt = (FlatPercentProvisionTransaction)t;
//            double percent = fppt.getPercent();
//            double provision = (percent*am)/(double)100;
//            array[2]=provision;
//            return array;
//        }
//    }
//
//    public boolean checkBalance(Account [] acts, Transaction t){
//        double []array = firstAfterState(acts,t);
//        if(array[0]-(array[1]+array[2])>=0){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    public boolean makeTransaction(Transaction t){
//        Account []acts = findAccounts(t);
//        if(acts[0]==null || acts[1]==null || !checkBalance(acts, t))
//            return false;
//        double [] array = firstAfterState(acts,t);
//        acts[0].setBalance((array[0]-(array[1]+array[2]))+"$");
//        acts[1].setBalance((Double.parseDouble(acts[1].getBalance().substring(0,4))+array[1])+"$");
//        totalProvisions=(Double.parseDouble(totalProvisions.substring(0,4))+array[2])+"$";
//        totalTransfers=(Double.parseDouble(totalTransfers.substring(0,4))+array[1])+"$";
//        return true;
//    }
//}
//
//public class BankTester {
//
//    public static void main(String[] args) {
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
//    }
//
//    private static void testEquals() {
//        Account a1 = new Account("Andrej", "20.00$");
//        Account a2 = new Account("Andrej", "20.00$");
//        Account a3 = new Account("Andrej", "30.00$");
//        Account a4 = new Account("Gajduk", "20.00$");
//        List<Account> all = Arrays.asList(a1, a2, a3, a4);
//        if (!(a1.equals(a1)&&!a1.equals(a2)&&!a2.equals(a1)&&!a3.equals(a1)
//                && !a4.equals(a1)
//                && !a1.equals(null))) {
//            System.out.println("Your account equals method does not work properly.");
//            return;
//        }
//        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
//        if (ids.size() != all.size()) {
//            System.out.println("Different accounts have the same IDS. This is not allowed");
//            return;
//        }
//        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
//        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, "20.00$", "10.00$");
//        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
//        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
//        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
//        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
//        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
//        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, "20.00$", 10);
//        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
//        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
//        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
//        if (fa1.equals(fa1) &&
//                !fa2.equals(null) &&
//                fa2.equals(fa1) &&
//                fa1.equals(fa2) &&
//                fa1.equals(fa3) &&
//                !fa1.equals(fa4) &&
//                !fa1.equals(fa5) &&
//                !fa1.equals(fp1) &&
//                fp1.equals(fp1) &&
//                !fp2.equals(null) &&
//                fp2.equals(fp1) &&
//                fp1.equals(fp2) &&
//                fp1.equals(fp3) &&
//                !fp1.equals(fp4) &&
//                !fp1.equals(fp5) &&
//                !fp1.equals(fp6)) {
//            System.out.println("Your transactions equals methods do not work properly.");
//            return;
//        }
//        Account accounts[] = new Account[]{a1, a2, a3, a4};
//        Account accounts1[] = new Account[]{a2, a1, a3, a4};
//        Account accounts2[] = new Account[]{a1, a2, a3};
//        Account accounts3[] = new Account[]{a1, a2, a3, a4};
//
//        Bank b1 = new Bank("Test", accounts);
//        Bank b2 = new Bank("Test", accounts1);
//        Bank b3 = new Bank("Test", accounts2);
//        Bank b4 = new Bank("Sample", accounts);
//        Bank b5 = new Bank("Test", accounts3);
//
//        if (!(b1.equals(b1) &&
//                !b1.equals(null) &&
//                !b1.equals(b2) &&
//                !b2.equals(b1) &&
//                !b1.equals(b3) &&
//                !b3.equals(b1) &&
//                !b1.equals(b4) &&
//                b1.equals(b5))) {
//            System.out.println("Your bank equals method do not work properly.");
//            return;
//        }
//        accounts[2] = a1;
//        if (!b1.equals(b5)) {
//            System.out.println("Your bank equals method do not work properly.");
//            return;
//        }
//        long from_id = a2.getId();
//        long to_id = a3.getId();
//        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
//        b1.makeTransaction(t);
//        if (b1.equals(b5)) {
//            System.out.println("Your bank equals method do not work properly.");
//            return;
//        }
//        b5.makeTransaction(t);
//        if (!b1.equals(b5)) {
//            System.out.println("Your bank equals method do not work properly.");
//            return;
//        }
//        System.out.println("All your equals methods work properly.");
//    }
//
//    private static void testTypicalUsage(Scanner jin) {
//        String bank_name = jin.nextLine();
//        int num_accounts = jin.nextInt();
//        jin.nextLine();
//        Account accounts[] = new Account[num_accounts];
//        for (int i = 0; i < num_accounts; ++i)
//            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
//        Bank bank = new Bank(bank_name, accounts);
//        while (true) {
//            String line = jin.nextLine();
//            switch (line) {
//                case "stop":
//                    return;
//                case "transaction":
//                    String descrption = jin.nextLine();
//                    String amount = jin.nextLine();
//                    String parameter = jin.nextLine();
//                    int from_idx = jin.nextInt();
//                    int to_idx = jin.nextInt();
//                    jin.nextLine();
//                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
//                    System.out.println("Transaction amount: " + t.getAmount());
//                    System.out.println("Transaction description: " + t.getDescription());
//                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
//                    break;
//                case "print":
//                    System.out.println(bank.toString());
//                    System.out.println("Total provisions: " + bank.totalProvision());
//                    System.out.println("Total transfers: " + bank.totalTransfers());
//                    System.out.println();
//                    break;
//            }
//        }
//    }
//
//    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
//        switch (description) {
//            case "FlatAmount":
//                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
//                        bank.getAccounts()[to_idx].getId(), amount, o);
//            case "FlatPercent":
//                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
//                        bank.getAccounts()[to_idx].getId(), amount, Integer.parseInt(o));
//        }
//        return null;
//    }
//
//
//}
//
