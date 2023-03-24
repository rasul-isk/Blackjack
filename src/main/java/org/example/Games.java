package org.example;
import java.lang.reflect.Array;
import java.util.*;

public class Games {



    private Map<Integer, List<Session>> games;  // <gameSessionId, list of its sessions>

    public Games() {
        games = new TreeMap<>();
    } //using TreeMap allows to sort keys (session IDs) by default

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

    public String detectErrors() {

        System.out.println(cardsToValue("QD-2D-AA"));
        StringBuilder sb = new StringBuilder();

        for(List<Session> sessions : games.values()) {
            for(Session session : sessions) {
                if(!isSessionValid(session)) {
                    sb.append(session.toFile() + "\n");
                    break;
                };
            }
        }
        return sb.toString();
    }

    private boolean isSessionValid(Session session) {
        boolean check = true;
        String[] action = session.action().split(" ");
        String player = session.playerHand();
        String dealer = session.dealerHand();
        boolean validHands = !Objects.equals(dealer.split("-")[0], "?") && Objects.equals(dealer.split("-")[1], "?") && !player.contains("?");
        boolean notBustedP = cardsToValue(player) < 22;
        boolean notBustedD = cardsToValue(dealer) < 22;

            System.out.println("HERE " + cardsToValue("kS-8C-2C"));
//        if(player.equals("2d-2h-2S-2c-3D")) {
//            System.out.println("notBustedP " + notBustedP);
//            System.out.println("notBustedD " + notBustedD);
//        }
        switch (action[1]) {
            case "Joined": // works
            case "Redeal": //?
                check = validHands && notBustedP;
                break;
            case "Hit":
                if(Objects.equals(action[0], "P")) {
                    check = notBustedP && notBustedD && cardsToValue(player) < 20;
                } else {
                    check = notBustedP && notBustedD && cardsToValue(dealer) < 20;
                }
                break;
            case "Stand":
            case "Show":
                check = notBustedP && notBustedD;
                break;
            case "Win":
                if(Objects.equals(action[0], "P")) {
                    check = notBustedP && !notBustedD;
                } else if(Objects.equals(action[0], "D")) {
                    check = !notBustedP && notBustedD;
                }
                break;
            case "Lose":
                if(Objects.equals(action[0], "P")) {
                    check = !notBustedP && notBustedD;
                } else if(Objects.equals(action[0], "D")) {
                    check = notBustedP && !notBustedD;
                }
                break;
            case "Left":
                break;
            default:
                check = false;
                break;
        }

        return check;
    }

    private Integer cardsToValue(String cardsOnHands) {
        String[] cards = cardsOnHands.split("-");
        Integer sum = 0;

        for (String card : cards) {
            if (card.matches("[JQKjqk].*")) {
                sum += 10;
            }
            else if(card.matches("[Aa].*")){
                sum += 11;
            }
            else {
                try {
                    int num = Integer.parseInt(card.substring(0,card.length()-1));
                    sum += num;
                } catch (NumberFormatException e) {
                    // no throws required
                }
            }
        }
        return sum;
    }


    //loop through each game session and check if everything is ok, if no, add it to main "faulcies" string
    //finally, write errors to the file, sorted in order of session IDs
    //make program dynamic to check game_date_INTEGER exists, analyze each of such file and write output for it
    public String toString() {
        StringBuilder result = new StringBuilder();
        for(List<Session> sessions : games.values()) {
            for(Session session : sessions) {
                result.append(session.toString() + "\n");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
