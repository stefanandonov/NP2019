package mk.ukim.finki.exams.vtor_kolokvium.message_system;

//package mk.ukim.finki.primeri;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class PartitionDoesNotExistException extends Exception {
    PartitionDoesNotExistException (String topic, int partition) {
        super(String.format("The topic %s does not have a partition with number %d", topic, partition));
    }
}

class UnsupportedOperationException extends Exception {

    public UnsupportedOperationException(String s) {
        super(s);
    }
}
class Message implements Comparable<Message>{
    LocalDateTime timestamp;
    String message;
    Integer partition;
    String key;

    public Message(LocalDateTime timestamp, String message, Integer partition, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.partition = partition;
        this.key = key;
    }

    public Message(LocalDateTime timestamp, String message, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
    }


    @Override
    public int compareTo(Message message) {
        return this.timestamp.compareTo(message.timestamp);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("timestamp=").append(timestamp);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

class Topic {
    String topicName;
    Map<Integer, TreeSet<Message>> messagesByPartition;
    int partitionsCount;

    public Topic(String topicName, int partitionsCount) {
        this.topicName = topicName;
        messagesByPartition = new TreeMap<>();
        this.partitionsCount = partitionsCount;
        IntStream.range(1,partitionsCount+1).forEach(i -> messagesByPartition.put(i, new TreeSet<>()));
    }

    void addMessage (Message message) throws PartitionDoesNotExistException {
        Integer messagePartition = message.partition;
        if (messagePartition==null) {
            messagePartition = (Math.abs(message.key.hashCode())  % this.partitionsCount) + 1;
        }

        if (!messagesByPartition.containsKey(messagePartition))
            throw new PartitionDoesNotExistException(topicName, messagePartition);

        messagesByPartition.computeIfPresent(messagePartition, (k,v) -> {
            if (v.size()==MessageBroker.capacityPerTopic)
                v.remove(v.first());
            v.add(message);
            return v;
        });
    }

    void changeNumberOfPartitions (int newPartitionsNumber) throws UnsupportedOperationException {
        if (newPartitionsNumber<this.partitionsCount)
            throw new UnsupportedOperationException("Partitions number cannot be decreased!");

        else {
            int diff = newPartitionsNumber - this.partitionsCount;
            int size = this.messagesByPartition.size();
            for (int i=1;i<=diff;i++)
                this.messagesByPartition.putIfAbsent(size+i, new TreeSet<>());
            this.partitionsCount = newPartitionsNumber;
        }
    }

    public String toString() {
        return String.format("Topic: %10s Partitions: %5d\n%s",
                topicName,
                partitionsCount,
                messagesByPartition.entrySet().stream().map(entry -> String.format("%2d : Count of messages: %5d\n%s",
                        entry.getKey(),
                        entry.getValue().size(),
                        !entry.getValue().isEmpty() ?
                                "Messages:\n" + entry.getValue().stream().map(Message::toString).collect(Collectors.joining("\n")) : "")
                ).collect(Collectors.joining("\n"))
        );
    }
}

class MessageBroker {
    Map<String, Topic> topicMap;
    static LocalDateTime minimumDate;
    static Integer capacityPerTopic;

    public MessageBroker(LocalDateTime minimumDate, Integer capacityPerTopic) {
        topicMap = new TreeMap<>();
        MessageBroker.minimumDate = minimumDate;
        MessageBroker.capacityPerTopic = capacityPerTopic;

    }

    public void addTopic (String topic, int partitionsCount) {
        topicMap.put(topic, new Topic(topic, partitionsCount));
    }

    public void addMessage (String topic, Message message) throws UnsupportedOperationException, PartitionDoesNotExistException {
        if (message.timestamp.isBefore(minimumDate))
            return ;

        topicMap.get(topic).addMessage(message);
    }

    public void changeTopicSettings (String topic, int partitionsCount) throws UnsupportedOperationException {
        topicMap.get(topic).changeNumberOfPartitions(partitionsCount);
    }

    public String toString() {
        return String.format("Broker with %2d topics:\n%s",
                topicMap.size(),
                topicMap.values().stream().map(Topic::toString).collect(Collectors.joining("\n"))
        );
    }
}
public class MessageBrokersTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String date = sc.nextLine();
        LocalDateTime localDateTime =LocalDateTime.parse(date);
        Integer partitionsLimit = Integer.parseInt(sc.nextLine());
        MessageBroker broker = new MessageBroker(localDateTime, partitionsLimit);
        int topicsCount = Integer.parseInt(sc.nextLine());

        //Adding topics
        for (int i=0;i<topicsCount;i++) {
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topicName = parts[0];
            int partitionsCount = Integer.parseInt(parts[1]);
            broker.addTopic(topicName, partitionsCount);
        }

        //Reading messages
        int messagesCount = Integer.parseInt(sc.nextLine());

        System.out.println("===ADDING MESSAGES TO TOPICS===");
        for (int i=0;i<messagesCount;i++) {
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length==4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,partition,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER ADDITION OF MESSAGES===");
        System.out.println(broker);

        System.out.println("===CHANGE OF TOPICS CONFIGURATION===");
        //topics changes
        int changesCount = Integer.parseInt(sc.nextLine());
        for (int i=0;i<changesCount;i++){
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topicName = parts[0];
            Integer partitions = Integer.parseInt(parts[1]);
            try {
                broker.changeTopicSettings(topicName, partitions);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("===ADDING NEW MESSAGES TO TOPICS===");
        messagesCount = Integer.parseInt(sc.nextLine());
        for (int i=0;i<messagesCount;i++) {
            String line = sc.nextLine();
            String [] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length==4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp,message,partition,key));
                } catch (UnsupportedOperationException | PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER CONFIGURATION CHANGE===");
        System.out.println(broker);


    }
}
