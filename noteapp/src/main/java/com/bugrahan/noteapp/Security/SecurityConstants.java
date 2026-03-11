package com.bugrahan.noteapp.Security;

/**
 * Güvenlik ile ilgili sabitler.
 * URL path'leri ve rol önekleri tek yerde toplanarak değişiklik tek noktadan yapılır (clean code).
 */
public final class SecurityConstants {

    private SecurityConstants() {
        // Utility sınıfı - örneklenmesin
    }

    /** Kayıt (register) endpoint'i - kimlik doğrulama gerektirmez */
    public static final String REGISTER_PATH = "/users";

    /** Not API base path - kimlik doğrulama gerekir */
    public static final String NOTES_PATH = "/notes/**";

    /** Spring Security'de yetki (authority) için kullanılan önek */
    public static final String ROLE_PREFIX = "ROLE_";
}
