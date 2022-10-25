package com.hcmute.yourtours.exceptions;

import com.hcmute.yourtours.libs.exceptions.IErrorCode;
import org.springframework.http.HttpStatus;

public enum YourToursErrorCode implements IErrorCode {

    //500
    AUTO_GENERATE_OTP(HttpStatus.INTERNAL_SERVER_ERROR, "50000001", "Lỗi tạo mã otp"),

    //400
    USERNAME_EXIST(HttpStatus.BAD_REQUEST, "40000002", "Tên đăng nhập đã tồn tại"),
    CREATE_USER_FAIL(HttpStatus.BAD_REQUEST, "40000003", "Tạo user thất bại"),
    EMAIL_INVALID(HttpStatus.BAD_REQUEST, "400000004", "Email không hợp lệ"),
    PHONE_NUMBER_INVALID(HttpStatus.BAD_REQUEST, "400000005", "Số điện thoại không hợp lệ"),
    REFRESH_TOKEN_FAIL(HttpStatus.BAD_REQUEST, "400000006", "Refresh token không thành công"),
    LOGOUT_FAIL(HttpStatus.BAD_REQUEST, "400000007", "Đăng xuất ứng dụng thất bại"),
    LOGIN_FAIL(HttpStatus.BAD_REQUEST, "400000008", "Đăng nhập ứng dụng thất bại"),
    INVALID_CHANGE_PASSWORD(HttpStatus.BAD_REQUEST, "400000009", "Mật khẩu không khớp"),
    CHANGE_PASSWORD_FAIL(HttpStatus.BAD_REQUEST, "400000010", "Thay đổi mật khẩu thất bại"),
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, "400000011", "Email đã tồn tại"),
    REFRESH_TOKEN_NOT_VALID(HttpStatus.BAD_REQUEST, "400000012", "Refresh token không hợp lệ"),
    UPLOAD_FILE_ERROR(HttpStatus.BAD_REQUEST, "400000013", "Upload file không thành công"),
    FILE_ERROR_IS_NULL(HttpStatus.BAD_REQUEST, "400000014", "File không được để trống"),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "400000015", "Token không hợp lệ"),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "400000016", "Token đã hết hạn"),

    //403
    FORBIDDEN(HttpStatus.FORBIDDEN, "4030000", "Bạn không đủ quyền truy cập"),

    //404
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "4040001", "Không tìm thấy người dùng, xin vui lòng thử lại"),
    NOT_FOUND_ROOM_CATEGORIES(HttpStatus.NOT_FOUND, "4040002", "Không tìm thấy loại phòng"),
    NOT_FOUND_BED_CATEGORIES(HttpStatus.NOT_FOUND, "4040003", "Không tìm thấy loại giường"),
    NOT_FOUND_GUEST_CATEGORIES(HttpStatus.NOT_FOUND, "4040004", "Không tìm thấy loại khách"),
    NOT_FOUND_SECURITY_CATEGORIES(HttpStatus.NOT_FOUND, "4040005", "Không tìm thấy loại bảo mật"),
    NOT_FOUND_RULE_CATEGORIES(HttpStatus.NOT_FOUND, "4040006", "Không tìm thấy loại nội quy của nhà"),
    NOT_FOUND_DISCOUNT_CATEGORIES(HttpStatus.NOT_FOUND, "4040007", "Không tìm thấy loại giảm giá"),
    NOT_FOUND_SURCHARGE_CATEGORIES(HttpStatus.NOT_FOUND, "4040008", "Không tìm thấy loại phụ phí"),
    NOT_FOUND_AMENITY_CATEGORIES(HttpStatus.NOT_FOUND, "4040009", "Không tìm thấy loại tiện ích"),
    NOT_FOUND_AMENITY(HttpStatus.NOT_FOUND, "4040010", "Không tìm thấy tiện ích"),
    NOT_FOUND_AMENITY_CATEGORIES_AMENITY_FACTORY(HttpStatus.NOT_FOUND, "4040011", "Không tìm thấy loại tiện ích"),
    NOT_FOUND_HOME(HttpStatus.NOT_FOUND, "4040012", "Không tìm thấy nhà"),
    NOT_FOUND_IMAGES_OF_HOME(HttpStatus.NOT_FOUND, "4040013", "Không tìm thấy hình ảnh của ngôi nhà"),
    NOT_FOUND_ROOMS_OF_HOME(HttpStatus.NOT_FOUND, "4040014", "Không tìm thấy căn phòng của ngôi nhà"),
    NOT_FOUND_IMAGE_ROOM_HOME(HttpStatus.NOT_FOUND, "4040015", "Không tìm thấy hình ảnh căn phòng của ngôi nhà"),
    NOT_FOUND_BEDS_OF_HOME(HttpStatus.NOT_FOUND, "4040016", "Không tìm thấy cấu hình giường ngủ của ngôi nhà"),
    NOT_FOUND_GUESTS_OF_HOME(HttpStatus.NOT_FOUND, "4040017", "Không tìm thấy cấu hình khách của ngôi nhà"),
    NOT_FOUND_RULES_OF_HOME(HttpStatus.NOT_FOUND, "4040017", "Không tìm thấy nội quy của ngôi nhà"),
    NOT_FOUND_AMENITIES_OF_HOME(HttpStatus.NOT_FOUND, "4040018", "Không tìm thấy tiện ích của ngôi nhà"),
    NOT_FOUND_DISCOUNTS_OF_HOME(HttpStatus.NOT_FOUND, "4040019", "Không tìm thấy giảm giá của ngôi nhà"),
    NOT_FOUND_SURCHARGES_OF_HOME(HttpStatus.NOT_FOUND, "4040019", "Không tìm thấy phụ phí của ngôi nhà"),
    NOT_FOUND_OWNER_OF_HOME(HttpStatus.NOT_FOUND, "4040020", "Không tìm thấy chủ quản lý của ngôi nhà"),
    NOT_FOUND_PRICE_OF_HOME(HttpStatus.NOT_FOUND, "4040021", "Không tìm thấy giá điều chỉnh của ngôi nhà"),
    NOT_FOUND_DISCOUNT_CAMPAIGN(HttpStatus.NOT_FOUND, "4040022", "Không tìm thấy chiến dịch quảng cáo"),
    NOT_FOUND_VERIFICATION_TOKEN(HttpStatus.NOT_FOUND, "4040023", "Không tìm thấy token xác thực"),
    NOT_FOUND_SECURITY_OF_HOME(HttpStatus.NOT_FOUND, "40400024", "Không tìm thấy bảo mật của ngôi nhà"),

    //409
    CONFIRM_PASSWORD_IS_NOT_VALID(HttpStatus.CONFLICT, "40900001", "Xác nhận mật khẩu không trùng khớp"),

    ;
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    YourToursErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
