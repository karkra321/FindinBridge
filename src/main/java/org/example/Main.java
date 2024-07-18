package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String bridgesFilePath = "/Users/karol/Downloads/bridges.txt";
        String queuesFilePath = "/Users/karol/Downloads/bw-tpwholesale-mgmt.txt";

        Set<String> bridgeQueues = extractQueuesFromBridges(bridgesFilePath);
        Set<String> allQueues = extractQueuesFromQueuesFile(queuesFilePath);

        Set<String> queuesNotInBridges = new HashSet<>(allQueues);
        queuesNotInBridges.removeAll(bridgeQueues);

        Set<String> queuesInBothFiles = new HashSet<>(allQueues);
        queuesInBothFiles.retainAll(bridgeQueues);

        System.out.println("Queues not present in bridges file:");
        queuesNotInBridges.forEach(System.out::println);

        System.out.println("\nQueues present in both files:");
        queuesInBothFiles.forEach(System.out::println);
    }

    private static Set<String> extractQueuesFromBridges(String bridgesFilePath) {
        Set<String> bridgeQueues = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(bridgesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("queue=")) {
                    String queue = line.split("=")[1].split(" ")[0];
                    bridgeQueues.add(queue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bridgeQueues;
    }

    private static Set<String> extractQueuesFromQueuesFile(String queuesFilePath) {
        Set<String> allQueues = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(queuesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Queue=")) {
                    String queue = line.split("=")[1].split("\\s+")[0];
                    allQueues.add(queue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allQueues;
    }
}
