package org.example;

public record Session(long timestamp, int gameSessionId, int playerId, String action, String dealerHand, String playerHand) {
    @Override
    public String toString() {
        return  "Timestamp = " + timestamp +
                ", Session ID = " + gameSessionId +
                ", Player ID = " + playerId +
                ", Action  '" + action + '\'' +
                ", Dealer's Hand = '" + dealerHand + '\'' +
                ", Player's Hand = '" + playerHand + '\'';
    }
    public String toFile() {
        return  timestamp + "," + gameSessionId + "," + playerId + "," + action + "," + dealerHand + "," + playerHand;
    }
}

