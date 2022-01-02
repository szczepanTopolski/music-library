package pl.sztop.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.sztop.model.Album;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.google.common.base.CharMatcher.breakingWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AlbumServiceTest {

    private AlbumService albumService;
    private ByteArrayOutputStream outContent;

    static Stream<Arguments> valuesProvider() {
        return Stream.of(
                arguments("20"),
                arguments("XD"),
                arguments(".25%"),
                arguments("12312341234")
        );
    }

    @BeforeEach
    private void setup() {
        albumService = new AlbumService(new FakeRepository());
        outContent = new ByteArrayOutputStream();
        IOService.setOutput(new PrintStream(outContent));
    }

    @AfterEach
    private void restoreDefault() {
        IOService.setOutput(System.out);
        IOService.setInput(new Scanner(System.in));
    }

    @Test
    public void should_find_by_author() {
        //arrange
        Function<Album, String> by = Album::getAuthor;
        String input = "1";
        final String label = "Authors:";
        String expected = String.format("(1). %s", FakeRepository.createAlbum(0).toString());
        //act
        InputStream in = new ByteArrayInputStream(input.getBytes());
        IOService.setInput(new Scanner(in));
        albumService.find(by, label);
        String actual = Iterables.getLast(Splitter.on(label).trimResults(breakingWhitespace()).split(outContent.toString()));
        //assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("valuesProvider")
    public void find_by_author_should_display_error_message_when_incorrect_input(String input) {
        //arrange
        Function<Album, String> by = Album::getAuthor;
        final String label = "Your input:";
        String expected = "Something got kurwa wrong, cause: Invalid input - try again.";
        //act
        InputStream in = new ByteArrayInputStream(input.getBytes());
        IOService.setInput(new Scanner(in));
        albumService.find(by, label);
        String actual = Iterables.getLast(Splitter.on(label).trimResults(breakingWhitespace()).split(outContent.toString()));
        //assert
        assertEquals(expected, actual);
    }
}