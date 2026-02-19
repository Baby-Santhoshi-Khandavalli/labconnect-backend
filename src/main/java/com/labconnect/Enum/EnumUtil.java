package com.labconnect.Enum;

public class EnumUtil {

    public static <T extends Enum<T>> T fromStringIgnoreCase(Class<T> enumClass, String value) {
        if (value == null || enumClass == null) {
            return null;
        }
        for (T enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equalsIgnoreCase(value)) {
                return enumConstant;
            }
        }
        // You can either return null or throw a custom error here
        return null;
    }
}