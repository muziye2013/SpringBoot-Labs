package cn.iocoder.springcloud.labx08.gatewaydemo.config;

import cn.hutool.crypto.SecureUtil;
import cn.iocoder.springcloud.labx08.gatewaydemo.filter.CsdnRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.UnsupportedEncodingException;

@Configuration
public class RouteConfiguration {
    @Value("${gateway.csdn.host}")
    private String csdnHost;

    @Value("${gateway.csdn.PaaSID}")
    private String paaSID;

    @Value("${gateway.csdn.PaaSToken}")
    private String paaSToken;

    @Value("${gateway.csdn.nonce}")
    private String nonce;

    @Autowired
    private CsdnRequestFilter csdnRequestFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) throws UnsupportedEncodingException {
        String timeTamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = SecureUtil.sha256(timeTamp + nonce + paaSToken);

        return builder.routes()
                .route(r -> r.path("/csdn2/**")
                        .filters(f ->
                                f.stripPrefix(1)
                                .addRequestHeader("x-tif-nonce", nonce)
                                .addRequestHeader("x-tif-signature", signature)
                                .addRequestHeader("x-tif-paasid", paaSID)
                                .addRequestHeader("x-tif-timestamp", timeTamp))
                        .uri(csdnHost)
                        .order(2))
                .route(r -> r.path("/csdn3/**")
                        .filters(f->f.stripPrefix(1)
                                .filter(csdnRequestFilter))
                        .uri(csdnHost)
                        .order(3))
                .build();
    }
}
