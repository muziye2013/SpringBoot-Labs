package cn.iocoder.springcloud.labx08.gatewaydemo.context;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Gateway Context Use Cache Request Content
 * @author chenggang
 * @date 2019/01/29
 */
@Getter
@Setter
@ToString
@Component
public class GatewayContext {

    public static final String CACHE_GATEWAY_CONTEXT = "cacheGatewayContext";
    /**
     * whether read request data
     */
    protected Boolean readRequestData = true;
    /**
     * whether read response data
     */
    protected Boolean readResponseData = false;
    /**
     * cache json body
     */
    protected String requestBody;
    /**
     * cache Response Body
     */
    protected Object responseBody;
    /**
     * request headers
     */
    protected HttpHeaders requestHeaders;
    /**
     * cache form data
     */
    protected MultiValueMap<String, String> formData;
    /**
     * cache all request data include:form data and query param
     */
    protected MultiValueMap<String, String> allRequestData = new LinkedMultiValueMap<>(0);


}