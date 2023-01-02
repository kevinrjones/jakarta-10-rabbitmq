package com.knowledgespike.api;

import io.javalin.http.Context;

public class HandlerHelpers {

    protected static void handleError(Context ctx, Exception e) {
        System.out.println(" [x] Unable to parse incoming JSON: " + e.getMessage());
        ctx.json(new Envelope<>(null, "Unable to parse incoming JSON"));
        ctx.res().setStatus(400);
    }

    protected static void handleSuccess(Context ctx) {
        ctx.json(new Envelope<>());
        ctx.res().setStatus(200);
    }
    protected static void handleSuccess(Context ctx, String message) {
        ctx.json(new Envelope<>(message, null));
        ctx.res().setStatus(200);
    }
    protected static void handleFailure(Context ctx) {
        ctx.res().setStatus(400);
        ctx.json(new Envelope<>(null, "Unable to publish the message"));
    }
}
