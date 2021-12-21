package pl.sztop.repository.mapper;

import org.apache.commons.csv.CSVRecord;
import pl.sztop.model.Album;

public class Mapper {
    public static Album mapToAlbum(String[] data) {
        return Album.builder()
                .author(data[0])
                .album_name(data[1])
                .releaseYear(Integer.parseInt(data[2]))
                .genre(data[3])
                .duration(data[4])
                .build();
    }

    public static Album mapToAlbum(CSVRecord record) {
        return Album.builder()
                .author(record.get("author"))
                .album_name(record.get("album_name"))
                .releaseYear(Integer.parseInt(record.get("release_year")))
                .genre(record.get("genre"))
                .duration(record.get("duration"))
                .build();
    }
}
