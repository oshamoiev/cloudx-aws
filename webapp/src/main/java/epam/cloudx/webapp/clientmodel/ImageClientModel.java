package epam.cloudx.webapp.clientmodel;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageClientModel {
    private long id;
    private String name;
    private long size;
    private String fileExtension;
    private LocalDateTime updatedAt;
    private byte[] bitmap;
    private ObjectMetadata objectMetadata;
}
