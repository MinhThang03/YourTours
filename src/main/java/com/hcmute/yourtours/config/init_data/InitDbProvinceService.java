package com.hcmute.yourtours.config.init_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.yourtours.config.init_data.models.InitDbProvinceModel;
import com.hcmute.yourtours.entities.Province;
import com.hcmute.yourtours.repositories.ProvinceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Configuration
public class InitDbProvinceService {

    private final ProvinceRepository provinceRepository;

    public InitDbProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Bean
    public void implementInitDataMenuAction() {
        List<Province> checkList = provinceRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initDbProvince.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbProvinceModel initModel = mapper.readValue(file, InitDbProvinceModel.class);

                provinceRepository.saveAll(initModel.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
