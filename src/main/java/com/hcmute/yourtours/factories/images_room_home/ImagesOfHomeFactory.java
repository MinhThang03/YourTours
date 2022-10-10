package com.hcmute.yourtours.factories.images_room_home;

import com.hcmute.yourtours.commands.ImagesRoomHomeCommand;
import com.hcmute.yourtours.exceptions.YourToursErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.factory.BasePersistDataFactory;
import com.hcmute.yourtours.models.images_room_home.ImageRoomHomeDetail;
import com.hcmute.yourtours.models.images_room_home.ImageRoomHomeInfo;
import com.hcmute.yourtours.repositories.ImagesRoomHomeRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImagesOfHomeFactory
        extends BasePersistDataFactory<UUID, ImageRoomHomeInfo, ImageRoomHomeDetail, Long, ImagesRoomHomeCommand>
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
    public ImagesRoomHomeCommand createConvertToEntity(ImageRoomHomeDetail detail) throws InvalidException {
        if (detail == null) {
            return null;
        }
        return ImagesRoomHomeCommand.builder()
                .path(detail.getPath())
                .roomOfHomeId(detail.getRoomOfHomeId())
                .build();
    }

    @Override
    public void updateConvertToEntity(ImagesRoomHomeCommand entity, ImageRoomHomeDetail detail) throws InvalidException {
        entity.setPath(detail.getPath());
        entity.setRoomOfHomeId(detail.getRoomOfHomeId());
    }

    @Override
    public ImageRoomHomeDetail convertToDetail(ImagesRoomHomeCommand entity) throws InvalidException {
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
    public ImageRoomHomeInfo convertToInfo(ImagesRoomHomeCommand entity) throws InvalidException {
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
        ImagesRoomHomeCommand image = imageRoomRepository.findByImageId(id).orElse(null);
        if (image == null) {
            throw new InvalidException(YourToursErrorCode.NOT_FOUND_IMAGE_ROOM_HOME);
        }
        return image.getId();
    }
}
