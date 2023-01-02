package com.knowledgespike.quotes.constants;

public final class Constants {


    private Constants() {
    }

    public static String DlxRoutingKey = "user_dlx";
    public static final String DefaultExchange = "";
    public static final String QuotesExchange = "quotes_exchange";
    public static final String DirectExchange = "direct_exchange";
    public static final String RegisterUserRoutingKey = "users";
    public static final String RegisterUserWithExchangeRoutingKey = "direct_route";
    public static final String RegisterUserWithExchangeAnotherRoutingKey = "another_direct_route";
    public static final String RegisterUserWithExchangeQueue = "direct_queue";
    public static final String RegisterUserWithExchangeQueueD = "direct_queue_d";
    public static final String RegisterUserWithExchangeQueueND = "direct_queue_nd";
    public static final String LoanApplicationQueue = "loan_application";

    public final static String TopicExchangeName = "quotes_exchange_topic";
    public final static String HeadersExchange = "quotes_headers_exchange";
    public final static String HeadersCarQueue = "headers_car_queue";
    public final static String HeadersHomeQueue = "headers_home_queue";

    public final static String DlxQueueName = "user_queue_dlx";
    public final static String DlxExchangeName = "user_dlx";

}
