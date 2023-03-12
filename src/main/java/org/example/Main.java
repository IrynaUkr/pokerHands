package org.example;

import org.example.engine.PokerHands;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter line");

        String line = scanner.nextLine();
        PokerHands comparator = new PokerHands();
        System.out.println(comparator.playPoker(line));


    }

}