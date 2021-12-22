package pl.sztop;

import pl.sztop.service.IOService;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utills {
    public static String TIME_PATTERN = "HH:mm:ss";
    public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(Utills.TIME_PATTERN);

    public static Optional<Integer> tryParseInput(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (Exception e) {
            IOService.displayErrorMessage("Invalid input - try again.");
            return Optional.empty();
        }
    }
}
