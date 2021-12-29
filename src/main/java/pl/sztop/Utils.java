package pl.sztop;

import pl.sztop.repository.dictionary.Dictionary;
import pl.sztop.service.IOService;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {

    public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(Dictionary.TIME_PATTERN);

    public static Optional<Integer> tryParseInput(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (Exception e) {
            IOService.displayErrorMessage("Invalid input - try again.");
            return Optional.empty();
        }
    }
}
