package cn.iocoder.springcloud.labx08.gatewaydemo.filter;


import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 交换中心-网关鉴权
 *
 * @author xujinggen@geostar.com.cn
 * @date 2020-3-16
 */
@Component
public class CsdnRequestFilter implements GatewayFilter, Ordered {

    @Value("${gateway.csdn.PaaSID}")
    private String paaSID;

    @Value("${gateway.csdn.PaaSToken}")
    private String paaSToken;

    @Value("${gateway.csdn.nonce}")
    private String nonce;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String timeTamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = SecureUtil.sha256(timeTamp + nonce + paaSToken);
        System.out.print("自定义过滤器：timeTamp:"+timeTamp+";signature:"+signature);
        //提取应用账户及应用令牌，鉴权
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("x-tif-nonce", nonce)
                .header("x-tif-signature", signature)
                .header("x-tif-paasid", paaSID)
                .header("x-tif-timestamp", timeTamp)
                .build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
