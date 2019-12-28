package mk.ukim.finki.konsultacii;//package mk.ukim.finki.code;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde


class TermFrequency
{
    Set<String> ignoriraj;
    Map<String,Integer> mapa;
    int total;
    public TermFrequency(InputStream inputStream,String[] stopWords)
    {
        total = 0;
        mapa = new TreeMap<>();
        this.ignoriraj = new TreeSet<>();
        Scanner sc = new Scanner(inputStream);

        ignoriraj.addAll(Arrays.asList(stopWords));
        while(sc.hasNextLine())
        {
            String line = sc.nextLine();

            String [] zborovi = line.split(" ");
            for(String s:zborovi)
            {
                s =s.replaceAll(" [^a-zA-Z_-]", "");
                s = s.toLowerCase();
                if(!ignoriraj.contains(s)) {
                    if(!s.equals(""))
                    {
                        total++;
                    }
                    if (!mapa.containsKey(s)) {
                        mapa.put(s, 1);
                    } else {
                        mapa.put(s, mapa.get(s) + 1);
                    }
                }
            }
        }

        sc.close();



    }
    public int countTotal()
    {
        return total;
    }
    public int countDistinct(){
        return mapa.size();
    }
    public List<String> mostOften(int k)
    {
        Comparator<String> comparator = (x1,x2) -> {
            if(mapa.get(x1).equals(mapa.get(x2)))
            {
                return x1.compareTo(x2);
            }
            else if(mapa.get(x1) > mapa.get(x2))
                return -1;
            else
                return 1;
        };

        return this.mapa.keySet().stream().sorted(comparator).limit(k).collect(Collectors.toList());
    }
}