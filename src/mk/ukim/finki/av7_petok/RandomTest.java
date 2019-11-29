package mk.ukim.finki.av7_petok;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NotEnoughParticipantsException extends Exception{

    public NotEnoughParticipantsException(int participants, int prizes) {
        super(String.format("There are only %d participants. Cannot award %d prizes.",
                participants,
                prizes));
    }
}

class RandomPicker {

    int n;
    List<Integer> participants;

    public RandomPicker(int n) {
        this.n = n;
        participants = new ArrayList<>();

        for (int i=1;i<=n;i++)
            participants.add(i);

        /* Java 8 solution
        IntStream.range(1,n+1).forEach(participants::add);
        *
         */
    }

    public List<Integer> pickPrizes (int prizes) throws NotEnoughParticipantsException {

//        List<Integer> pickedIntegers = new ArrayList<>();
        if (prizes>n)
            throw new NotEnoughParticipantsException(n, prizes);
        Random random = new Random();
//        for (int i=0;i<prizes;i++) {
//            int index = random.nextInt(participants.size());
//            Integer pickedValue = participants.get(index);
//            pickedIntegers.add(pickedValue);
//            participants.remove(pickedValue);
//        }

        return IntStream.range(0,prizes).mapToObj(prize -> {
            int index = random.nextInt(participants.size());
            Integer pickedValue = participants.get(index);
            participants.remove(pickedValue);
            return pickedValue;
        }).collect(Collectors.toList());

        //return pickedIntegers;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RandomPicker{");
        sb.append("n=").append(n);
        sb.append(", participants=").append(participants);
        sb.append('}');
        return sb.toString();
    }
}

public class RandomTest {

    public static void main(String[] args) {

        RandomPicker randomPicker = new RandomPicker(30);
        try {
            System.out.println(randomPicker.pickPrizes(3));
        } catch (NotEnoughParticipantsException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(randomPicker);



    }
}
