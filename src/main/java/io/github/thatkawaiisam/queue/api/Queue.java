package io.github.thatkawaiisam.queue.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.UUID;

@Getter @Setter
public class Queue {

    private String id;
    private String displayName;
    private boolean enabled = true;
    private boolean paused = false;
    private boolean allowDecay = true;
    private PriorityQueue<QueueEntry> players = new PriorityQueue<>();

    /**
     * Queue class.
     *
     * @param id of the Queue.
     */
    public Queue(String id) {
        this.id = id;
    }

    /**
     * Check if player is in this current Queue.
     *
     * @param uuid of player to check.
     * @return if player is in queue.
     */
    public boolean containsPlayer(UUID uuid) {
        return this.players.stream().anyMatch(entry -> entry.getUuid().equals(uuid));
    }

    /**
     * Get the QueueEntry of a player in the current Queue.
     *
     * @param uuid of the player.
     * @return QueueEntry if it exists.
     */
    public QueueEntry getQueueEntry(UUID uuid) {
        for (QueueEntry queueEntry : players) {
            if (queueEntry.getUuid().equals(uuid)) {
                return queueEntry;
            }
        }
        return null;
    }

    /**
     * Get position of UUID in Queue.
     *
     * @param uuid of player to get position of.
     * @return position. This will return 0 if player is not in the Queue.
     */
    public int getPosition(UUID uuid) {
        if (!this.containsPlayer(uuid)) {
            return 0;
        }

        PriorityQueue<QueueEntry> queue = new PriorityQueue<>(this.players);

        int position = 0;

        while (!queue.isEmpty()) {
            QueueEntry entry = queue.poll();

            if (entry != null && entry.getUuid().equals(uuid)) {
                break;
            }

            position++;
        }

        return position + 1;
    }

    /**
     * Remove player from current Queue.
     *
     * @param uuid to be removed.
     */
    public void removePlayer(UUID uuid) {
        getPlayers().removeIf(other -> other.getUuid().equals(uuid));
    }

    /**
     * Remove player from current Queue.
     *
     * @param queueEntry to be removed
     */
    public void removePlayer(QueueEntry queueEntry) {
        removePlayer(queueEntry.getUuid());
    }

    /**
     * Gets the total amount of player's in the current Queue.
     *
     * @return total amount in cache.
     */
    public int getTotal() {
        return players.size();
    }
}
