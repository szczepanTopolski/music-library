package pl.sztop.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import pl.sztop.model.Album;
import pl.sztop.repository.mapper.Mapper;
import pl.sztop.service.IOService;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Repository {

    private final static String[] HEADERS = {"author", "album_name", "release_year", "genre", "duration"};
    private final static char CSV_DELIMITER = ',';

    public List<Album> importAlbums(String fileName) {
        try {
            Reader in = new FileReader(fileName);

            Iterable<CSVRecord> iterator = CSVFormat.newFormat(CSV_DELIMITER)
                    .withFirstRecordAsHeader()
                    .parse(in);

            List<CSVRecord> records = new ArrayList<>();
            iterator.forEach(records::add);

            return records
                    .stream()
                    .map(Mapper::mapToAlbum)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public void exportAlbums(List<Album> albums, String fileName) {
        try (CSVPrinter printer = new CSVPrinter(new FileWriter(fileName), CSVFormat.newFormat(CSV_DELIMITER)
                .withHeader(HEADERS))) {
            printer.print("\n");
            convertToCSV(albums).forEach(s -> {
                s = s + "\n";
                tryWriteLine(printer, s);
            });

        } catch (IOException e) {
            IOService.displayErrorMessage("Unable to open the file!");
        }
    }

    private void tryWriteLine(CSVPrinter printer, String s) {
        try {
            printer.printRecord(s);
        } catch (IOException e) {
            IOService.displayErrorMessage("Unable to write line - " + s);
        }
    }

    public List<String> convertToCSV(List<Album> albums) {
        return albums.stream()
                .map(Album::getSimpleForm)
                .map(album -> Stream.of(album)
                        .map(this::escapeSpecialCharacters)
                        .collect(Collectors.joining(",")))
                .collect(Collectors.toList());
    }

    public String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
