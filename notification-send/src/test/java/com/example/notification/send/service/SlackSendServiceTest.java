package com.example.notification.send.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class SlackSendServiceTest {
    // ACCESS_TOKEN: xoxe.xoxp-1-Mi0yLTE1ODU5NTA4ODAzOS0yNzM3MzE2NTEzNTQwLTMyNTQ5MTM1MTM4MTItMzI0OTY4MjE4MTgyOS04OTJmNDNkM2MxZTI1NjQ2Mzc4NTRhMWJkMzdhNTdmZTZmODQ5NThlM2IyZTI3OWJmODA4NjRlMmY3Mjc2MzAw
    // REFRESH_TOKEN: xoxe-1-My0xLTE1ODU5NTA4ODAzOS0zMjU0OTEzNTEzODEyLTMyNTI1NzQwNzAwNTAtNWExOWVhNzQxYTVlMTZkNzE3NTYyOGViZDAzMjJmZTIyMWYyOGIxNTkxOTNjOGM3MWRmOTZlYTI0NTc1MTU2ZQ

    private final String ACCESS_TOKEN = "xoxe.xoxp-1-Mi0yLTE1ODU5NTA4ODAzOS0yNzM3MzE2NTEzNTQwLTMyNTQ5MTM1MTM4MTItMzI0OTY4MjE4MTgyOS04OTJmNDNkM2MxZTI1NjQ2Mzc4NTRhMWJkMzdhNTdmZTZmODQ5NThlM2IyZTI3OWJmODA4NjRlMmY3Mjc2MzAw";
    private final String REFRESH_TOKEN = "xoxe-1-My0xLTE1ODU5NTA4ODAzOS0zMjU0OTEzNTEzODEyLTMyNTI1NzQwNzAwNTAtNWExOWVhNzQxYTVlMTZkNzE3NTYyOGViZDAzMjJmZTIyMWYyOGIxNTkxOTNjOGM3MWRmOTZlYTI0NTc1MTU2ZQ";
    private final String SLACK_TOKEN = "xoxb-158595088039-2125501385222-57WwvxaphwIPvd3RoHz9Ttxj";
//    private final String SLACK_WEBHOOKS_URL_DAVE = "https://hooks.slack.com/services/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";
    private final String SLACK_WEBHOOKS_URL_DAVE = "https://hooks.slack.com/services/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";
    private final String SLACK_WEBHOOKS_URL = "https://hooks.slack.com/services/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";
    Slack slack = Slack.getInstance();

    @Test
    public void sendSlack() throws Exception {

        MethodsClient methods = slack.methods(ACCESS_TOKEN);

        // Build a request object
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("데이브_TI_가든")
                .text("@데이브 TEST")
                .build();

        // Get a response as a Java object
        ChatPostMessageResponse response = methods.chatPostMessage(request);
        log.info("response: {}", response);
    }

    @Test
    public void webhookTest() throws Exception {
        /**
         * 유럽: 트렌비 영국, 트렌비 독일, 트렌비 이탈리아, 트렌비 프랑스, 해외부띠크-이태리
         * 주문서 상태 값: 현지 주문완료, 현지바잉중
         * #eu-outlet-md-service
         * 셀러: 아울렛-EU (109)
         * 셀러: 아울렛-이탈리아(547)
         *
         * #eu-homeliving-service:
         * 카테고리 : 홈_리빙
         *
         * #eu-central-md-service
         * 그 외 나머지
         *
         * 주문서 상태 값: 현지도착, 배송준비중
         * #  eu-io-service
         *
         * 미국: 트렌비 미국
         * 주문서 상태값: 현지주문완료, 현지도착, 배송준비중
         * #  us-io-service => us-outlet-md-service 로 변경?
         */
//        String payload="{\"channel\": \"@데이브_TI_가든\",\"text\": \"테스트 입니다.\n또 테스트 입니다.\", \"username\": \"GARDEN\", \"icon_url\": \"https://slack.com/img/icons/app-57.png\"}"; // channel_not_found ERROR
        String payload="{\"text\": \"테스트 입니다.\n또 테스트 입니다.\"}";
//        String payload="{\"channel\": \"#eu-outlet-md-service\", \"text\": \"테스트 입니다..\n또 테스트 입니다.\"}";
//        String payload="{\"channel\": \"#eu-homeliving-service\", \"text\": \"테스트 입니다..\n또 테스트 입니다.\"}";
//        String payload="{\"channel\": \"#eu-central-md-service\", \"text\": \"테스트 입니다..\n또 테스트 입니다.\"}";

        WebhookResponse response = slack.send(SLACK_WEBHOOKS_URL_DAVE, payload);
//        WebhookResponse response = slack.send(SLACK_WEBHOOKS_URL, payload);
        log.info("response: {}", response);
    }
}