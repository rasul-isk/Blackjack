package org.example;

public record Session(long timestamp,
                      int gameSessionId,
                      int playerId,
                      String action,
                      String dealerHand,
                      String playerHand) {
    private static final String SESSION_FORMAT = "Timestamp = %d, Session ID = %d, Player ID = %d, " +
            "Action = '%s', Dealer's Hand = '%s', Player's Hand = '%s'";

    @Override
    public String toString() {
        return String.format(SESSION_FORMAT, timestamp, gameSessionId, playerId, action, dealerHand, playerHand);
    }

    //Same as toString, but for writing session into file
    public String toFile() {
        return timestamp + "," + gameSessionId + "," + playerId + "," + action + "," + dealerHand + "," + playerHand;
    }
}

