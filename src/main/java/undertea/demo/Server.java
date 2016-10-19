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

/**
 * Created by me on 10/19/16.
 */
public class Server {
    public static void main(String[] args) {

        int httpPort = 8080;


        File c = new File("./src/main/java");
        //File c = new File(WebServer.class.getResource("/").getPath());
        String cp = c.getAbsolutePath().replace("./", "");

//            if (cp.contains("web/web")) //happens if run from web/ directory
//                cp = cp.replace("web/web", "web");

        Path rpath = Paths.get(
                //System.getProperty("user.home")
                cp
        );
        PathResourceManager resourcePath = new PathResourceManager(rpath, 0, true, true);


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
