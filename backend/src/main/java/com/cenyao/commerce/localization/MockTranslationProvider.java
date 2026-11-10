package com.cenyao.commerce.localization;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 本地化翻译 Mock：仅加前缀标记，便于联调。
 */
@Slf4j
@Component
public class MockTranslationProvider implements TranslationProvider {

    @Override
    public String providerCode() {
        return "MOCK";
    }

    @Override
    public String translate(String text, String sourceLang, String targetLang) {
        if (text == null) {
            return null;
        }
        if (targetLang == null || targetLang.equalsIgnoreCase(sourceLang)
                || "zh-CN".equalsIgnoreCase(targetLang) || "zh".equalsIgnoreCase(targetLang)) {
            return text;
        }
        log.info("【Stub】翻译 {} → {}，长度={}", sourceLang, targetLang, text.length());
        return "[" + targetLang + "] " + text;
    }
}
