package mk.ukim.finki.konsultacii;//package LabaratoriskiVezbi.lab7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;
import java.util.stream.Collectors;

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String roomName){
        super(roomName + "Doesnt exist");
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String userName){
        super(userName + "is not registered");
    }
}

class User{

    String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

class ChatRoom{

    String name;
    Set<User> chatRoom;

    public ChatRoom(String name) {
        this.name = name;
        Comparator<User> userComparator = Comparator.comparing(User::getUsername);
        chatRoom = new TreeSet<>(userComparator);
    }

    public void addUser(String username) {
        chatRoom.add(new User(username));
    }


    public void removeUser(String username) {
        chatRoom.removeIf(user -> user.getUsername().equals(username));
        //chatRoom.remove(username)
    }

    public boolean hasUser(String username) {
        User user = new User(username);
        return chatRoom.contains(user);
    }

    public int numUsers(){
        return chatRoom.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        if(chatRoom.isEmpty()) sb.append("EMPTY");
        else chatRoom.forEach(sb::append);
        return sb.toString();
    }
}

class ChatSystem{

    Map<String,ChatRoom> chatSystem;
    Map<User,Set<ChatRoom>> userRegistry;

    public ChatSystem(){
        chatSystem = new TreeMap<>();
        userRegistry = new HashMap<>();
    }

    public void addRoom(String roomName){
        chatSystem.computeIfAbsent(roomName,(k) -> new ChatRoom(roomName));
    }

    public void removeRoom(String roomName){
        chatSystem.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {

        if(!chatSystem.containsKey(roomName)) throw new NoSuchRoomException(roomName);
        return chatSystem.get(roomName);
    }

    public void register(String userName){

        User user = new User(userName);
        registerUser(user);
        ChatRoom temp = findRoomWithMinUsers();
        chatSystem.get(temp.name).addUser(userName);
        userRegistry.get(user).add(temp);
    }

    private ChatRoom findRoomWithMinUsers(){
        return  chatSystem.values()
                .stream()
                .min(Comparator.comparing(ChatRoom::numUsers))
                .get();
    }

    private void registerUser(User user){

        Comparator<ChatRoom> chatRoomComparator = Comparator.comparing(ChatRoom::numUsers);
        userRegistry.computeIfAbsent(user,(k) -> new TreeSet<>(chatRoomComparator));
    }

    public void registerAndJoin(String userName,String roomName){

        User user = new User(userName);
        registerUser(user);
        chatSystem.get(roomName).addUser(userName);
        userRegistry.get(user).add(new ChatRoom(roomName));
    }

    private void checkUser(User user) throws NoSuchUserException {

        if(!userRegistry.containsKey(user))
            throw new NoSuchUserException(user.getUsername());
    }

    private void checkRoom(String roomName) throws NoSuchRoomException {

        if(!chatSystem.containsKey(roomName)) throw new NoSuchRoomException(roomName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {

        User user = new User(userName);
        checkUser(user);
        checkRoom(roomName);

        chatSystem.get(roomName).addUser(userName);
        userRegistry.get(user).add(new ChatRoom(roomName));
    }

    public void leaveRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {

        User user = new User(userName);
        checkUser(user);
        checkRoom(roomName);

        chatSystem.get(roomName).removeUser(userName);
        ChatRoom roomToRemove = new ChatRoom(roomName);
        userRegistry.get(user).remove(roomToRemove);
    }

    public void followFriend(String userName,String friendUserName) throws NoSuchUserException {

        User user = new User(userName);
        //User friendUser = new User(friendUserName);
        checkUser(user);

        chatSystem.values().stream().filter(room -> room.hasUser(friendUserName)).forEach(room -> room.addUser(userName));

//        List<ChatRoom> chatRoomsToAdd = new ArrayList<>(userRegistry.get(friendUser));
//        chatRoomsToAdd.forEach(room -> chatSystem.get(room.name).addUser(userName));
//        userRegistry.get(user).addAll(chatRoomsToAdd);
    }
}

public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr.addUser(jin.next());
                if ( k == 1 ) cr.removeUser(jin.next());
                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if ( n == 0 ) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for ( int i = 0 ; i < n ; ++i ) {
                k = jin.nextInt();
                if ( k == 0 ) cr2.addUser(jin.next());
                if ( k == 1 ) cr2.removeUser(jin.next());
                if ( k == 2 ) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if ( k == 1 ) {
            ChatSystem cs = new ChatSystem();
            Method[] mts = cs.getClass().getMethods();
            while ( true ) {
                String cmd = jin.next();
                if ( cmd.equals("stop") ) break;
                if ( cmd.equals("print") ) {
                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
                }
                for ( Method m : mts ) {
                    if ( m.getName().equals(cmd) ) {
                        String params[] = new String[m.getParameterTypes().length];
                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                        try {
                            m.invoke(cs, params);
                        }
                        catch (Exception e) {
                            System.out.println("");
                        }
                    }
                }
            }
        }
    }

}
