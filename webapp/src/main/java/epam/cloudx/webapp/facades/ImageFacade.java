package epam.cloudx.webapp.facades;

import epam.cloudx.webapp.clientmodel.ImageClientModel;
import epam.cloudx.webapp.clientmodel.ImageUploadClientModel;
import epam.cloudx.webapp.dao.ImageJpaRepository;
import epam.cloudx.webapp.entity.ImageEntityModel;
import epam.cloudx.webapp.exception.S3ObjectNotFoundException;
import epam.cloudx.webapp.mapper.CloudXImageMapper;
import epam.cloudx.webapp.service.CloudXS3Service;
import epam.cloudx.webapp.service.CloudXFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Component
public class ImageFacade {

    private final ImageJpaRepository imageJpaRepository;
    private final CloudXImageMapper cloudXImageMapper;
    private final CloudXS3Service cloudXS3Service;
    private final CloudXFileService cloudXFileService;

//    @Value("${server.port}")
//    private String serverPort;

    public ImageFacade(ImageJpaRepository imageJpaRepository, CloudXImageMapper cloudXImageMapper,
                       CloudXS3Service cloudXS3Service, CloudXFileService cloudXFileService) {
        this.imageJpaRepository = imageJpaRepository;
        this.cloudXImageMapper = cloudXImageMapper;
        this.cloudXS3Service = cloudXS3Service;
        this.cloudXFileService = cloudXFileService;
    }

    @Transactional
    public ResponseEntity<?> findAll() {
        List<ImageEntityModel> entities = imageJpaRepository.findAll();

        List<ImageClientModel> clientModels = entities.stream()
                .map(entityModel -> cloudXImageMapper.toClientModel(entityModel, cloudXS3Service))
                .toList();

        return new ResponseEntity<>(clientModels, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> upload(ImageUploadClientModel downloadClientModel) {
        MultipartFile file = downloadClientModel.getFile();
        InputStream tempInput = cloudXFileService.getInputStream(file);
        cloudXS3Service.uploadObject(tempInput, file.getOriginalFilename(), downloadClientModel.getName());
        ImageEntityModel entityModel = cloudXImageMapper.toEntityModel(downloadClientModel);
        entityModel.setSize(file.getSize());
        entityModel.setFileExtension(cloudXFileService.getFileExtension(file.getOriginalFilename()));
        ImageEntityModel save = imageJpaRepository.save(entityModel);
        ImageClientModel imageClientModel = cloudXImageMapper.toClientModel(save, cloudXS3Service);
        return new ResponseEntity<>(imageClientModel, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<?> delete(String name) {
        List<ImageEntityModel> byName = imageJpaRepository.findByName(name);
        byName.stream()
                .map(ImageEntityModel::getName)
                .forEach(cloudXS3Service::deleteObject);
        byName.forEach(imageJpaRepository::delete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ResponseEntity<?> getRandom() {
        Optional<ImageEntityModel> entityModel = imageJpaRepository.findRandomEntity();
        ImageClientModel imageClientModel = cloudXImageMapper.toClientModel(entityModel.get(), cloudXS3Service);
        return new ResponseEntity<>(imageClientModel, HttpStatus.OK);
    }

    @Transactional
    public byte[] getImage(String name) {
        List<ImageEntityModel> byName = imageJpaRepository.findByName(name);
        if (byName == null || byName.size() == 0) {
            throw new S3ObjectNotFoundException();
        }
        return cloudXImageMapper.toClientModel(byName.get(0), cloudXS3Service).getBitmap();
    }
}
