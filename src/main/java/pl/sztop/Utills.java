package pl.sztop;

import pl.sztop.service.IOService;

import java.util.Optional;

public class Utills {

    public static Optional<Integer> tryParseInput(String input) {
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (Exception e) {
            IOService.displayErrorMessage("Invalid input - try again.");
            return Optional.empty();
        }
    }
}
