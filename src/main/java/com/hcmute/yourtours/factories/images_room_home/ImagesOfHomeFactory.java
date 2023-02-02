package com.hcmute.yourtours.factories.images_room_home;

import com.hcmute.yourtours.entities.ImagesRoomHome;
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
        extends BasePersistDataFactory<UUID, ImageRoomHomeInfo, ImageRoomHomeDetail, UUID, ImagesRoomHome>
        implements IImagesRoomHomeFactory {


    protected ImagesOfHomeFactory(ImagesRoomHomeRepository repository) {
        super(repository);
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
                .id(entity.getId())
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
                .id(entity.getId())
                .path(entity.getPath())
                .roomOfHomeId(entity.getRoomOfHomeId())
                .build();
    }

}
