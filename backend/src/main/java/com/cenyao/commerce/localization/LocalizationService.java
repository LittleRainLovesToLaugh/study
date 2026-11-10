package com.cenyao.commerce.localization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 本地化转换：按店铺站点/语言转换标题、详情等。
 * <p>
 * 硬规则：目标语言 ≠ 源语言（简体中文）时禁止跳过。
 * </p>
 */
@Service
@RequiredArgsConstructor
public class LocalizationService {

    public static final String SOURCE_LANG = "zh-CN";

    private final TranslationProvider translationProvider;

    public LocalizedContent localize(String title, String detailHtml, String targetLanguage) {
        if (targetLanguage == null || targetLanguage.isBlank()) {
            throw new IllegalArgumentException("目标语言不能为空");
        }
        boolean sameAsSource = SOURCE_LANG.equalsIgnoreCase(targetLanguage)
                || "zh".equalsIgnoreCase(targetLanguage);
        if (sameAsSource) {
            return new LocalizedContent(title, detailHtml, targetLanguage, false);
        }
        String localizedTitle = translationProvider.translate(title, SOURCE_LANG, targetLanguage);
        String localizedDetail = translationProvider.translate(detailHtml, SOURCE_LANG, targetLanguage);
        return new LocalizedContent(localizedTitle, localizedDetail, targetLanguage, true);
    }

    public record LocalizedContent(
            String title,
            String detailHtml,
            String targetLanguage,
            boolean translated
    ) {
    }
}
