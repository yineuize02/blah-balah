package com.fml.blah.gateway.utils;

import cn.hutool.json.JSONUtil;
import com.fml.blah.common.vo.WebResponse;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public class WebUtils {
  public static Mono<Void> ofMonoResponse(ServerHttpResponse response, WebResponse webResponse) {
    response.setStatusCode(HttpStatus.OK);
    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.getHeaders().set("Access-Control-Allow-Origin", "*");
    response.getHeaders().set("Cache-Control", "no-cache");
    String body = JSONUtil.toJsonStr(webResponse);
    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
    return response
        .writeWith(Mono.just(buffer))
        .doOnError(error -> DataBufferUtils.release(buffer));
  }
}
