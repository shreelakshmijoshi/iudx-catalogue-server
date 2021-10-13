package iudx.catalogue.server.authenticator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.serviceproxy.ServiceBinder;


import static iudx.catalogue.server.authenticator.Constants.*;
import static iudx.catalogue.server.util.Constants.*;

/**
 * The Authentication Verticle.
 * <h1>Authentication Verticle</h1>
 * <p>
 * The Authentication Verticle implementation in the the IUDX Catalogue Server exposes the
 * {@link iudx.catalogue.server.authenticator.AuthenticationService} over the Vert.x Event Bus.
 * </p>
 * 
 * @version 1.0
 * @since 2020-05-31
 */

public class MockAuthenticationVerticle extends AbstractVerticle {


  private static final Logger LOGGER = LogManager.getLogger(MockAuthenticationVerticle.class);
  private AuthenticationService authentication;

  /**
   * This method is used to start the Verticle. It deploys a verticle in a cluster, registers the
   * service with the Event bus against an address, publishes the service with the service discovery
   * interface.
   * 
   * @throws Exception which is a startup exception
   */

  @Override
  public void start() throws Exception {

    String authHost = config().getString(AUTH_SERVER_HOST);
    authentication = new MockAuthenticationServiceImpl(createWebClient(vertx, config()), authHost);


    new ServiceBinder(vertx).setAddress(AUTH_SERVICE_ADDRESS)
      .register(AuthenticationService.class, authentication);
  }

  static WebClient createWebClient(Vertx vertx, JsonObject config) {
    return createWebClient(vertx, config, false);
  }

  /**
   * Helper function to create a WebClient to talk to the auth server. Uses the keystore to get the client certificate
   * required to call Auth APIs (has to be class 1). Since it's a pure function, it can be used as a helper in testing
   * initializations also.
   * @param vertx the vertx instance
   * @param properties the properties field of the verticle
   * @param testing a bool which is used to disable client side ssl checks for testing purposes
   * @return a web client initialized with the relevant client certificate
   */
  static WebClient createWebClient(Vertx vertx, JsonObject config, boolean testing) {
    /* Initialize properties from the config file */
    WebClientOptions webClientOptions = new WebClientOptions();
    if (testing) webClientOptions.setTrustAll(true).setVerifyHost(false);
    webClientOptions
            .setSsl(false)
            .setKeyStoreOptions(new JksOptions()
                    .setPath(config.getString((KEYSTORE_PATH)))
                    .setPassword(config.getString(KEYSTORE_PASSWORD)));
    return WebClient.create(vertx, webClientOptions);
  }
}
