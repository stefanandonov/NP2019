//package mk.ukim.finki.konsultacii;
////package Kolokvium2;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Year;
//import java.util.*;
//
//class WrongDateException extends Exception{
//    public WrongDateException(Date date){
//        super("Wrong date: " + date.toString().replace("GMT","UTC"));
//    }
//}
//
//class Event{
//
//    String name;
//    String location;
//    Date date;
//
//    public Event(String name, String location, Date date) {
//        this.name = name;
//        this.location = location;
//        this.date = date;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s at %s, %s",
//                d
//                location,
//                name);
//    }
//}
//
//class EventCalendar{
//
//    //dali moze set poso vika O(n) ili treba so lista
//    //DALI KJE MOZE DA KORISTIME JAVA.TIME A NE OVA ZASTARENOVO DATE?
//    Map<Integer, Set<Event>> eventCalendar;
//    Integer eventYear;
//
//    public EventCalendar(){
//        eventCalendar = new HashMap<>();
//    }
//
//    public EventCalendar(int year){
//        eventCalendar = new HashMap<>();
//        this.eventYear = year;
//    }
//
//    public void addEvent(String name, String location, Date date) throws WrongDateException {
//
//        if(date.getYear()+1900 != eventYear) throw new WrongDateException(date);
//
//        Comparator<Event> eventComparator = Comparator.comparing(Event::getDate).thenComparing(Event::getName);
//        eventCalendar.computeIfAbsent(date.getDate(),(k) -> new TreeSet<>(eventComparator));
//        eventCalendar.get(date.getDate()).add(new Event(name, location, date));
//
//    }
//
//    public void listEvents(Date date) {
//
//        eventCalendar.get(date.getDate()).forEach(System.out::println);
//    }
//
//    public void listByMonth() {
//
//    }
//}
//
//public class EventCalendarTest {
//    public static void main(String[] args) throws ParseException {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        int year = scanner.nextInt();
//        scanner.nextLine();
//        EventCalendar eventCalendar = new EventCalendar(year);
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//        for (int i = 0; i < n; ++i) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(";");
//            String name = parts[0];
//            String location = parts[1];
//            Date date = df.parse(parts[2]);
//            try {
//                eventCalendar.addEvent(name, location, date);
//            } catch (WrongDateException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        Date date = df.parse(scanner.nextLine());
//        eventCalendar.listEvents(date);
//        eventCalendar.listByMonth();
//    }
//}
//
//// vashiot kod ovde