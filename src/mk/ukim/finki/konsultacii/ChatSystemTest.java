//package mk.ukim.finki.konsultacii;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Scanner;
//import java.util.TreeSet;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.*;
//import java.util.stream.Collectors;
//
//class NoSuchUserException extends Throwable {
//    public NoSuchUserException(String userName) {
//        super("The user: "+userName+" its not registered");
//    }
//}
//
//class NoSuchRoomException extends Throwable {
//    public NoSuchRoomException(String roomName) {
//        super("The room "+roomName+" is not in the ChatSystem");
//    }
//}
//
//
//class UserName implements Comparable<UserName>{
//
//    String name;
//    UserName(String ime)
//    {
//        this.name = ime;
//    }
//
//    public String getIme() {
//        return name;
//    }
//
//    public void setIme(String ime) {
//        this.name = ime;
//    }
//
//    @Override
//    public int compareTo(UserName otherUser) {
//        if(this.name.compareTo(otherUser.name) > 0)
//        {
//            return 1;
//        }
//        else if(this.name.compareTo(otherUser.name) < 0)
//        {
//            return -1;
//        }
//        else {
//            return 0;
//        }
//    }
//}
//
//class ChatRoom{
//
//    String name;
//    Set<UserName> personsInRoom;
//
//    ChatRoom(String name)
//    {
//        this.name = name;
//        this.personsInRoom = new TreeSet<>();
//    }
//
//    public void addUser(String userName)
//    {
//        UserName newUser = new UserName(userName);
//        personsInRoom.add(newUser);
//    }
//
//    public void removeUser(String username)
//    {
//        UserName userName = new UserName(username);
//        personsInRoom.remove(userName);
//    }
//
//    public String toString(){
//
//        StringBuilder sb = new StringBuilder();
//        sb.append(name).append("\n");
//        String str = new String();
//        str = personsInRoom.stream().map(userName -> {
//            return userName.getIme() + "\n";
//        }).collect(Collectors.joining());
//        if (str.length() == 0)
//        {
//            str = "EMPTY\n";
//        }
//        sb.append(str);
//        return sb.toString();
//    }
//
//    public boolean hasUser(String username)
//    {
//        UserName userName = new UserName(username);
//        return personsInRoom.stream()
//                .anyMatch(s-> s.getIme().equals(username));
//    }
//
//    public int numUsers(){
//        return (int) personsInRoom.stream().count();
//    }
//
//}
//
//class ChatSystem{
//
//    Map<String,ChatRoom> chatRooms;
//
//    ChatSystem(){
//        chatRooms = new TreeMap<>();
//    }
//
//    public void addRoom(String roomName)
//    {
//        ChatRoom newRoom = new ChatRoom(roomName);
//        chatRooms.put(roomName, newRoom);
//    }
//
//    public void removeRoom(String roomName)
//    {
//        chatRooms.remove(roomName);
//    }
//
//    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
//
//        ChatRoom room = null;
//        room = chatRooms.get(roomName);
//        if(room == null)
//        {
//            throw new NoSuchRoomException(roomName);
//        }
//        return room;
//    }
//
//    public void register(String userName)
//    {
//
//        chatRooms.values().stream().min(Comparator.comparing(ChatRoom::numUsers));
//
//        chatRooms.values().stream().filter(ro)
//        Set<String> keys = chatRooms.keySet();
//        int min = 100000000;
//        ChatRoom room= null;
//
//
//        for(String k: keys)
//        {
//
//            if(room == null)
//            {
//                room = chatRooms.get(k);
//            }
//            else {
//                if (min > chatRooms.get(k).numUsers())
//                {
//                    room = chatRooms.get(k);
//                    min = room.numUsers();
//                }
//
//            }
//
//        }
//        room.addUser(userName);
//        // Dodava userName vo soba so najmalku korisnici---> treba da ja najdeme sobata so najmalku korisnici
//    }
//
//    public void registerAndJoin(String userName, String roomName)
//    {
//        chatRooms.get(roomName).addUser(userName);
//    }
//
//    public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
//        ChatRoom room = chatRooms.get(roomName);
//        if(room == null)
//        {
//            throw new NoSuchRoomException(roomName);
//        }
//        boolean hasuser = room.hasUser(userName);
//
//        room.addUser(userName);
//    }
//
//    public void leaveRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException {
//        boolean hasRoom = chatRooms.containsKey(roomName);
//        boolean hasUser = chatRooms.get(roomName).hasUser(userName);
//        if(!hasRoom)
//        {
//            throw new NoSuchRoomException(roomName);
//        }
//        if(!hasUser)
//        {
//            throw new NoSuchUserException(userName);
//        }
//
//        chatRooms.get(roomName).removeUser(userName);
//    }
//
//    public void followFriend(String username, String friend_username)
//    {
//        Set<String> keys = chatRooms.keySet();
//        for(String key : keys)
//        {
//            if(chatRooms.get(key).hasUser(friend_username))
//            {
//                chatRooms.get(key).addUser(username);
//            }
//        }
//    }
//
//}
//
//
//public class ChatSystemTest {
//
//    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
//        Scanner jin = new Scanner(System.in);
//        int k = jin.nextInt();
//        if ( k == 0 ) {
//            ChatRoom cr = new ChatRoom(jin.next());
//            int n = jin.nextInt();
//            for ( int i = 0 ; i < n ; ++i ) {
//                k = jin.nextInt();
//                if ( k == 0 ) cr.addUser(jin.next());
//                if ( k == 1 ) cr.removeUser(jin.next());
//                if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
//            }
//            System.out.println("");
//            System.out.println(cr.toString());
//            n = jin.nextInt();
//            if ( n == 0 ) return;
//            ChatRoom cr2 = new ChatRoom(jin.next());
//            for ( int i = 0 ; i < n ; ++i ) {
//                k = jin.nextInt();
//                if ( k == 0 ) cr2.addUser(jin.next());
//                if ( k == 1 ) cr2.removeUser(jin.next());
//                if ( k == 2 ) cr2.hasUser(jin.next());
//            }
//            System.out.println(cr2.toString());
//        }
//        if ( k == 1 ) {
//            ChatSystem cs = new ChatSystem();
//            Method mts[] = cs.getClass().getMethods();
//            while ( true ) {
//                String cmd = jin.next();
//                if ( cmd.equals("stop") ) break;
//                if ( cmd.equals("print") ) {
//                    System.out.println(cs.getRoom(jin.next())+"\n");continue;
//                }
//                for ( Method m : mts ) {
//                    if ( m.getName().equals(cmd) ) {
//                        String params[] = new String[m.getParameterTypes().length];
//                        for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
//                        try {
//                            m.invoke(cs,params);
//                        } catch (InvocationTargetException e) {
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//}
