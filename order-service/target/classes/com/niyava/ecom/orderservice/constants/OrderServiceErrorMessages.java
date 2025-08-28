package com.niyava.ecom.orderservice.constants;

public class OrderServiceErrorMessages {
    public static final String FAILED = "failed";
    public static final String SUCCESS = "success";
    public static final String ORDER_CREATION_FAILED_ERR_MSG = "Could not create the order. Kindly try after some time.";
    public static final String INVALID_ORDER_ID_ERR_MSG = "Invalid Order Id";
    public static final String INVALID_PARAMETER_IN_REQUEST = "Invalid parameters in request";
    public static final String ERR_MSG_INVALID_PAYLOAD = "Invalid payload";
    public static final String ERR_MSG_API_ERROR = "API Error: Pls try again after some time";
    public static final String ERR_MSG_INVALID_EMAIL = "Invalid email";
    public static final String ERR_MSG_INVALID_TOTAL_MRP_GT1 = "Total MRP must be greater than or equal to 1";
    public static final String ERR_MSG_INVALID_NET_AMT_GT1 = "Net amount must be greater than or equal to 1";
    public static final String ERR_MSG_INVALID_ORDER_TOTAL_QTY_GT1 = "Total order quantity must be greater than or equal to 1";
    public static final String ERR_MSG_INVALID_ORDER_ITEM_QTY_GT1 = "Item quantity must be greater than or equal to 1";
    public static final String ERR_MSG_INVALID_ORDER_DATE_TODAY = "Order date can only be today's date";
}
