package com.hamrasta.trellis.message.metadata;

import com.hamrasta.trellis.core.message.MessageHandler;

public enum MagfaMessages implements MessageHandler {
    SMS_SERVICE_PROVIDER_IS_UNAVAILABLE(-1),
    RECEIVER_NUMBER_IS_INVALID(1),
    SENDER_NUMBER_IS_INVALID(2),
    ENCODING_IS_INVALID(3),
    M_CLASS_IS_INVALID(4),
    UDH_IS_INVALID(6),
    BODY_IS_EMPTY(13),
    BALANCE_NOT_ENOUGH(14),
    SERVER_IS_BUSY(15),
    ACCOUNT_IS_DISABLED(16),
    ACCOUNT_IS_EXPIRED(17),
    USERNAME_OR_PASSWORD_IS_INVALID(18),
    REQUEST_IS_INVALID(19),
    SENDER_NUMBER_NOT_RELATED_TO_ACCOUNT(20),
    SERVICE_IS_INACTIVE_FOR_THIS_ACCOUNT(22),
    SERVICE_NOT_PROCESSED_REQUEST(23),
    MESSAGE_ID_IS_INVALID(24),
    METHOD_IS_INVALID(25),
    RECEIVER_NUMBER_IS_BLOCKED_SMS(27),
    RECEIVER_NUMBER_BLOCKED_BY_SERVICE_PROVIDER(28),
    IP_IS_INVALID(29),
    MESSAGE_CHARACTER_LENGTH_GREATER_THAN_MAX_SIZE(30),
    MESSAGE_BODIES_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(101),
    MESSAGE_CLASS_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(102),
    SENDER_NUMBERS_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(103),
    UDH_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(104),
    PRIORITIES_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(105),
    RECEIVER_ARRAY_IS_EMPTY(106),
    RECEIVER_ARRAY_SIZE_GREATER_THAN_MAX_SIZE(107),
    SENDER_ARRAY_IS_EMPTY(108),
    ENCODING_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(109),
    CHECKING_MESSAGE_IDS_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY(110);

    private long value;

    public long getValue() {
        return value;
    }

    public static long getMaxValue() {
        return CHECKING_MESSAGE_IDS_ARRAY_NOT_MATCH_WITH_RECEIVER_ARRAY.getValue();
    }

    public static boolean isSuccess(long value) {
        return value > getMaxValue();
    }

    public static MagfaMessages valueOf(long v) {
        for (MagfaMessages value : values())
            if (v == value.getValue()) return value;
        return SMS_SERVICE_PROVIDER_IS_UNAVAILABLE;
    }

    MagfaMessages(Integer value) {
        this.value = value;
    }
}
