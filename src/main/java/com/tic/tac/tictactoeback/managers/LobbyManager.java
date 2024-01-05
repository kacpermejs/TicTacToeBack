package com.tic.tac.tictactoeback.managers;

import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tic.tac.tictactoeback.models.QueueEntry;
import com.tic.tac.tictactoeback.services.GameService;

@Component
public class LobbyManager {
    private static final Queue<QueueEntry> lobby = new ConcurrentLinkedQueue<>();

    @Autowired
    GameService gameService;

    public static void addUserToQueue(QueueEntry entry) {
        lobby.add(entry);
    }
    
    public static QueueEntry removeFromQueueWithSessionId(String sessionId) {
        Iterator<QueueEntry> iterator = lobby.iterator();
        QueueEntry removedElement = null;

        while (iterator.hasNext()) {
            var entry = iterator.next();

            // Compare the attribute and remove the element if it matches
            if (entry.sessionId() == sessionId) {
                removedElement = entry;
                iterator.remove();
                break; // Assuming there is at most one element with the given attribute
            }
        }
        return removedElement;
    }

    public static QueueEntry removeFromQueueWithUserId(Long userId) {
        Iterator<QueueEntry> iterator = lobby.iterator();
        QueueEntry removedElement = null;

        while (iterator.hasNext()) {
            var entry = iterator.next();

            // Compare the attribute and remove the element if it matches
            if (entry.userId() == userId) {
                removedElement = entry;
                iterator.remove();
                break; // Assuming there is at most one element with the given attribute
            }
        }
        return removedElement;
    }

    public static void printQueue() {
        // Print the current state of the queue (for debugging purposes)
        System.out.println("Current Queue: " + lobby);
    }

    public static void tryMatchPlayers(BiConsumer<Long, Long> matchHandler) {
        // Check if there are enough players to form a pair
        if (lobby.size() >= 2) {
            // Pop two players from the queue
            Long playerOneId = lobby.poll().userId();
            Long playerTwoId = lobby.poll().userId();

            // Handle the matched players (e.g., create a game session)
            if (matchHandler != null) {
                matchHandler.accept(playerOneId, playerTwoId);
            }
        }
    }

}
