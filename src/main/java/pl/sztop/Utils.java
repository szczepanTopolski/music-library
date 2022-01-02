package pl.sztop;

import pl.sztop.repository.dictionary.Dictionary;
import pl.sztop.service.IOService;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Utils {

    public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(Dictionary.TIME_PATTERN);

    public static Optional<Integer> tryParseInput(String input, int range) {
        try {
            Optional<Integer> parsed = Optional.of(Integer.parseInt(input));
            parsed.ifPresent(value -> {
                if (value > range - 1 || value < 1) throw new RuntimeException("Illegal range");
            });
            return parsed;
        } catch (Exception e) {
            IOService.displayErrorMessage("Invalid input - try again.");
            return Optional.empty();
        }
    }
}
