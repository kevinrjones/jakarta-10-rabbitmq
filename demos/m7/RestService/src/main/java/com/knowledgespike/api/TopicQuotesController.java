package com.knowledgespike.api;

import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.knowledgespike.quotes.messages.HomeDetailsMessage;
import com.knowledgespike.quotes.producer.PublishTopicPriceQuotes;
import io.javalin.http.Handler;

import static com.knowledgespike.api.HandlerHelpers.*;

public class TopicQuotesController {

    public static Handler getCarQuotes = ctx -> {

        try {
            var message = ctx.bodyAsClass(CarDetailsMessage.class);

            var publish = new PublishTopicPriceQuotes(Application.connection);

            if(publish.publishMessage(message, "quotes.car")) {
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }

        } catch (Exception e) {
            handleError(ctx, e);
        }

    };

    public static Handler getHomeQuotes = ctx -> {

        try {
            var message = ctx.bodyAsClass(HomeDetailsMessage.class);

            var publish = new PublishTopicPriceQuotes(Application.connection);

            if(publish.publishMessage(message, "quotes.home")) {
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }

        } catch (Exception e) {
            handleError(ctx, e);
        }

    };


}
