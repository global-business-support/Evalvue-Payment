package com.subscription.enumMapping;

public enum SubscriptionStatus {
    CREATED(1),
    ACTIVE(2),
    COMPLETED(3),
    CANCELLED(4),
    PENDING(5),
    EXPIRED(6),
    AUTHENTICATED(7);

    public final int value;

    SubscriptionStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
