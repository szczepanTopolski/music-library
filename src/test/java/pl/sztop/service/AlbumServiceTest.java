package pl.sztop.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sztop.model.Album;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Function;

import static com.google.common.base.CharMatcher.breakingWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AlbumServiceTest {

    private AlbumService albumService;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    private void setup() {
        albumService = new AlbumService(new FakeRepository());
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void should_find_by_author() {
        //given
        Function<Album, String> by = Album::getAuthor;
        String input = "1";
        final String label = "Authors:";
        String expected = String.format("(1). %s", FakeRepository.createAlbum(0).toString());
        //arrange
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        albumService.find(by, label);
        String actual = Iterables.getLast(Splitter.on(label).trimResults(breakingWhitespace()).split(outContent.toString()));
        //assert
        assertEquals(expected, actual);
    }

}