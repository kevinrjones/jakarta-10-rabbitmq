package com.knowledgespike.api;

import com.knowledgespike.quotes.messages.LoanApplicationMessage;
import com.knowledgespike.quotes.producer.PublishLoanApplication;
import io.javalin.http.Handler;

import static com.knowledgespike.api.HandlerHelpers.*;

public class LoanController {

    public static Handler apply = ctx -> {
        try{
            var message = ctx.bodyAsClass(LoanApplicationMessage.class);

            var publisher = new PublishLoanApplication(Application.connection);
            var messageResponse = publisher.publishMessage(message);

            if (messageResponse != null) {
                handleSuccess(ctx, messageResponse);
            } else {
                handleFailure(ctx);
            }

        } catch (Exception e) {
            handleError(ctx, e);
        }
    };

}