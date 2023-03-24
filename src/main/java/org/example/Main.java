package org.example;

import java.io.*;
import java.sql.Array;
import java.util.*;

public class Main {

    static Games ReadFile(String fileName) {
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("Game Data/" + fileName);

            if (input == null) {
                throw new FileNotFoundException("Requested file not found: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Games games = new Games();
            String line;
            while ((line =  reader.readLine()) != null) {
                //  timestamp | gameSessionId | playerId | action | dealerHand | playerHand
                String[] line_parts = line.split(",");
                boolean hasEmptyOrNull = Arrays.stream(line_parts).anyMatch(str -> str == null || str.isEmpty());

                if(line_parts.length == 6 && !hasEmptyOrNull) {
                    Session gameSession = new Session(
                            Long.parseLong(line_parts[0]),
                            Integer.parseInt(line_parts[1]),
                            Integer.parseInt(line_parts[2]),
                            line_parts[3],
                            line_parts[4],
                            line_parts[5]
                    );
                    games.addSession(gameSession);
                }
            }

            reader.close();

            games.Sort();

            return games;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
//        Read files
//        for(int i = 1; i<4; i++) {
//
//        }
        Games game = ReadFile("game_data_1.txt");
//        System.out.println(game.toString());
        System.out.println(game.detectErrors());
    }
}
