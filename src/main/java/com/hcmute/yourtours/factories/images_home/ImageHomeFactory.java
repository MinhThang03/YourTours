package com.hcmute.yourtours.factories.images_home;

import com.hcmute.yourtours.entities.ImagesHome;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.images_home.ImageHomeDetail;
import com.hcmute.yourtours.models.images_home.ImageHomeInfo;
import com.hcmute.yourtours.repositories.ImagesHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ImageHomeFactory
        extends BasePersistDataFactory<UUID, ImageHomeInfo, ImageHomeDetail, UUID, ImagesHome>
        implements IImagesHomeFactory {

    private final ImagesHomeRepository imagesHomeRepository;

    protected ImageHomeFactory(ImagesHomeRepository repository) {
        super(repository);
        this.imagesHomeRepository = repository;
    }

    @Override
    @NonNull
    protected Class<ImageHomeDetail> getDetailClass() {
        return ImageHomeDetail.class;
    }

    @Override
    public ImagesHome createConvertToEntity(ImageHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return ImagesHome.builder()
                .path(detail.getPath())
                .homeId(detail.getHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(ImagesHome entity, ImageHomeDetail detail) throws InvalidException {
        entity.setPath(detail.getPath());
        entity.setHomeId(detail.getHomeId());
    }

    @Override
    public ImageHomeDetail convertToDetail(ImagesHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return ImageHomeDetail.builder()
                .id(entity.getHomeId())
                .path(entity.getPath())
                .homeId(entity.getHomeId())
                .build();
    }

    @Override
    public ImageHomeInfo convertToInfo(ImagesHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return ImageHomeInfo.builder()
                .id(entity.getHomeId())
                .path(entity.getPath())
                .homeId(entity.getHomeId())
                .build();
    }


    @Override
    public void createListWithHomeId(UUID homeId, List<ImageHomeDetail> listCreate) throws InvalidException {
        imagesHomeRepository.deleteAllByHomeId(homeId);
        for (ImageHomeDetail item : listCreate) {
            item.setHomeId(homeId);
            createModel(item);
        }
    }

    @Override
    public List<ImageHomeDetail> getListByHomeId(UUID homeId, String pathThumbnail) throws InvalidException {
        List<ImagesHome> commands = imagesHomeRepository.findAllByHomeId(homeId);
        List<ImageHomeDetail> result = new ArrayList<>();
        for (ImagesHome command : commands) {
            if (!command.getPath().equals(pathThumbnail)) {
                result.add(convertToDetail(command));
            }
        }
        return result;
    }

    @Override
    public List<ImageHomeDetail> getFullListByHomeId(UUID homeId, String pathThumbnail) throws InvalidException {
        List<ImagesHome> commands = imagesHomeRepository.findAllByHomeId(homeId);
        List<ImageHomeDetail> result = new ArrayList<>();
        for (ImagesHome command : commands) {
            result.add(convertToDetail(command));
        }
        return result;
    }
}
