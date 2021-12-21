package pl.sztop;


import lombok.RequiredArgsConstructor;
import pl.sztop.controller.AlbumController;
import pl.sztop.repository.Repository;
import pl.sztop.service.AlbumService;

@RequiredArgsConstructor
public class MusicLibrary {

    private final AlbumController albumController;

    public static void main(String[] args) {
        final Repository repository = new Repository();
        final AlbumService service = new AlbumService(repository);
        final AlbumController controller = new AlbumController(service);
        MusicLibrary musicLibrary = new MusicLibrary(controller);

        musicLibrary.albumController.start();
    }


}
