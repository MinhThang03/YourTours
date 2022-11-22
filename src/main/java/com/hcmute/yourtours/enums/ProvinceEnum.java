package com.hcmute.yourtours.enums;

public enum ProvinceEnum {
    THANH_PHO_HA_NOI("Thành phố Hà Nội", 1),
    TINH_HA_GIANG("Tỉnh Hà Giang", 2),
    TINH_CAO_BANG("Tỉnh Cao Bằng", 4),
    TINH_BAC_KAN("Tỉnh Bắc Kạn", 6),
    TINH_TUYEN_QUANG("Tỉnh Tuyên Quang", 8),
    TINH_LAO_CAI("Tỉnh Lào Cai", 10),
    TINH_DIEN_BIEN("Tỉnh Điện Biên", 11),
    TINH_LAI_CHAU("Tỉnh Lai Châu", 12),
    TINH_SON_LA("Tỉnh Sơn La", 14),
    TINH_YEN_BAI("Tỉnh Yên Bái", 15),
    TINH_HOA_BINH("Tỉnh Hoà Bình", 17),
    TINH_THAI_NGUYEN("Tỉnh Thái Nguyên", 19),
    TINH_LANG_SON("Tỉnh Lạng Sơn", 20),
    TINH_QUANG_NINH("Tỉnh Quảng Ninh", 22),
    TINH_BAC_GIANG("Tỉnh Bắc Giang", 24),
    TINH_PHU_THO("Tỉnh Phú Thọ", 25),
    TINH_VINH_PHUC("Tỉnh Vĩnh Phúc", 26),
    TINH_BAC_NINH("Tỉnh Bắc Ninh", 27),
    TINH_HAI_DUONG("Tỉnh Hải Dương", 30),
    THANH_PHO_HAI_PHONG("Thành phố Hải Phòng", 31),
    TINH_HUNG_YEN("Tỉnh Hưng Yên", 33),
    TINH_THAI_BINH("Tỉnh Thái Bình", 34),
    TINH_HA_NAM("Tỉnh Hà Nam", 35),
    TINH_NAM_DINH("Tỉnh Nam Định", 36),
    TINH_NINH_BINH("Tỉnh Ninh Bình", 37),
    TINH_THANH_HOA("Tỉnh Thanh Hóa", 38),
    TINH_NGHE_AN("Tỉnh Nghệ An", 40),
    TINH_HA_TINH("Tỉnh Hà Tĩnh", 42),
    TINH_QUANG_BINH("Tỉnh Quảng Bình", 44),
    TINH_QUANG_TRI("Tỉnh Quảng Trị", 45),
    TINH_THUA_THIEN_HUE("Tỉnh Thừa Thiên Huế", 46),
    THANH_PHO_DA_NANG("Thành phố Đà Nẵng", 48),
    TINH_QUANG_NAM("Tỉnh Quảng Nam", 49),
    TINH_QUANG_NGAI("Tỉnh Quảng Ngãi", 51),
    TINH_BINH_DINH("Tỉnh Bình Định", 52),
    TINH_PHU_YEN("Tỉnh Phú Yên", 54),
    TINH_KHANH_HOA("Tỉnh Khánh Hòa", 56),
    TINH_NINH_THUAN("Tỉnh Ninh Thuận", 58),
    TINH_BINH_THUAN("Tỉnh Bình Thuận", 60),
    TINH_KON_TUM("Tỉnh Kon Tum", 62),
    TINH_GIA_LAI("Tỉnh Gia Lai", 64),
    TINH_DAK_LAK("Tỉnh Đắk Lắk", 66),
    TINH_DAK_NONG("Tỉnh Đắk Nông", 67),
    TINH_LAM_DONG("Tỉnh Lâm Đồng", 68),
    TINH_BINH_PHUOC("Tỉnh Bình Phước", 70),
    TINH_TAY_NINH("Tỉnh Tây Ninh", 72),
    TINH_BINH_DUONG("Tỉnh Bình Dương", 74),
    TINH_DONG_NAI("Tỉnh Đồng Nai", 75),
    TINH_BA_RIA_VUNG_TAU("Tỉnh Bà Rịa - Vũng Tàu", 77),
    THANH_PHO_HO_CHI_MINH("Thành phố Hồ Chí Minh", 79),
    TINH_LONG_AN("Tỉnh Long An", 80),
    TINH_TIEN_GIANG("Tỉnh Tiền Giang", 82),
    TINH_BEN_TRE("Tỉnh Bến Tre", 83),
    TINH_TRA_VINH("Tỉnh Trà Vinh", 84),
    TINH_VINH_LONG("Tỉnh Vĩnh Long", 86),
    TINH_DONG_THAP("Tỉnh Đồng Tháp", 87),
    TINH_AN_GIANG("Tỉnh An Giang", 89),
    TINH_KIEN_GIANG("Tỉnh Kiên Giang", 91),
    THANH_PHO_CAN_THO("Thành phố Cần Thơ", 92),
    TINH_HAU_GIANG("Tỉnh Hậu Giang", 93),
    TINH_SOC_TRANG("Tỉnh Sóc Trăng", 94),
    TINH_BAC_LIEU("Tỉnh Bạc Liêu", 95),
    TINH_CA_MAU("Tỉnh Cà Mau", 96),
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
