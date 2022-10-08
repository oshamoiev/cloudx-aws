package epam.cloudx.webapp.web;

import epam.cloudx.webapp.facades.ImageFacade;
import epam.cloudx.webapp.clientmodel.ImageUploadClientModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("images")
public class ImageController {

    private final ImageFacade imageFacade;

    public ImageController(ImageFacade imageFacade) {
        this.imageFacade = imageFacade;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return imageFacade.findAll();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@ModelAttribute ImageUploadClientModel downloadClientModel) {
        return imageFacade.upload(downloadClientModel);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        return imageFacade.delete(name);
    }

    @GetMapping("/get-random")
    public ResponseEntity<?> getRandomImage() {
        return imageFacade.getRandom();
    }

    @GetMapping(value = "/download/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] download(@PathVariable String name) {
        return imageFacade.getImage(name);
    }

}
