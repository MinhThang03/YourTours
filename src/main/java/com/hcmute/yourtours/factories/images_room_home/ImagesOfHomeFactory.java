package com.hcmute.yourtours.factories.images_room_home;

import com.hcmute.yourtours.entities.ImagesRoomHome;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.images_room_home.ImageRoomHomeDetail;
import com.hcmute.yourtours.models.images_room_home.ImageRoomHomeInfo;
import com.hcmute.yourtours.repositories.ImagesRoomHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ImagesOfHomeFactory
        extends BasePersistDataFactory<UUID, ImageRoomHomeInfo, ImageRoomHomeDetail, Long, ImagesRoomHome>
        implements IImagesRoomHomeFactory {

    private final ImagesRoomHomeRepository imageRoomRepository;

    protected ImagesOfHomeFactory(ImagesRoomHomeRepository repository) {
        super(repository);
        this.imageRoomRepository = repository;
    }

    @Override
    @NonNull
    protected Class<ImageRoomHomeDetail> getDetailClass() {
        return ImageRoomHomeDetail.class;
    }

    @Override
    public ImagesRoomHome createConvertToEntity(ImageRoomHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return ImagesRoomHome.builder()
                .path(detail.getPath())
                .roomOfHomeId(detail.getRoomOfHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(ImagesRoomHome entity, ImageRoomHomeDetail detail) throws InvalidException {
        entity.setPath(detail.getPath());
        entity.setRoomOfHomeId(detail.getRoomOfHomeId());
    }

    @Override
    public ImageRoomHomeDetail convertToDetail(ImagesRoomHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return ImageRoomHomeDetail.builder()
                .id(entity.getImageId())
                .path(entity.getPath())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .build();
    }

    @Override
    public ImageRoomHomeInfo convertToInfo(ImagesRoomHome entity) throws InvalidException {
        if (entity == null) {
            return null;
        }
        return ImageRoomHomeInfo.builder()
                .id(entity.getImageId())
                .path(entity.getPath())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .build();
    }

    @Override
    protected Long convertId(UUID id) throws InvalidException {
        ImagesRoomHome image = imageRoomRepository.findByImageId(id).orElse(null);
        if (image == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_IMAGE_ROOM_HOME);
        }
        return image.getId();
    }
}
