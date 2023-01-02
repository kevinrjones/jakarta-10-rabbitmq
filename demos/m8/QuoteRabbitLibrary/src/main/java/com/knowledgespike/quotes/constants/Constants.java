package com.knowledgespike.quotes.constants;

public final class Constants {
    private Constants() {
    }

    public static final String DefaultExchange = "";
    public static final String QuotesExchange = "quotes_exchange";
    public static final String DirectExchange = "direct_exchange";
    public static final String RegisterUserRoutingKey = "users";
    public static final String RegisterUserWithExchangeRoutingKey = "direct_route";
    public static final String RegisterUserWithExchangeAnotherRoutingKey = "another_direct_route";
    public static final String RegisterUserWithExchangeQueue = "direct_queue";
    public static final String LoanApplicationQueue = "loan_application";

    public final static String TopicExchangeName = "quotes_exchange_topic";

}
