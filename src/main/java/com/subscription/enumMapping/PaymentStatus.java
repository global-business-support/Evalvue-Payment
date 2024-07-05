package com.subscription.enumMapping;

public enum PaymentStatus {
    PAID(1),
    FAILED(2),
    REFUNDED(3);

    private final int value;

    PaymentStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PaymentStatus fromCapturedStatus(boolean captured, String status) {
        if (captured && "captured".equalsIgnoreCase(status)) {
            return PAID;
        } else if (!captured && "failed".equalsIgnoreCase(status)) {
            return FAILED;
        } else {
            return REFUNDED;
        }
    }
}
