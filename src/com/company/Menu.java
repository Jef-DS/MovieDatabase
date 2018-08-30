package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Movie> movieDatabase = new ArrayList<>();
    private int keuzeMenu;
    private int selectieFilm;
    private int selectieBewerking;

    public Menu() {
        laadDatabank();
        toonMenu();
    }

    public void toonMenu() {
        System.out.println();
        System.out.print("Wat wilt u doen?\n" +
                "\t1-- Voeg film toe\n" +
                "\t2-- Toon databank\n" +
                "\t3-- Bewerk film\n" +
                "\t4-- Verwijder film\n" +
                "\t5-- Creëer HTML\n" +
                "\t6-- Exit\n" +
                "Uw keuze: ");

        keuzeMenu = Integer.parseInt(scanner.nextLine());
        uitvoeringMenu();
    }

    public void uitvoeringMenu() {
        switch (keuzeMenu) {
            case 1:
                voegToe();
                bewaarDatabank();
                toonMenu();
                break;
            case 2:
                toonDatabank();
                toonMenu();
                break;
            case 3:
                selecteerFilm();
                selecteerBewerking();
                bewaarDatabank();
                toonMenu();
                break;
            case 4:
                //verwijderFilm();
                //bewaarDatabank();
                toonMenu();
                break;
            case 5:
                //createHTML();
                toonMenu();
                break;
            case 6:
                System.exit(0);
                break;
            default:
                System.out.println("Onjuiste invoer, probeer opnieuw.");
                toonMenu();
        }
    }

    private void voegToe() {
        System.out.println();

        //creëer nieuwe film en vraag gegevens
        Movie movie = new Movie(null, null, 0, null, 0);
        System.out.print("Titel: ");
        movie.setTitle(scanner.nextLine());
        System.out.print("Regisseur: ");
        movie.setDirector(scanner.nextLine());
        System.out.print("Jaar: ");
        movie.setYear(Integer.parseInt(scanner.nextLine()));
        System.out.print("Genre: ");
        movie.setGenre(scanner.nextLine());
        System.out.print("Rating (op 5): ");
        movie.setYear(Integer.parseInt(scanner.nextLine()));

        //voeg film toe aan databank
        movieDatabase.add(movie);
        System.out.printf("%s is toegevoegd aan de databank.", movie.getTitle());
    }

    private int selecteerFilm() {
        toonDatabank();
        System.out.print("Welke film wilt u bewerken: ");
        selectieFilm = Integer.parseInt(scanner.nextLine())-1;
        return selectieFilm;
    }

    private void selecteerBewerking() {
        System.out.println();
        System.out.print("Wat wilt u wijzigen?\n" +
                "\t1-- Titel\n" +
                "\t2-- Regisseur\n" +
                "\t3-- Jaar\n" +
                "\t4-- Genre\n" +
                "\t5-- Rating\n" +
                "\t6-- Exit\n" +
                "Uw keuze: ");

        selectieBewerking = Integer.parseInt(scanner.nextLine());
        uitvoeringBewerking();
    }

    private void uitvoeringBewerking() {
        switch (selectieBewerking) {
            case 1:
                System.out.print("Titel: ");
                movieDatabase.get(selectieFilm).setTitle(scanner.nextLine());
                break;
            case 2:
                System.out.print("Regisseur: ");
                movieDatabase.get(selectieFilm).setDirector(scanner.nextLine());
                break;
            case 3:
                System.out.print("Jaar: ");
                movieDatabase.get(selectieFilm).setYear(Integer.parseInt(scanner.nextLine()));
                break;
            case 4:
                System.out.print("Genre: ");
                movieDatabase.get(selectieFilm).setGenre(scanner.nextLine());
                break;
            case 5:
                System.out.print("Rating (op 5): ");
                movieDatabase.get(selectieFilm).setRating(Integer.parseInt(scanner.nextLine()));
                break;
            case 6:
                return;
            default:
                System.out.println("Onjuiste invoer, probeer opnieuw: ");
        }
    }


    private void toonDatabank() {
        System.out.println();
        for (int i = 0; i < movieDatabase.size(); i++) {
            Movie movie = movieDatabase.get(i);
            System.out.printf("\t#%d-- %s van %s - %d, %s, %d (op 5).%n", i + 1, movie.getTitle(), movie.getDirector(), movie.getYear(), movie.getGenre(), movie.getRating());
        }
    }

    private void bewaarDatabank() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("moviedatabase.txt")))) {
            // Schrijf films naar bestand
            for (Movie movie : movieDatabase) {
                String filmInfo = String.format("%s;%s;%d;%s;%d", movie.getTitle(), movie.getDirector(), movie.getYear(), movie.getGenre(), movie.getRating());
                writer.println(filmInfo);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void laadDatabank() {
        try {
            Scanner reader = new Scanner(new BufferedReader(new FileReader("moviedatabase.txt")));
            // Lees lijnen in bestand
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] array = line.split(";");
                // Creëer nieuwe film en voeg toe in database
                Movie movie = new Movie(array[0], array[1], Integer.parseInt(array[2]), array[3], Integer.parseInt(array[4]));
                movieDatabase.add(movie);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

