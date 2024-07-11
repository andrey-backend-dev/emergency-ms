package org.example.emergency.util.enums;

import lombok.Getter;

@Getter
public enum ServiceEnum {
    TELEGRAM("TELEGRAM"), EMAIL("EMAIL");

    private String value;

    ServiceEnum(String value) {
        this.value = value;
    }
}