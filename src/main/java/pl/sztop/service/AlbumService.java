package pl.sztop.service;

import pl.sztop.Utills;
import pl.sztop.model.Album;
import pl.sztop.repository.Repository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AlbumService {

    private static final String ALBUMS_CSV_PATH = "src\\main\\resources\\albums.csv";
    private final Repository repository;
    private List<Album> inMemoryCache;

    public AlbumService(Repository repository) {
        this.repository = repository;
        synchronizeAlbums();
    }


    public void synchronizeAlbums() {
        this.inMemoryCache = repository.importAlbums(ALBUMS_CSV_PATH);
    }

    public void displayAlbums() {
        IOService.display(inMemoryCache, "\n Albums in memory: ");
    }

    public void findByAuthor() {
        find(Album::getAuthor, "\n Authors:");
    }

    public void findByGenre() {
        find(Album::getGenre, "\n Genres:");
    }

    public void find(Function<Album, String> by, String label) {
        List<String> findBy = inMemoryCache
                .stream()
                .map(by)
                .distinct()
                .collect(Collectors.toList());

        Utills.tryParseInput(IOService.getInputFromPossibilities(findBy))
                .ifPresent(choose -> IOService.display(inMemoryCache.stream()
                        .filter(album -> by.apply(album).equals(findBy.get(choose - 1)))
                        .collect(Collectors.toList()), label)
                );
    }


    public void addAlbum() {
        synchronizeAlbums();
        inMemoryCache.add(IOService.albumFromInput());
        repository.exportAlbums(inMemoryCache, ALBUMS_CSV_PATH);
    }


    public void exit() {
        System.exit(0);
    }
}
