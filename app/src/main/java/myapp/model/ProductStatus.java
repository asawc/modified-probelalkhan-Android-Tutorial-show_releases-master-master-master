package myapp.model;

import com.google.gson.annotations.SerializedName;

public enum ProductStatus {

    @SerializedName("0")
    OCZEKUĄCY (0),

    @SerializedName("1")
    BRAK_W_MAGAZYNIE (1),

    @SerializedName("2")
    WYDANY (2);

    private final int value;

    public int getValue() {
        return value;
    }

    private ProductStatus(int value) {
        this.value = value;
    }
}
