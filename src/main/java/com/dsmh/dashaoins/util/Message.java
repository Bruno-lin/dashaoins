package com.dsmh.dashaoins.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.nio.charset.StandardCharsets;

/**
 * @author TeamScorpio
 * @since 2022/4/10
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private String secret;

    private String bizContent;

    @JsonIgnore
    private transient String data;


    @JsonIgnore
    private transient String originalDataJson;


    public void encryptBizContent(String secret) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, secret.getBytes(StandardCharsets.UTF_8));
        byte[] encrypt = aes.encrypt(JSONUtil.toJsonStr(bizContent));
        this.bizContent = Base64.encode(encrypt);
    }


    public void decryptBizContent(String secret) {
        AES aes = new AES(Mode.ECB, Padding.PKCS5Padding, secret.getBytes(StandardCharsets.UTF_8));
        byte[] decodedData = Base64.decode(this.bizContent);
        originalDataJson = aes.decryptStr(decodedData);
        data = JSONUtil.toJsonStr(originalDataJson);
    }
}
