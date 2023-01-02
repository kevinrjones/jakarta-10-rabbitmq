package com.knowledgespike.api;

import com.knowledgespike.quotes.messages.UserRegistrationMessage;
import com.knowledgespike.quotes.producer.PublishRegisterUser;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.knowledgespike.api.HandlerHelpers.*;

public class UserController {

    public static Handler registerUser = ctx -> {
        try{
            var message = ctx.bodyAsClass(UserRegistrationMessage.class);

            var publishRegisterUser = new PublishRegisterUser(Application.connection);

            if(publishRegisterUser.publishMessage(message)) {
                handleSuccess(ctx);
            } else {
                handleFailure(ctx);
            }

        } catch (Exception e) {
            handleError(ctx, e);
        }
    };


}