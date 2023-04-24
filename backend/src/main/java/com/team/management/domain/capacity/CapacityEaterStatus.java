package com.team.management.domain.capacity;

import java.util.Arrays;

public enum CapacityEaterStatus {
    TODO("ToDo", "TODO"),
    IN_PROGRESS("In Progress", "IN_PROGRESS"),
    DONE("Done", "DONE");

    // почему final → ибо извне можно перезаписать значение, мол
    // CapacityEaterStatus.DONE.canonicalName = "newValue"
    final String canonicalName;
    final String secondName;

    CapacityEaterStatus(String canonicalName, String secondName) {
        this.canonicalName = canonicalName;
        this.secondName = secondName;
    }

    public static CapacityEaterStatus stringToEnum(String statusText) {
        return Arrays.stream(CapacityEaterStatus.values())
                .filter(en -> en.canonicalName.equals(statusText))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid enum"));
    }

    public static String EnumToString(CapacityEaterStatus status) {
        return Arrays.stream(CapacityEaterStatus.values())
                .filter(en -> en == status)
                .map(en -> en.canonicalName)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid enum"));
    }

}
