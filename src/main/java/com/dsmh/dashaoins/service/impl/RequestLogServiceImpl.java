//package com.dsmh.dashaoins.service.impl;
//
//import com.dsmh.dashaoins.service.RequestLogService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
///**
// * @author TeamScorpio
// * @since 2022/4/21
// */
//@RequiredArgsConstructor
//@Service
//public class RequestLogServiceImpl implements RequestLogService {
//
//
//    private final INSLogRepository insLogRepository;
//
//    private final APPLogRepository appLogRepository;
//
//    @Async
//    @Override
//    public void logAPPRequest(RequestLog log) {
//        appLogRepository.save(new APPLog(log));
//    }
//
//    @Async
//    @Override
//    public void logINSRequest(RequestLog log) {
//       insLogRepository.save(new INSLog(log));
//    }
//
//}
