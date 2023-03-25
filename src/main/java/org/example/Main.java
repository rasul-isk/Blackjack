package org.example;

import java.io.*;
import java.util.List;

public class Main {
    private static final String FILE_SEPARATOR = File.separator;

    //Method to read file, initialise objects and return all-in-one "games" object
    static Games readFile(String fileName) {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("Game Data" + FILE_SEPARATOR + fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            Games games = new Games();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] line_parts = line.split(",");
                boolean hasEmptyOrNull = List.of(line_parts).stream().anyMatch(str -> str == null || str.isEmpty());

                if (line_parts.length == 6 && !hasEmptyOrNull) {
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

            games.sort();
            return games;

        } catch (IOException e) {
            e.printStackTrace();
            return new Games();
        }
    }

    //Method to write output file
    static void writeFile(String filePath, String input) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Looping through existing data files and writing output files
    public static void main(String[] args) {
        Integer iterator = 0;
        String filesPath = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "";
        File file = new File("." + FILE_SEPARATOR);
        while (file.exists()) {
            Games game = readFile("game_data_" + iterator + ".txt");
            System.out.println(game.toString());
            String errors = game.findInvalidSessions();
            writeFile(filesPath + "Analyze" + FILE_SEPARATOR + "analyzer_output_" + iterator + ".txt", errors);

            iterator++;
            file = new File(filesPath + "Game Data" + FILE_SEPARATOR + "game_data_" + iterator + ".txt");
        }
    }
}
