package main;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int word_count = 0;
        int special_count = 0;
        int space_count = 0;
        try(Scanner scanner = new Scanner(new File(args[0]))) {
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                boolean wasWord = false;
                for(char c : line.toCharArray()) {
                    if(c == ' ') {
                        space_count++;
                        wasWord = false;
                    } else if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
                        if(!wasWord) {
                            word_count++;
                            wasWord = true;
                        }
                    } else {
                        special_count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(word_count + " slov, " + space_count + " medzier, " + special_count + " ine znaky.");
    }
}
