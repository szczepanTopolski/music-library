package pl.sztop.service;

import pl.sztop.model.Album;
import pl.sztop.repository.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FakeRepository extends Repository {

    private final List<Album> inMemoryAlbums;

    public FakeRepository() {
        this.inMemoryAlbums = initializeInMemory();
    }

    private List<Album> initializeInMemory() {
        return IntStream
                .range(0, 10)
                .mapToObj(FakeRepository::createAlbum)
                .collect(Collectors.toList());
    }

    public static Album createAlbum(int id) {
        return Album.builder()
                .album_name("test_name_" + id)
                .genre("test_genre_" + id)
                .author("test_author_" + id)
                .duration(LocalTime.of(id, id))
                .releaseYear(2020)
                .build();
    }

    @Override
    public List<Album> findAll(String fileName) {
        return inMemoryAlbums;
    }

    @Override
    public void saveAll(List<Album> albums, String fileName) {
        inMemoryAlbums.clear();
        inMemoryAlbums.addAll(albums);
    }
}
