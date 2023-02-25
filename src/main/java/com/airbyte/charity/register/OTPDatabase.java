package com.airbyte.charity.register;

import java.util.Map;
import java.util.TreeMap;

public class OTPDatabase {
    protected static Map<String, String> OTP_MAP = new TreeMap<>();
    protected static Map<String, Boolean> VALID_PHONE_NUMBER = new TreeMap<>();
}
