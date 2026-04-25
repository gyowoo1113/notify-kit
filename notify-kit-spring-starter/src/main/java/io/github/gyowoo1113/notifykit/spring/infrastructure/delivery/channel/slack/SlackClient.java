package io.github.gyowoo1113.notifykit.spring.infrastructure.delivery.channel.slack;

import io.github.gyowoo1113.notifykit.core.exception.NotRetryableException;
import io.github.gyowoo1113.notifykit.core.exception.RetryableException;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SlackClient {

    private final String webhookUrl;
    private final RestClient restClient;

    public SlackClient(String webhookUrl) {
        this.webhookUrl = webhookUrl;
        this.restClient = RestClient.create();
    }

    public CompletableFuture<Void> send(String channel, String text){
        try{
            restClient.post()
                    .uri(webhookUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            // "channel", channel,
                            "text",text
                    ))
                    .retrieve()
                    .toBodilessEntity();

            return CompletableFuture.completedFuture(null);
        } catch (Exception e){
            throw new RetryableException("slack", e);
        }
    }
}
