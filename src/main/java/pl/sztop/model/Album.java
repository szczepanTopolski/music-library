package pl.sztop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Album {

    private final String author;
    private final String album_name;
    private final int releaseYear;
    private final String genre;
    private final String duration;


    public String[] getSimpleForm() {
        return new String[]{author,
                album_name,
                String.valueOf(releaseYear),
                genre,
                duration};
    }
}
