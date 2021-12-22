package pl.sztop.controller;

import pl.sztop.Utills;
import pl.sztop.service.AlbumService;
import pl.sztop.service.IOService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumController {
    private final Map<Integer, Runnable> menu;
    private final AlbumService service;
    private final List<String> options;

    public AlbumController(AlbumService service) {
        this.service = service;
        this.menu = postConstruct();
        this.options = List.of("Synchronize albums.",
                "Display all albums.",
                "Find by genre.",
                "Find by author",
                "Add new album.",
                "Display statistics.",
                "Exit");
    }

    public void start() {
        while (true) {
            IOService.display(options, "\n Menu: ");
            String input = IOService.getUserInput("What do you want to do?:");
            Utills.tryParseInput(input).ifPresent(integer -> menu.get(integer).run());
        }
    }


    private Map<Integer, Runnable> postConstruct() {
        Map<Integer, Runnable> menu = new HashMap<>();
        menu.put(1, service::synchronizeAlbums);
        menu.put(2, service::displayAlbums);
        menu.put(3, service::findByGenre);
        menu.put(4, service::findByAuthor);
        menu.put(5, service::addAlbum);
        menu.put(6, service::displayStatistics);
        menu.put(7, service::exit);
        return menu;
    }
}
