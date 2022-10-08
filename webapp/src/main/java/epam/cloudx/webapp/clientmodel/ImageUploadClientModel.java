package epam.cloudx.webapp.clientmodel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public final class ImageUploadClientModel {

    @NotEmpty
    private String name;

    @NotNull
    private MultipartFile file;
}
