package mk.ukim.finki.konsultacii;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

class PartitionAssigner {
    public static Integer assignPartition(Message message, int partitionsCount) {
        return (Math.abs(message.key.hashCode()) % partitionsCount) + 1;
    }
}

public class MessageBrokersTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String date = sc.nextLine();
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        Integer partitionsLimit = Integer.parseInt(sc.nextLine());
        MessageBroker broker = new MessageBroker(localDateTime, partitionsLimit);
        int topicsCount = Integer.parseInt(sc.nextLine());

        //Adding topics
        for (int i = 0; i < topicsCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topicName = parts[0];
            int partitionsCount = Integer.parseInt(parts[1]);
            broker.addTopic(topicName, partitionsCount);
        }

        //Reading messages
        int messagesCount = Integer.parseInt(sc.nextLine());

        System.out.println("===ADDING MESSAGES TO TOPICS===");
        for (int i = 0; i < messagesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length == 4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, partition, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER ADDITION OF MESSAGES===");
        System.out.println(broker);

        System.out.println("===CHANGE OF TOPICS CONFIGURATION===");
        //topics changes
        int changesCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < changesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
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
        for (int i = 0; i < messagesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length == 4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, partition, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER CONFIGURATION CHANGE===");
        System.out.println(broker);


    }
}

class Message implements Comparable<Message> {
    LocalDateTime timestamp;
    String message;
    String key;
    Integer particija;

    public Message(LocalDateTime timestamp, String message, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
        this.particija = null;
    }

    public Message(LocalDateTime timestamp, String message, Integer particija, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
        this.particija = particija;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int compareTo(Message otherMessage) {
        return this.timestamp.compareTo(otherMessage.timestamp);
    }
}

class Topic {
    String topicName;
    int partitionsCount;
    Map<Integer, TreeSet<Message>> poraki;

    public Topic(String topicName, int partitionsCount) {
        this.topicName = topicName;
        this.partitionsCount = partitionsCount;
        this.poraki = new TreeMap<>();
        IntStream.rangeClosed(1, partitionsCount).forEach(i -> poraki.put(i, new TreeSet<>()));
    }

    void addMessage(Message message) throws PartitionDoesNotExistException {

        if (message.timestamp.isBefore(MessageBroker.minimumDate))
            return ;

        Integer partitionNumber = null;
        if (message.particija == null) {
            partitionNumber = PartitionAssigner.assignPartition(message, this.partitionsCount);
        } else {
            partitionNumber = message.particija;
        }
        TreeSet<Message> messagesPerPartition = poraki.get(partitionNumber);
        if (messagesPerPartition.size() == MessageBroker.getCapacityPerTopic()) {
            messagesPerPartition.remove(messagesPerPartition.first());
            messagesPerPartition.add(message);
        }
    }

    void changeNumberOfPartitions(int newPartitionsNumber) throws UnsupportedOperationException {
        if (newPartitionsNumber < this.partitionsCount) {
            throw new UnsupportedOperationException();
        } else {
            this.partitionsCount = newPartitionsNumber;
            IntStream.rangeClosed(poraki.size()+1, newPartitionsNumber).forEach(i -> poraki.put(i, new TreeSet<>()));
        }
    }

    public String toString() {
        Comparator<Message> comparator = Comparator.comparing(message -> message.particija);
        String s = String.format("Topic: %10s Partitions: %5d\n", topicName, partitionsCount);
        //this.poraki.values().stream().
        return s;
    }

}

class MessageBroker {
    static LocalDateTime minimumDate;
    static Integer capacityPerTopic;
    Map<String, Topic> topics;

    public MessageBroker(LocalDateTime minimumDate, Integer capacityPerTopic) {
        MessageBroker.minimumDate = minimumDate;
        MessageBroker.capacityPerTopic = capacityPerTopic;
        topics = new HashMap<>();
    }

    void addTopic(String topic, int partitionsCount) {
        topics.putIfAbsent(topic, new Topic(topic, partitionsCount));
    }

    void addMessage(String topic, Message message) throws PartitionDoesNotExistException {
        topics.get(topic).addMessage(message);
    }

    void changeTopicSettings(String topic, int partitionsCount) throws UnsupportedOperationException {
        topics.get(topic).changeNumberOfPartitions(partitionsCount);
    }

    public static LocalDateTime getMinimumDate() {
        return minimumDate;
    }

    public static Integer getCapacityPerTopic() {
        return capacityPerTopic;
    }

    public String toString() {
        Comparator<Topic> comparator = Comparator.comparing(topic -> topic.topicName);
        return "empty";
    }
}

class PartitionDoesNotExistException extends Exception {

}

//class UnsupportedOperationException extends Exception {
//    public UnsupportedOperationException() {
//    }
//}