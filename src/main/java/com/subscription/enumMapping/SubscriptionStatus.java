package com.subscription.enumMapping;


public enum SubscriptionStatus {
    CREATED(1),
    ACTIVE(2),
    COMPLETED(3),
    CANCELLED(4),
    PENDING(5),
    EXPIRED(6);

    private final int value;

    SubscriptionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SubscriptionStatus fromValue(int value) {
        for (SubscriptionStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        return EXPIRED;
    }
    
    public static SubscriptionStatus fromString(String status) {
        for (SubscriptionStatus s : values()) {
            if (s.name().equalsIgnoreCase(status)) {
                return s;
            }
        }
        return EXPIRED;
    }
}

