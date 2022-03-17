package com.example.notification.send.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(builderClassName = "All", builderMethodName = "all")
public class SlackRequest {
    private String channel;
    private String text;
}
