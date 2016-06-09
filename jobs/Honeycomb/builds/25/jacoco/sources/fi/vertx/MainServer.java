package fi.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
final class MainServer {
    /**
     * Variable for port number.
     */
    private static final int PORT = 8080;

    /**
     * Instantiates a new MainServer. Private to prevent instantiation
     */
    private MainServer() {

        // Throw an exception if this ever *is* called
        throw new AssertionError("Instantiating utility class.");
    }

    /**
     * Main method initiating the vertx web server.
     *
     * @param args standard arguments
     */
    public static void main(final String[] args) {
        // Create an HTTP server which simply returns "Hello World!" to each
        // request.
        Router router = Router.router(Vertx.vertx());
        router.route().handler(BodyHandler.create());
        router.route("/test").blockingHandler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            response.end("yes");
        }, false);

        router.post("/login").blockingHandler(RouterClass::login, false);

        Vertx.vertx().createHttpServer().requestHandler(router::accept)
                .listen(PORT);
    }

}