package com.rakuten.ross.auroraj.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class XtrDocService {

    public XtrDoc getByUrl(String url) {
        return XtrDoc.builder()
            .url("https://my.oschina.net/u/4939618/blog/17892139")
            .title("订单系统详细设计")
            .content("(重要提示注意，该内容来自aurora学习小组,请不要随意传播！)，订单系统详细设计暂时移交上级高度保密，请申请权限后到上级系统查阅！")
            .build();
    }
}
