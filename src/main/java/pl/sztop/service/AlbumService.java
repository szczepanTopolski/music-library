package pl.sztop.service;

import com.google.common.collect.Iterables;
import pl.sztop.Utils;
import pl.sztop.model.Album;
import pl.sztop.repository.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
        this.inMemoryCache = repository.findAll(ALBUMS_CSV_PATH);
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

    public void addAlbum() {
        inMemoryCache.add(IOService.albumFromInput());
        repository.saveAll(inMemoryCache, ALBUMS_CSV_PATH);
    }

    void find(Function<Album, String> by, String label) {
        List<String> findBy = inMemoryCache
                .stream()
                .map(by)
                .distinct()
                .collect(Collectors.toList());

        Utils.tryParseInput(IOService.getInputFromPossibilities(findBy), findBy.size())
                .ifPresent(choose -> IOService.display(inMemoryCache.stream()
                        .filter(album -> by.apply(album).equals(findBy.get(choose - 1)))
                        .collect(Collectors.toList()), label)
                );
    }


    public void displayStatistics() {
        List<Album> sorted = inMemoryCache.stream()
                .sorted(Comparator.comparing(Album::getDuration))
                .collect(Collectors.toList());
        Album longest = Iterables.getLast(sorted);
        Album shortest = Iterables.getFirst(sorted, null);
        if (longest == null || shortest == null) return;
        final Map.Entry<String, Long> mostFrequent = mostFrequent(Album::getAuthor);
        IOService.displayStatistics(createStatisticsMap(longest, shortest, mostFrequent));
    }

    public void deleteAlbum() {
        Utils.tryParseInput(IOService.getInputFromPossibilities(inMemoryCache), inMemoryCache.size())
                .ifPresent(index -> {
                            inMemoryCache.remove(index.intValue());
                            repository.saveAll(inMemoryCache, ALBUMS_CSV_PATH);
                        }
                );
    }

    private List<Album> getAlbumsByAuthor(String author) {
        return inMemoryCache.stream()
                .filter(album -> album.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    private Map.Entry<String, Long> mostFrequent(Function<Album, String> of) {
        return inMemoryCache.stream()
                .map(of)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();
    }


    private Map<String, Object> createStatisticsMap(Album longest,
                                                    Album shortest,
                                                    Map.Entry<String, Long> mostFrequent) {
        return Map.of(
                "Longest album: ", longest,
                "Shortest album: ", shortest,
                "Most frequent author: ",
                String.format("%s with %d albums", mostFrequent.getKey(), mostFrequent.getValue()),
                "Albums by most frequent author: \n", getAlbumsByAuthor(mostFrequent.getKey()));
    }

    public void exit() {
        System.exit(0);
    }
}
