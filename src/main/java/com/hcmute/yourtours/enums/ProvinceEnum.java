package com.hcmute.yourtours.enums;

public enum ProvinceEnum {
    THANH_PHO_HA_NOI("Thanh pho Ha Noi", 1),
    TINH_HA_GIANG("Tinh Ha Giang", 2),
    TINH_CAO_BANG("Tinh Cao Bang", 4),
    TINH_BAC_KAN("Tinh Bac Kan", 6),
    TINH_TUYEN_QUANG("Tinh Tuyen Quang", 8),
    TINH_LAO_CAI("Tinh Lao Cai", 10),
    TINH_DIEN_BIEN("Tinh Đien Bien", 11),
    TINH_LAI_CHAU("Tinh Lai Chau", 12),
    TINH_SON_LA("Tinh Sơn La", 14),
    TINH_YEN_BAI("Tinh Yên Bai", 15),
    TINH_HOA_BINH("Tinh Hoà Binh", 17),
    TINH_THAI_NGUYEN("Tinh Thai Nguyen", 19),
    TINH_LANG_SON("Tinh Lang Son", 20),
    TINH_QUANG_NINH("Tinh Quang Ninh", 22),
    TINH_BAC_GIANG("Tinh Bac Giang", 24),
    TINH_PHU_THO("Tinh Phu Tho", 25),
    TINH_VINH_PHUC("Tinh Vinh Phuc", 26),
    TINH_BAC_NINH("Tinh Bac Ninh", 27),
    TINH_HAI_DUONG("Tinh Hai Duong", 30),
    THANH_PHO_HAI_PHONG("Thanh pho Hai Phong", 31),
    TINH_HUNG_YEN("Tinh Hưng Yen", 33),
    TINH_THAI_BINH("Tinh Thai Binh", 34),
    TINH_HA_NAM("Tinh Ha Nam", 35),
    TINH_NAM_DINH("Tinh Nam Dinh", 36),
    TINH_NINH_BINH("Tinh Ninh Binh", 37),
    TINH_THANH_HOA("Tinh Thanh Hoa", 38),
    TINH_NGHE_AN("Tinh Nghe An", 40),
    TINH_HA_TINH("Tinh Ha Tinh", 42),
    TINH_QUANG_BINH("Tinh Quang Binh", 44),
    TINH_QUANG_TRI("Tinh Quang Tri", 45),
    TINH_THUA_THIEN_HUE("Tinh Thua Thien Hue", 46),
    THANH_PHO_DA_NANG("Thanh pho Đa Nang", 48),
    TINH_QUANG_NAM("Tinh Quang Nam", 49),
    TINH_QUANG_NGAI("Tinh Quang Ngai", 51),
    TINH_BINH_DINH("Tinh Binh Đinh", 52),
    TINH_PHU_YEN("Tinh Phu Yen", 54),
    TINH_KHANH_HOA("Tinh Khanh Hoa", 56),
    TINH_NINH_THUAN("Tinh Ninh Thuan", 58),
    TINH_BINH_THUAN("Tinh Bình Thuan", 60),
    TINH_KON_TUM("Tinh Kon Tum", 62),
    TINH_GIA_LAI("Tinh Gia Lai", 64),
    TINH_DAK_LAK("Tinh Đak Lak", 66),
    TINH_DAK_NONG("Tinh Đak Nong", 67),
    TINH_LAM_DONG("Tinh Lam Đong", 68),
    TINH_BINH_PHUOC("Tinh Binh Phưoc", 70),
    TINH_TAY_NINH("Tinh Tây Ninh", 72),
    TINH_BINH_DUONG("Tinh Bình Dương", 74),
    TINH_DONG_NAI("Tinh Đong Nai", 75),
    TINH_BA_RIA_VUNG_TAU("Tinh Bà Ria - Vung Tau", 77),
    THANH_PHO_HO_CHI_MINH("Thanh phố Ho Chi Minh", 79),
    TINH_LONG_AN("Tinh Long An", 80),
    TINH_TIEN_GIANG("Tinh Tien Giang", 82),
    TINH_BEN_TRE("Tinh Ben Tre", 83),
    TINH_TRA_VINH("Tinh Tra Vinh", 84),
    TINH_VINH_LONG("Tinh Vinh Long", 86),
    TINH_DONG_THAP("Tinh Đong Thap", 87),
    TINH_AN_GIANG("Tinh An Giang", 89),
    TINH_KIEN_GIANG("Tinh Kien Giang", 91),
    THANH_PHO_CAN_THO("Thanh pho Can Tho", 92),
    TINH_HAU_GIANG("Tinh Hau Giang", 93),
    TINH_SOC_TRANG("Tinh Soc Trang", 94),
    TINH_BAC_LIEU("Tinh Bac Lieu", 95),
    TINH_CA_MAU("Tinh Ca Mau", 96),
    ;

    private final String provinceName;
    private final Integer provinceCode;

    ProvinceEnum(String provinceName, Integer provinceCode) {
        this.provinceName = provinceName;
        this.provinceCode = provinceCode;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }
}
