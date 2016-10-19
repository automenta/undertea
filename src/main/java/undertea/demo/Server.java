package undertea.demo;

import io.undertow.Undertow;
import io.undertow.server.handlers.cache.DirectBufferCache;
import io.undertow.server.handlers.resource.CachingResourceManager;
import io.undertow.server.handlers.resource.PathResourceManager;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
import static io.undertow.UndertowOptions.ENABLE_HTTP2;
import static io.undertow.UndertowOptions.ENABLE_SPDY;


public class Server {

    public static void main(String[] args) {

        int httpPort = 8080;

        PathResourceManager resourcePath = new PathResourceManager(Paths.get(
                new File("./src/main/java").getAbsolutePath().replace("./", "")
        ), 0, true, true);


        Undertow.builder()
                .addHttpListener(httpPort, "0.0.0.0")
                .setServerOption(ENABLE_HTTP2, true)
                .setServerOption(ENABLE_SPDY, true)
                .setIoThreads(2)
                .setHandler(path()
                        .addPrefixPath("/", resource(
                                new CachingResourceManager(
                                        16384,
                                        16 * 1024 * 1024,
                                        new DirectBufferCache(100, 10, 1000),
                                        resourcePath,
                                        0 //7 * 24 * 60 * 60 * 1000
                                ))
                                .setDirectoryListingEnabled(true)
                                .addWelcomeFiles("index.html")
                        ))
                .build().start();

    }

}
