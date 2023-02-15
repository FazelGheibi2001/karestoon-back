package com.airbyte.charity;

import com.airbyte.charity.jwt.LoginDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CommonTestData {
    public static final String DEFAULT_STRING = "AAAAAAAAAAAAAAA";
    public static final String UPDATED_STRING = "BBBBBBBBBBBBBBB";

    public static final Boolean DEFAULT_BOOLEAN = true;
    public static final Boolean UPDATED_BOOLEAN = false;

    public static final Integer DEFAULT_INTEGER = 1;
    public static final Integer UPDATED_INTEGER = 2;

    public static final String DEFAULT_GENDER = "male";
    public static final String UPDATED_GENDER = "female";

    public static final String DEFAULT_MARITAL_STATUS = "divorced";

    public static final String DEFAULT_INSTANT_AS_STRING = "2055-02-15T18:35:24.00Z";
    public static final String PERSON_TYPE = "Person";

    public static final BigDecimal DEFAULT_BIG_DECIMAL = BigDecimal.ONE;
    public static final BigDecimal UPDATED_BIG_DECIMAL = BigDecimal.TEN;

    public static final Instant DEFAULT_INSTANT = Instant.now();
    public static final Instant UPDATED_INSTANT = DEFAULT_INSTANT.plus(2, ChronoUnit.DAYS);

    public static final String DEFAULT_NATIONAL_CODE = "1952083794";
    public static final String UPDATED_NATIONAL_CODE = "4061794902";

    public static final String DEFAULT_PHONE_NUMBER = "09384719663";
    public static final String UPDATED_PHONE_NUMBER = "0917080635";

    public static final String DEFAULT_HOME_NUMBER = "02838840303";

    public static final String DEFAULT_NUMBER_STRING = "0123456789";

    public static final String DEFAULT_EMAIL = "divinehemlock@gmail.com";

    public static final Long DEFAULT_LONG = 1L;
    public static final Long UPDATED_LONG = 10L;

    public static final String DEFAULT_CATEGORY_NAME = "بیمه";
    public static final String UPDATED_CATEGORY_NAME = "تعمیرات";

    public static final String DEFAULT_DATE = "2022/10/14 14:30:00";
    public static final String UPDATED_DATE = "2024/10/14 15:30:00";

    public static final String DEFAULT_VALUE_STRING = "12.5";
    public static final String UPDATED_VALUE_STRING = "15.5";

    public static final String DEFAULT_UNIT = "IRR";
    public static final String UPDATED_UNIT = "USD";

    public static final String DEFAULT_ID = UUID.randomUUID().toString();
    public static final String UPDATED_ID = UUID.randomUUID().toString();

    public static final String DEFAULT_PARENT_TYPE = "Person";
    public static final String UPDATED_PARENT_TYPE = "Personnel";

    public static final String DEFAULT_NAME = "Fazel";
    public static final String UPDATED_NAME = "Mohsen";

    public static final String DEFAULT_MOBILE_NUMBER = "09170080635";
    public static final String UPDATED_MOBILE_NUMBER = "09924664362";

    public static final String DEFAULT_TELEPHONE_NUMBER = "52336889";
    public static final String UPDATED_TELEPHONE_NUMBER = "52342335";

    public static final Double DEFAULT_DOUBLE = 12.0;
    public static final Double UPDATED_DOUBLE = 15.0;

    public static final String DEFAULT_USERNAME = "AIRBYTE_USER";
    public static final String DEFAULT_PASSWORD = "AIRBYTE_PASSWORD";

    public static LoginDTO loginDTO() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPassword(DEFAULT_PASSWORD);
        loginDTO.setUsername(DEFAULT_USERNAME);
        return loginDTO;
    }
}
