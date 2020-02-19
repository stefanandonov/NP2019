package mk.ukim.finki.konsultacii;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MojDDVTest {
    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);


        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);


    }
}
class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(String message) {
        super(message);
    }
}
class MojDDV {
    List<Smetka> list;

    public MojDDV() {
        list = new ArrayList<>();
    }
    public void readRecords (InputStream inputStream)  {
        Scanner sc=new Scanner(inputStream);


        while(sc.hasNextLine()) {
            List<Proizvod> list1=new ArrayList<>();
            String line=sc.nextLine();
            String pom[]=line.split("\\s+");
            String id=pom[0];
            for(int i=1; i<pom.length; i+=2) {
                int cena=Integer.parseInt(pom[i]);
                String tip=pom[i+1];
                list1.add(new Proizvod(cena,tip));

            }
            Smetka s= null;
            try {
                s = new Smetka(id,list1);
                list.add(s);
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void printTaxReturns (OutputStream outputStream) {
        PrintWriter pw=new PrintWriter(outputStream);
        list.forEach(pw::println);
        pw.flush();

    }
}
class Smetka {
    String ID;
    List<Proizvod> list;

    public Smetka(String ID, List<Proizvod> list) throws AmountNotAllowedException {
        this.ID = ID;
        this.list = list;

        if (this.promet()>30000)
            throw new AmountNotAllowedException(String.format("Receipt with amount %d is not allowed to be scanned", this.promet()));


    }
    public double danok() {
        double ddv=0.0;
        for(int i=0; i<list.size(); i++) {
            int cena=list.get(i).cena;
            String tip=list.get(i).typeTax;
            if(tip.equals("A")) {
                ddv+=cena*0.18;
            } else if(tip.equals("B")) {
                ddv+=cena*0.05;
            } else {
                ddv+=cena*0;
            }
        }

        return  ddv * 0.15;

    }
    public  int promet() {
        int vk_cena=0;
        for(int i=0; i<list.size(); i++) {
            int cena=list.get(i).cena;
            vk_cena+=cena;
        }
        return  vk_cena;
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f",ID,promet(),danok());
    }
}
class Proizvod {
    int cena;
    String typeTax;

    public Proizvod(int cena, String typeTax) {
        this.cena = cena;
        this.typeTax=typeTax;
    }

    public int getCena() {
        return cena;
    }
}
