package com.cenyao.commerce.pipeline;

import com.cenyao.commerce.common.ApiResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 流水线演示接口：采集 → 本地化 → 刊登（Stub）。
 */
@RestController
@RequestMapping("/api/v1/pipeline")
@RequiredArgsConstructor
public class PipelineController {

    private final PipelineDemoService pipelineDemoService;

    @PostMapping("/demo-run")
    public ApiResponse<Map<String, Object>> demoRun(@RequestBody(required = false) DemoRunRequest request) {
        DemoRunRequest req = request == null ? new DemoRunRequest() : request;
        String lang = req.getTargetLanguage() == null || req.getTargetLanguage().isBlank()
                ? "zh-TW" : req.getTargetLanguage();
        String site = req.getSiteRegion() == null || req.getSiteRegion().isBlank()
                ? "TW" : req.getSiteRegion();
        return ApiResponse.ok(pipelineDemoService.runDemo(req.getSourceOfferId(), lang, site));
    }

    @Data
    public static class DemoRunRequest {
        private String sourceOfferId;
        private String targetLanguage;
        private String siteRegion;
    }
}
