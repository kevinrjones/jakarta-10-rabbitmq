package com.knowledgespike.api;

import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.knowledgespike.quotes.producer.PublishFanOutPriceQuotes;
import io.javalin.http.Handler;

import static com.knowledgespike.api.HandlerHelpers.*;

public class FanOutQuotesController {

    public static Handler getQuotes = ctx -> {

        try {
            var message = ctx.bodyAsClass(CarDetailsMessage.class);

            var publish = new PublishFanOutPriceQuotes(Application.connection);

            if(publish.publishMessage(message)) {
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }


        } catch (Exception e) {
            handleError(ctx, e);
        }

    };


}
