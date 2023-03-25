package org.example;

import java.util.*;

public class Games {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private Map<Integer, List<Session>> games;  // <gameSessionId, list of its sessions>

    public Games() {
        games = new TreeMap<>();
    } //using TreeMap allows to sort keys (session IDs) by default

    //Adding session to relevant key (game session ID)
    public void addSession(Session session) {
        int gameId = session.gameSessionId();
        List<Session> sessions = games.getOrDefault(gameId, new ArrayList<>());
        sessions.add(session);
        games.put(gameId, sessions);
    }

    //Sorting lists of sessions by timestamp
    public void Sort() {
        for (List<Session> sessions : games.values()) {
            Collections.sort(sessions, Comparator.comparingLong(Session::timestamp));
        }
    }

    //Looping through all sessions to
    public String findInvalidSessions() {
        StringBuilder sb = new StringBuilder();

        for (List<Session> sessions : games.values()) {
            for (Session session : sessions) {
                if (!isSessionValid(session)) {
                    sb.append(session.toFile()).append(LINE_SEPARATOR);
                    break;
                }
            }
        }
        return sb.toString();
    }

    //Validating session by possible conditions
    private boolean isSessionValid(Session session) {
        if (session.action().contains("Left")) {
            return true;
        }

        String[] action = session.action().split(" ");
        String player = session.playerHand();
        String dealer = session.dealerHand();

        boolean check = true;
        boolean validHands = !dealer.split("-")[0].equals("?") && dealer.split("-")[1].equals("?") && !player.contains("?");
        boolean notBustedP = cardsToValue(player) < 22;
        boolean notBustedD = cardsToValue(dealer) < 22;

        switch (action[1]) {
            case "Joined":
            case "Redeal":
                check = validHands && notBustedP;
                return check;
            case "Hit":
                if (action[0].equals("D")) {
                    check = notBustedP && notBustedD && cardsToValue(dealer) < 17;
                } else if (action[0].equals("P")) {
                    check = notBustedP && notBustedD && cardsToValue(player) < 20;
                }
                return check;
            case "Stand":
            case "Show":
                check = notBustedP && notBustedD;
                return check;
            case "Win":
                if (action[0].equals("P")) {
                    check = notBustedP && cardsToValue(player) >= cardsToValue(dealer) && cardsToValue(dealer) >= 17 || notBustedP && !notBustedD;
                } else if (action[0].equals("D")) {
                    check = notBustedD && cardsToValue(player) < cardsToValue(dealer) || !notBustedP && notBustedD;
                }
                return check;
            case "Lose":
                if (action[0].equals("P")) {
                    check = !notBustedP && notBustedD;
                } else if (Objects.equals(action[0], "D")) {
                    check = notBustedP && !notBustedD;
                }
                return check;
        }
        return false;
    }

    //Summing up cards on hands
    private Integer cardsToValue(String cardsOnHands) {
        String[] cards = cardsOnHands.split("-");
        Integer sum = 0;

        for (String card : cards) {
            if (card.matches("[JQKjqk].*")) {
                sum += 10;
            } else if (card.matches("[Aa].*")) {
                sum += 11;
            } else {
                try {
                    int num = Integer.parseInt(card.substring(0, card.length() - 1));
                    sum += num;
                } catch (NumberFormatException e) {
                    // No throws required here
                }
            }
        }
        return sum;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (List<Session> sessions : games.values()) {
            for (Session session : sessions) {
                result.append(session.toString()).append(LINE_SEPARATOR);
            }
            result.append(LINE_SEPARATOR);
        }
        return result.toString();
    }
}
