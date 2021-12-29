package pl.sztop.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import pl.sztop.Utills;

import java.time.LocalTime;

@Builder
@Getter
@ToString
@EqualsAndHashCode(exclude = "duration")
public class Album {

    private final String author;
    private final String album_name;
    private final int releaseYear;
    private final String genre;
    private final LocalTime duration;


    public String[] getSimpleForm() {
        return new String[]{author,
                album_name,
                String.valueOf(releaseYear),
                genre,
                duration.format(Utills.TIME_FORMATTER)};
    }
}
