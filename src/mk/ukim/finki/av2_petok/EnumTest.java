package mk.ukim.finki.av1;

enum TYPE_OF_SHAPE {
    TRIANGLE,
    SQUARE,
    CIRCLE
}

public class EnumTest {

    public static void main(String[] args) {

        TYPE_OF_SHAPE circleShape;

        circleShape = TYPE_OF_SHAPE.CIRCLE;

//        System.out.println(TYPE_OF_SHAPE.valueOf("x"));

        TYPE_OF_SHAPE.values();

        StringBuilder sb = new StringBuilder();


        sb.append("Stefamn")
                .append(" petok e ")
                .append("aj da ")
                .append("si odime")
                .append("BEEEE").toString();



    }
}
