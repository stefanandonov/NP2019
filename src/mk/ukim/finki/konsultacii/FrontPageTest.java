package mk.ukim.finki.konsultacii;

//package Np.IKol;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for(Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch(CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde

class Category {

    String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if (obj.getClass() != Category.class) return false;
        Category category = (Category) obj;
        return category.getName().equals(getName());
    }
}

abstract class NewsItem {

    String title;
    Date datePublished;
    Category category;

    public NewsItem(String title, Date datePublished, Category category) {
        this.title = title;
        this.datePublished = datePublished;
        this.category = category;
    }

    String getTeaser() {
        Date today = Calendar.getInstance().getTime();
        long minutes = Math.abs(today.getTime() - datePublished.getTime());
        minutes = TimeUnit.MILLISECONDS.toMinutes(minutes);
        return String.format("%s\n%d\n", title, minutes);
    }
}

class TextNewsItem extends NewsItem {
    String text;

    public TextNewsItem(String title, Date datePublished, Category category, String text) {
        super(title, datePublished, category);
        this.text = text;
    }

    @Override
    String getTeaser() {
        return super.getTeaser() + String.format("%s\n", text.length() > 80 ? text.substring(0, 80) : text);
    }
}

class MediaNewsItem extends NewsItem {

    String url;
    int views;

    public MediaNewsItem(String title, Date datePublished, Category category, String url, int views) {
        super(title, datePublished, category);
        this.url = url;
        this.views = views;
    }

    @Override
    String getTeaser() {
        return super.getTeaser() + String.format("%s\n%d\n", url, views);
    }
}

class FrontPage {

    List<NewsItem> news;
    List<Category> categories;

    public FrontPage(Category [] categories) {
        this.news = new ArrayList<NewsItem>();
        this.categories = new ArrayList<Category>();
        this.categories = Arrays.asList(categories);
    }

    void  addNewsItem(NewsItem newsItem) {
        this.news.add(newsItem);
    }

    List<NewsItem> listByCategory(Category category) {
        List<NewsItem> newsByCategory = new ArrayList<NewsItem>();
        for(NewsItem ni : news) {
            if(ni.category.equals(category)) {
                newsByCategory.add(ni);
            }
        }
        return newsByCategory;
    }

    List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        if (!categories.contains(new Category(category)))
            throw new CategoryNotFoundException();
        List<NewsItem> newsByCategoryName = new ArrayList<NewsItem>();
        for(NewsItem ni : news) {
            if(ni.category.getName().equals(category)) {
                newsByCategoryName.add(ni);
            }
        }
        return newsByCategoryName;
    }

    @Override
    public String toString() {
        String s = new String();
        for(NewsItem ni : news) {
            s += ni.getTeaser();
        }
        return s;
    }
}

class CategoryNotFoundException extends Exception {
    public CategoryNotFoundException() {
        super("Category Fun was not found");
    }
}