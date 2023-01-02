package com.knowledgespike.api;

import com.knowledgespike.quotes.messages.CarDetailsMessage;
import com.knowledgespike.quotes.messages.HomeDetailsMessage;
import com.knowledgespike.quotes.producer.HeaderRoutingPriceQuotes;
import com.knowledgespike.quotes.producer.PublishTopicPriceQuotes;
import io.javalin.http.Handler;

import java.util.HashMap;

import static com.knowledgespike.api.HandlerHelpers.*;

public class HeaderQuotesController {

    public static Handler getCarQuotes = ctx -> {

        try {
            var message = ctx.bodyAsClass(CarDetailsMessage.class);

            var publish = new HeaderRoutingPriceQuotes(Application.connection);

            var headers = new HashMap<String, Object>();

            var vehicleType = ctx.queryParam("vehicle");

            if(vehicleType != null && !vehicleType.isEmpty()) {
                headers.put("vehicle", vehicleType);
            } else {
                headers.put("insurance", "car");
            }

            if(publish.publishMessage(message, headers)){
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

            var publish = new HeaderRoutingPriceQuotes(Application.connection);
            var headers = new HashMap<String, Object>();

            headers.put("insurance", "buildings");
            headers.put("type", "business");

            if(publish.publishMessage(message, headers)){
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }

        } catch (Exception e) {
            handleError(ctx, e);
        }

    };


}
