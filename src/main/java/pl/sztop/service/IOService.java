package pl.sztop.service;

import pl.sztop.model.Album;
import pl.sztop.repository.mapper.Mapper;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class IOService {

    static Scanner sc;
    static PrintStream out;

    static {
        sc = new Scanner(System.in);
        out = System.out;
    }

    public static void setOutput(PrintStream output) {
        out = output;
    }

    public static void setInput(Scanner input) {
        sc = input;
    }

    public static <T> void display(List<T> list, String label) {
        out.println(label);
        AtomicInteger counter = new AtomicInteger(1);
        list.forEach(line -> {
            out.printf("(%d). %s%n", counter.get(), line);
            counter.getAndIncrement();
        });
    }

    public static String getUserInput(String message) {
        out.println(message);
        return sc.nextLine();
    }

    public static void displayErrorMessage(String cause) {
        out.println("Something got kurwa wrong, cause: " + cause);
    }

    public static String getInputFromPossibilities(List<?> list) {
        display(list, "\n Choose from possibilities: ");
        return getUserInput("Your input: ");
    }

    public static Album albumFromInput() {

        String[] line = getUserInput(
                """
                        Insert in correct pattern []
                        [ author, album name, release year, genre, duration]:
                        """
        )
                .replaceAll(" ", "")
                .split(",");
        return Mapper.mapToAlbum(line);
    }

    public static void displayStatistics(Map<String, Object> statistics) {
        statistics.forEach((key, value) -> out.println(key + value));
    }
}
