package com.agustin.literalura.book.domain;

public enum Language {
    EN("en"),
    ES("es"),
    FR("fr"),
    DE("de"),
    IT("it"),
    PT("pt"),
    RU("ru"),
    ZH("zh"),
    JA("ja"),
    AR("ar"),
    HI("hi"),
    KO("ko"),
    TR("tr"),
    NL("nl"),
    PL("pl"),
    SV("sv"),
    UK("uk"),
    RO("ro"),
    OTHER("others");

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public static Language fromString(String text) {
        for (Language lang : Language.values()) {
            if (lang.displayName.equalsIgnoreCase(text)) {
                return lang;
            }
        }
        return OTHER;
    }

    public String getDisplayName() {
        return displayName;
    }
}
