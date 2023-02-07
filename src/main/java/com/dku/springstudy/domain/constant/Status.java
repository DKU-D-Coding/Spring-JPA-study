package com.dku.springstudy.domain.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Status {

    PROGRESS("판매중"), COMPLETE("거래완료");

    private final String name;

    // 캐싱해두므로 조할 때마다 모든 값 순회하지 않는다.
    private static final Map<String, Status> BY_NAME =
            Stream.of(values()).collect(Collectors.toMap(Status::name, e -> e));

    public static Status valueOfName(String name) {
        return BY_NAME.get(name);
    }
}
