package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Movie> movieDatabase = new ArrayList<>();
    private int keuzeMenu;
    private int selectieBewerking;
    private String prompt;

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

        // Controleer input op getal
        try {
            keuzeMenu = Integer.parseInt(scanner.nextLine());
            uitvoeringMenu();
        } catch (NumberFormatException ex) {
            System.out.println("Onjuiste invoer, probeer opnieuw.");
            toonMenu();
        }
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
                int keuzeBewerk = selecteerFilm();
                selecteerBewerking();
                uitvoeringBewerking(keuzeBewerk);
                bewaarDatabank();
                toonMenu();
                break;
            case 4:
                int keuzeVerwijder = selecteerFilm();
                verwijderFilm(keuzeVerwijder);
                bewaarDatabank();
                toonMenu();
                break;
            case 5:
                createHTML();
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
        // Controleer input op getal
        prompt = "Jaar: ";
        int jaar = checkGetal(prompt);
        movie.setYear(jaar);
        System.out.print("Genre: ");
        movie.setGenre(scanner.nextLine());
        // Controleer input op getal
        prompt = "Rating (op 5): ";
        int rating = checkGetal(prompt);
        movie.setRating(rating);

        //voeg film toe aan databank
        movieDatabase.add(movie);
        System.out.printf("%s is toegevoegd aan de databank.", movie.getTitle());
    }

    private void verwijderFilm(int selectieFilm) {
        System.out.println();
        String title = movieDatabase.get(selectieFilm).getTitle();
        System.out.printf("Weet uw zeker dat uw \"%s\" wilt verwijderen (Ja/Nee)? ", title);
        String antwoord = scanner.nextLine();
        while (!antwoord.equalsIgnoreCase("ja") && !antwoord.equalsIgnoreCase("nee")) {
            System.out.print("Onjuiste invoer, probeer opnieuw: ");
            antwoord = scanner.nextLine();
        }
        if (antwoord.equalsIgnoreCase("ja")) {
            movieDatabase.remove(selectieFilm);
            System.out.printf("%s is verwijderd.", title);
        } else return;
    }

    private int selecteerFilm() {
        toonDatabank();
        System.out.println();
        // Controleer input op getal
        prompt = "Selecteer een film (nr): ";
        int selectieFilm = checkGetal(prompt) - 1;
        while (selectieFilm < 0 && selectieFilm >= movieDatabase.size()) {
            System.out.println("Onjuiste invoer, probeer opnieuw.");
            selectieFilm = checkGetal(prompt) - 1;
        }
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
                "\t6-- Annuleer\n" +
                "Uw keuze: ");

        selectieBewerking = Integer.parseInt(scanner.nextLine());
    }

    private void uitvoeringBewerking(int selectieFilm) {
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
                // Controleer input op getal
                prompt = "Jaar: ";
                int jaar = checkGetal(prompt);
                movieDatabase.get(selectieFilm).setYear(jaar);
                break;
            case 4:
                System.out.print("Genre: ");
                movieDatabase.get(selectieFilm).setGenre(scanner.nextLine());
                break;
            case 5:
                // Controleer input op getal
                prompt = "Rating (op 5): ";
                int rating = checkGetal(prompt);
                movieDatabase.get(selectieFilm).setRating(rating);
                break;
            case 6:
                return;
            default:
                System.out.println("Onjuiste invoer, probeer opnieuw: ");
        }
    }

    private void createHTML() {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("moviedatabase.html")))) {
            // Schrijf HTML naar bestand
            String html = "<!doctype html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "  <meta charset=\"utf-8\">\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                    "  <title>Movie database</title>\n" +
                    "  <script type=\"text/javascript\">\n" +
                    "    function load() {\n" +
                    "      window.location.href = \"https://www.imdb.com\";\n" +
                    "    }\n" +
                    "  </script>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body onload=\"load()\">\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
            writer.println(html);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Methode checkt of input een getal is
    private int checkGetal(String prompt) {
        boolean check;
        check = false;
        int getal = 0;
        do {
            System.out.print(prompt);
            try {
                getal = Integer.parseInt(scanner.nextLine());
                check = true;
            } catch (NumberFormatException ex) {
                System.out.println("Onjuiste invoer, probeer opnieuw.");
            }
        } while (!check);
        return getal;
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

