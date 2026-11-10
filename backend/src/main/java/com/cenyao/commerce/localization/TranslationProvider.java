package com.cenyao.commerce.localization;

/**
 * 可插拔翻译提供方。不绑死单一机器翻译或大模型厂商。
 */
public interface TranslationProvider {

    /** 提供方编码，如 MOCK */
    String providerCode();

    /**
     * 将文本从源语言翻译到目标语言。
     */
    String translate(String text, String sourceLang, String targetLang);
}
