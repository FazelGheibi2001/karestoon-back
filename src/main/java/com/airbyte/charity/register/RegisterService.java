package com.airbyte.charity.register;

import com.airbyte.charity.dto.RegisterDTO;
import com.airbyte.charity.dto.UserDTO;
import com.airbyte.charity.exception.DataMissException;
import com.airbyte.charity.exception.InvalidInputException;
import com.airbyte.charity.model.UserInformation;
import com.airbyte.charity.permission.Role;
import com.airbyte.charity.sms.SMSSender;
import com.airbyte.charity.user.UserInformationRepository;
import com.airbyte.charity.user.UserInformationService;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.airbyte.charity.register.OTPDatabase.VALID_PHONE_NUMBER;
import static com.airbyte.charity.register.OTPDatabase.OTP_MAP;

@Service
public class RegisterService {
    private final UserInformationService userInformationService;
    private final UserInformationRepository userInformationRepository;
    private final SMSSender sendSMS;

    public RegisterService(UserInformationService userInformationService, UserInformationRepository userInformationRepository, SMSSender sendSMS) {
        this.userInformationService = userInformationService;
        this.userInformationRepository = userInformationRepository;
        this.sendSMS = sendSMS;
    }

    public RegisterDTO verifyPhoneNumber(RegisterDTO dto) {
        try {
            if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isEmpty()) {
                userInformationService.getByUsername(dto.getPhoneNumber());
                dto.setStatus("exist");
                return dto;
            }
            throw new InvalidInputException("phoneNumber must not be null");
        } catch (IllegalArgumentException exception) {
            String otp = generateOTP(6);
            OTP_MAP.put(dto.getPhoneNumber(), otp);
//            sendSMS(otp, dto.getPhoneNumber());
            dto.setStatus("newUser");
            return dto;
        }
    }

    private void sendSMS(String otp, String phoneNumber) {
        String sms = String.format("کد تاییدیه شما در سایت کارستون : %s", otp);
        sendSMS.sendSMS(phoneNumber, sms);
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
            throw new DataMissException("The password has been gone. try again.");
        }
        String otp = OTP_MAP.get(dto.getPhoneNumber());
        if (dto.getOtp().equals((String) otp)) {
            OTP_MAP.remove(dto.getPhoneNumber());
            VALID_PHONE_NUMBER.put(dto.getPhoneNumber(), true);
            return true;
        }
        VALID_PHONE_NUMBER.put(dto.getPhoneNumber(), false);
        return false;
    }

    public RegisterDTO create(RegisterDTO dto) {
        if (VALID_PHONE_NUMBER == null || VALID_PHONE_NUMBER.isEmpty()) {
            throw new DataMissException("Can not do anything");
        }
        if (VALID_PHONE_NUMBER.get(dto.getPhoneNumber())) {
            VALID_PHONE_NUMBER.remove(dto.getPhoneNumber());
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
        dto = null;
        dto = new RegisterDTO();
        dto.setStatus("userCanNotCreated");
        return dto;
    }

    public RegisterDTO forgotPassword(RegisterDTO dto) {
        try {
            userInformationService.getByUsername(dto.getPhoneNumber());
            String otp = generateOTP(6);
            OTP_MAP.put(dto.getPhoneNumber(), otp);
//            sendSMS(otp, dto.getPhoneNumber());
            dto.setStatus("OTPSent");
        } catch (IllegalArgumentException exception) {
            dto.setStatus("newUser");
        }
        return dto;
    }

    public RegisterDTO checkOTPForgotPassword(RegisterDTO dto) {
        if (OTP_MAP == null || OTP_MAP.isEmpty()) {
            throw new DataMissException("The password has been gone. try again.");
        }
        String otp = OTP_MAP.get(dto.getPhoneNumber());
        if (dto.getOtp().equals((String) otp)) {
            OTP_MAP.remove(dto.getPhoneNumber());
            VALID_PHONE_NUMBER.put(dto.getPhoneNumber(), true);
            dto.setStatus("OTPAccepted");
            return dto;
        }
        dto.setStatus("OTPNotAccepted");
        return dto;
    }

    public RegisterDTO updatePassword(RegisterDTO dto) {
        if (VALID_PHONE_NUMBER == null || VALID_PHONE_NUMBER.isEmpty()) {
            throw new DataMissException("Can not do anything");
        }
        if (VALID_PHONE_NUMBER.get(dto.getPhoneNumber())) {
            VALID_PHONE_NUMBER.remove(dto.getPhoneNumber());
            UserInformation user = userInformationService.getByUsername(dto.getPhoneNumber());
            user.setPassword(dto.getPassword());
            userInformationRepository.save(user);
            dto = null;
            dto = new RegisterDTO();
            dto.setPhoneNumber(user.getUsername());
            dto.setStatus("passwordUpdated");
            return dto;
        }
        dto.setStatus("passwordCanNotUpdated");
        return dto;
    }
}
