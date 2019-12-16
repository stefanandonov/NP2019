package mk.ukim.finki.konsultacii;
import java.util.*;

/**
 * Transactions 2015/11/14 first midterm
 */

class UnsupportedOperationException extends Exception {
    UnsupportedOperationException () {
        super("Button click is not supported");
    }
}
class Button {
    int id;
    String name;
    OnButtonClick onButtonClick;

    public Button(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    void clickButton() throws UnsupportedOperationException {
        if (onButtonClick==null)
            throw new UnsupportedOperationException();
        else
            onButtonClick.onClick(this);
    }

    public Integer getId() {
        return id;
    }
}

class View {
    Map<Integer, Button> buttonMap;
    
    View () {
        buttonMap = new HashMap<>();
    }

    public void addButton(int id, String name) {
        Button newButton = new Button(id,name);
        buttonMap.put(id, newButton);


    }

    public void setOnButtonClickListener(int id, OnButtonClick onButtonClick) {
        Button button = buttonMap.get(id);
        button.setOnButtonClick(onButtonClick);
    }

    public void clickButton(int id) throws UnsupportedOperationException {
        buttonMap.get(id).clickButton();
    }
}

public class ViewTest {
    public static void main(String[] args) {
        View view = new View();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; ++i) {
            view.addButton(i + 1, String.format("BUTTON %d", i + 1));
        }
        int nn = scanner.nextInt();
        for (int i = 0; i < nn; ++i) {
            int x = scanner.nextInt();
            view.setOnButtonClickListener(x, new LongButtonClickImpl());
        }
        nn = scanner.nextInt();
        for (int i = 0; i < nn; ++i) {
            int x = scanner.nextInt();
            view.setOnButtonClickListener(x, new ShortButtonClickImpl());
        }
        for (int i = 0; i < n; ++i) {
            try {
                view.clickButton(i + 1);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
    }

}

interface OnButtonClick {
    void onClick(Button button);
}

class ShortButtonClickImpl implements OnButtonClick {
    @Override
    public void onClick(Button button) {
        System.out.println(String.format("Button with id '%d' was clicked SHORT", button.getId()));
    }
}

class LongButtonClickImpl implements OnButtonClick {
    @Override
    public void onClick(Button button) {
        System.out.println(String.format("Button with id '%d' was clicked LONG", button.getId()));
    }
}

// vashiot kod ovde

