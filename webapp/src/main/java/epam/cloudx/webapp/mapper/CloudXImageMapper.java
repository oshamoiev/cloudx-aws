package epam.cloudx.webapp.mapper;

import epam.cloudx.webapp.clientmodel.ImageClientModel;
import epam.cloudx.webapp.clientmodel.ImageUploadClientModel;
import epam.cloudx.webapp.entity.ImageEntityModel;
import epam.cloudx.webapp.service.CloudXS3Service;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CloudXImageMapper {

    @Mapping(target = "bitmap", ignore = true)
    ImageClientModel toClientModel(ImageEntityModel entityModel, @Context CloudXS3Service cloudXS3Service);

    ImageEntityModel toEntityModel(ImageUploadClientModel clientModel);

    ImageEntityModel toEntityModel(ImageClientModel clientModel);

    @AfterMapping
    default void setBitmapToClient(@MappingTarget ImageClientModel target, ImageEntityModel source, @Context CloudXS3Service cloudXS3Service) {
        target.setBitmap(cloudXS3Service.downloadObject(source.getName()));
    }
}

