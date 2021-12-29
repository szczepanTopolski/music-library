package pl.sztop.repository.mapper;

import org.apache.commons.csv.CSVRecord;
import pl.sztop.model.Album;

import java.time.LocalTime;

import static pl.sztop.repository.dictionary.Dictionary.*;

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
                .author(record.get(CSV_AUTHOR_HEADER))
                .album_name(record.get(CSV_ALBUM_NAME_HEADER))
                .releaseYear(Integer.parseInt(record.get(CSV_RELEASE_YEAR_HEADER)))
                .genre(record.get(CSV_GENRE_HEADER))
                .duration(mapDuration(record.get(CSV_DURATION_HEADER)))
                .build();
    }

    public static LocalTime mapDuration(String durationString) {
        String[] duration = durationString.split(CSV_DURATION_REGEX);
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
