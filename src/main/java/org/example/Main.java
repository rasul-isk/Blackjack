package org.example;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<String> ReadFile(String fileName) {
        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("Game Data/" + fileName);

            if (input == null) {
                throw new FileNotFoundException("File not found: " + fileName);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            ArrayList<String> lines = new ArrayList<String>();
            String line;
            while ((line =  reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        for (String line : ReadFile("game_data_0.txt")){
            System.out.println(line);
        }
    }
}
