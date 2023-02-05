package com.airbyte.charity.register;

import com.airbyte.charity.dto.RegisterDTO;
import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.permission.Role;
import com.airbyte.charity.user.UserInformationService;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.airbyte.charity.register.OTPDatabase.OTP_MAP;

@Service
public class RegisterService {
    private final UserInformationService userInformationService;

    public RegisterService(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    public RegisterDTO find(RegisterDTO dto) {
        try {
            if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
                userInformationService.getByUsername(dto.getPhoneNumber());
                dto.setStatus("exist");
                return dto;
            }
            throw new RuntimeException("phoneNumber must not be null");
        } catch (IllegalArgumentException exception) {
            String otp = generateOTP(6);
            OTP_MAP.put(dto.getPhoneNumber(), otp);
            sendSMS(otp, dto.getPhoneNumber());
            dto.setStatus("newUser");
            return dto;
        }
    }

    private void sendSMS(String otp, String phoneNumber) {
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "message=test&receptor=09111111111&linenumber=5000222&senddate=1508144471&checkid=1");
//        Request request = new Request.Builder()
//                .url("https://api.ghasedak.me/v2/sms/send/simple")
//                .post(body)
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .addHeader("apikey", "your apikey")
//                .addHeader("cache-control", "no-cache")
//                .build();
//
//        Response response = client.newCall(request).execute();
    }

    private String generateOTP(int length) {
        String otp = "";
        for (int index = 0; index < length; index++) {
            Random random = new Random();
            int randomInt = random.nextInt(10);
            otp += (char) ('0' + randomInt);
        }
        return otp;
    }

    public Boolean checkOTP(RegisterDTO dto) {
        if (OTP_MAP == null || OTP_MAP.isEmpty()) {
            throw new RuntimeException("The password has been gone. try again.");
        }
        String otp = OTP_MAP.get(dto.getPhoneNumber());
        if (dto.getOtp().equals((String) otp)) {
            OTP_MAP.remove(dto.getPhoneNumber());
            return true;
        }

        return false;
    }

    public RegisterDTO create(RegisterDTO dto) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(dto.getPhoneNumber());
        userDTO.setPassword(dto.getPassword());
        userDTO.setFirstName(dto.getFirstName());
        userDTO.setLastName(dto.getLastName());
        userDTO.setRole(Role.USER.name());
        userInformationService.save(userDTO);
        dto = null;
        dto = new RegisterDTO();
        dto.setStatus("create");
        dto.setRole(Role.USER.name());
        return dto;
    }
}
