package com.dsmh.dashaoins.web.controller;


import com.dsmh.dashaoins.acl.INSClientManager;
import com.dsmh.dashaoins.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/gateway.do")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashaoinsGatewayController {


    @PostMapping("/decrypt")
    public ResponseEntity<?> decryptMessage(@RequestBody Message encryptedData) {
        encryptedData.decryptBizContent(encryptedData.getSecret());
        return ResponseEntity.ok().body(encryptedData.getData());
    }

    @PostMapping("/encrypt")
    public ResponseEntity<?> encryptMessage(@RequestBody Message message) {
        message.encryptBizContent(message.getSecret());
        return ResponseEntity.ok().body(message);
    }
}
