package com.rks.paymentservice.constants;

public class Constants {
    public static final String AUTHORIZATION = "authorization";
    /**
     * Response related constants
     */
    public static final String RESPONSE_CODE = "response_code";
    /**
     * Order related constants
     */
    public static final String ORDER_DATE = "order_date";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String ITEMS_IN_ORDER = "items";
    public static final String ORDER_AMOUNT = "order_amount";

    /**
     * Item related constants
     */
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_NAME = "name";
    public static final String ITEM_QUANTITY = "quantity";
    public static final String ITEM_PRICE = "price";

    public static final String ORDER_SERVICE = "order-service";
    public static final String PAYMENT_SERVICE = "payment-service";
    public static final String JWT_ORDER_ID = "orderId";

    public static final String ORDER_GET = "order.get";

    // Exception Message
    public static final String INTERNAL_SERVER_ERROR = "internal error";
    public static final String FAILED = "failed";
    public static final String IS_OLD_API = "isOldApi";

    public static final String JWT_SECRET_KEY_ORDER_SERVICE = "Ak37osje2360KHy#0ncWR0J$xw29";
    public static final String JWT_SECRET_KEY_PAYMENT_SERVICE = "s7kys&923onwg81AE9$$qdvs45";

    public static final String JWT_CLIENT_ID = "Client-id";

    public static final String JWT_TOKEN_GENERATION_ERROR_MSG = "Error in jwt token generation";
    public static final int JWT_EXPIRATION_TIME = 3600000;

    // Order Status
    public static final String ORDER_STATUS_CREATED = "created";
    public static final String ORDER_STATUS_FAILED = "failed";
    // Payment Status
    public static final String PAYMENT_STATUS_CREATED = "created";
    public static final String PAYMENT_STATUS_AUTHORIZED = "authorized";
    public static final String PAYMENT_STATUS_CAPTURED = "captured";
    public static final String PAYMENT_STATUS_REFUNDED = "refunded";
    public static final String PAYMENT_STATUS_FAILED = "failed";

    // Payment Methods
    public static final String PAYMENT_METHOD_CARD = "card";
    public static final String PAYMENT_METHOD_NB = "netbanking";
    public static final String PAYMENT_METHOD_WALLET = "wallet";
    public static final String PAYMENT_METHOD_EMI = "emi";
    public static final String PAYMENT_METHOD_UPI = "upi";

    public static final String TXN_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    public static final int BAD_REQUEST_ERROR_CODE = 9400;
    public static final int NOT_FOUND_ERROR_CODE = 9404;
    public static final int INTERNAL_SERVER_ERROR_CODE = 9500;

    public static final int INVALID_PAYMENT_ID = 1101;
    public static final int INTEGRATION_SERVICE_UNAVAILABLE_ERROR_CODE = 9000;
    public static final int ORDER_SERVICE_UNAVAILABLE_ERROR_CODE = 9001;
    public static final int DATE_PARSE_ERROR = 9002;
    public static final int UNAUTHORIZED_ERROR_CODE = 9004;
    public static final int MICRO_SERVICE_ERROR_CODE = 9005;
    public static final int NULL_RESPONSE_RECEIVED = 9006;
    public static final int ORDER_DETAILS_COULD_NOT_BE_FETCHED = 9007;
    public static final int JWT_TOKEN_GENERATION_ERROR_CODE = 9008;
}
