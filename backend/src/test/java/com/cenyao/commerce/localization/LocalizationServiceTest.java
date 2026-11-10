package com.cenyao.commerce.localization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalizationServiceTest {

    @Test
    void skipWhenTargetIsSimplifiedChinese() {
        LocalizationService service = new LocalizationService(new MockTranslationProvider());
        var result = service.localize("标题", "<p>详情</p>", "zh-CN");
        assertFalse(result.translated());
        assertEquals("标题", result.title());
    }

    @Test
    void mustTranslateWhenTargetDiffers() {
        LocalizationService service = new LocalizationService(new MockTranslationProvider());
        var result = service.localize("模拟商品", "<p>详情</p>", "zh-TW");
        assertTrue(result.translated());
        assertNotEquals("模拟商品", result.title());
        assertFalse(result.title().isBlank());
    }

    @Test
    void englishGetsLanguagePrefix() {
        LocalizationService service = new LocalizationService(new MockTranslationProvider());
        var result = service.localize("标题", "详情", "en");
        assertTrue(result.translated());
        assertTrue(result.title().startsWith("[en]"));
    }
}
