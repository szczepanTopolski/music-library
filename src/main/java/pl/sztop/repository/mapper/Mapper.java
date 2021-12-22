package pl.sztop.repository.mapper;

import org.apache.commons.csv.CSVRecord;
import pl.sztop.model.Album;

import java.time.LocalTime;

public class Mapper {
    public static Album mapToAlbum(String[] data) {
        return Album.builder()
                .author(data[0])
                .album_name(data[1])
                .releaseYear(Integer.parseInt(data[2]))
                .genre(data[3])
                .duration(mapDuration(data[4]))
                .build();
    }

    public static Album mapToAlbum(CSVRecord record) {
        return Album.builder()
                .author(record.get("author"))
                .album_name(record.get("album_name"))
                .releaseYear(Integer.parseInt(record.get("release_year")))
                .genre(record.get("genre"))
                .duration(mapDuration(record.get("duration")))
                .build();
    }

    public static LocalTime mapDuration(String durationString) {
        String[] duration = durationString.split(":");
        final int HOURS_INDEX = 0;
        final int MINUTES_INDEX = 1;
        final int SECONDS_INDEX = 2;
        final int CONVERTER = 60;
        int hours = Integer.parseInt(duration[HOURS_INDEX]);
        int minutes = Integer.parseInt(duration[MINUTES_INDEX]);
        int seconds = Integer.parseInt(duration[SECONDS_INDEX]);
        hours = minutes > CONVERTER ? hours + minutes / CONVERTER : hours;
        minutes = minutes > CONVERTER ? minutes % CONVERTER : minutes;
        minutes = seconds > CONVERTER ? minutes + seconds / CONVERTER : minutes;
        seconds = seconds > CONVERTER ? seconds % CONVERTER : seconds;
        return LocalTime.of(hours, minutes, seconds);
    }
}
