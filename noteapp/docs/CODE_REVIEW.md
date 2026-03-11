# NoteApp — Proje Code Review

**Tarih:** 2025  
**Kapsam:** Backend (Spring Boot 4, Java 21)

---

## 1. Özet

Proje katmanlı mimari (Controller → Service → Repository) ve Spring Security ile temel güvenlik (BCrypt, CustomUserDetailsService) kullanıyor. Aşağıda **güçlü yönler**, **sorunlar** ve **öneriler** madde madde listelenmiştir.

---

## 2. Güçlü Yönler

| Alan | Açıklama |
|------|----------|
| **Mimari** | Controller / Service / Repository ayrımı net; interface + impl kullanımı var. |
| **Güvenlik** | BCrypt, SecurityConstants, CustomUserDetailsService, SecurityFilterChain ile temel auth akışı kurulmuş. |
| **Validasyon** | Entity'lerde `@Valid`, `@NotBlank`, `@Size`, `@Email` kullanılmış; `GlobalExceptionHandler` ile MethodArgumentNotValidException merkezi işleniyor. |
| **Şifre** | Kayıt/güncellemede şifre hash'leniyor; `isAlreadyEncoded` ile çift encode önleniyor. |
| **Clean code** | Security path'leri `SecurityConstants`'ta toplanmış; tek sorumluluk prensibi Security tarafında uygulanmış. |

---

## 3. Sorunlar ve Öneriler

### 3.1 Kritik / Güvenlik

| # | Sorun | Konum | Öneri |
|---|--------|--------|--------|
| 1 | **Kullanıcı yanıtında şifre sızıntısı** | `UserController` (GET /users, GET /users/{id}, POST /users) | API `Users` entity'sini doğrudan döndürüyor; `password` (ve hash) JSON'da gider. **Öneri:** DTO kullanın (örn. `UserResponse`: id, username, email, role; password yok) veya `@JsonIgnore` ile password'ü serialize etmeyin. |
| 2 | **Not sahipliği kontrolü yok** | `NoteController`, `NoteServiceImpl` | `getAllNotes()` tüm kullanıcıların notlarını döndürüyor. `updateNote`'ta `note.getUsers()` ile isteği yapan kullanıcı, notun sahibini başka bir kullanıcıya değiştirebiliyor. **Öneri:** Notları oturumdaki kullanıcıya göre filtreleyin (`Principal` / `Authentication`); güncelleme/silme için "bu not bana ait mi?" kontrolü ekleyin. |
| 3 | **Kullanıcı listesi herkese açık değil ama GET /users korumalı** | `DemoSecurity` | Sadece `POST /users` permitAll; GET /users authenticated. Giriş yapmış her kullanıcı tüm kullanıcı listesini görebiliyor. İş kuralına göre GET /users'ı kısıtlamak veya sadece kendi profilini göstermek mantıklı olabilir. |

### 3.2 Orta Seviye

| # | Sorun | Konum | Öneri |
|---|--------|--------|--------|
| 4 | **Exception türü çok genel** | `NoteServiceImpl.getNoteById`, `NoteController.getNoteById` | `RuntimeException` fırlatılıyor / yakalanıyor. **Öneri:** `NoteNotFoundException` gibi özel exception + `@ExceptionHandler` ile 404 dönmek daha okunabilir ve tutarlı olur. |
| 5 | **Loglama** | `GlobalExceptionHandler` | `System.out.println` kullanılıyor. **Öneri:** SLF4J Logger kullanın; production'da log seviyesi ve yapılandırma daha iyi kontrol edilir. |
| 6 | **updateNote validasyonu** | `NoteController.updateNote` | `@Valid` yok; body'deki title/content boş veya hatalı olabilir. **Öneri:** DTO veya entity üzerinde `@Valid` kullanın. |

### 3.3 Küçük / Temizlik

| # | Sorun | Konum | Öneri |
|---|--------|--------|--------|
| 7 | **Gereksiz @Controller** | `NoteController` | Hem `@Controller` hem `@RestController` var; `@RestController` yeterli. **Öneri:** `@Controller` kaldırın. |
| 8 | **Kullanılmayan / tekrarlı import** | `NoteServiceImpl` | `javax.naming.spi.DirStateFactory.Result` kullanılmıyor; `java.util.Optional` iki kez import edilmiş. **Öneri:** Kullanılmayan ve tekrarlı importları kaldırın. |
| 9 | **getNoteById stili** | `NoteServiceImpl.getNoteById` | `Optional` ile if/else ve manuel null/throw kullanılıyor. **Öneri:** `return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("Note not found"));` daha kısa ve okunabilir. |
| 10 | **Package tutarlılığı** | `Security` | Security sınıfları `Security` package'ında; uygulama `com.bugrahan.noteapp` altında. **Öneri:** Uzun vadede `com.bugrahan.noteapp.security` kullanmak paket yapısını tutarlı kılar. |

---

## 4. Güvenlik Özeti

- **Şifre:** BCrypt ve kayıt/güncellemede encode iyi.
- **Erişim:** POST /users açık, diğer uçlar authenticated; form login var.
- **Eksikler:** API yanıtında şifre alanının çıkarılması, notların kullanıcıya göre filtrelenmesi ve not sahipliği kontrolleri.

---

## 5. Clean Code ve Bakım

- **İyi:** SecurityConstants, interface/impl ayrımı, validasyon mesajları Türkçe ve anlamlı.
- **İyileştirilebilir:** Controller’da DTO kullanımı, özel exception’lar, logger, Optional kullanımının tutarlılaştırılması.

---

## 6. Sonuç

Proje öğrenme / demo için sağlam bir temele sahip; production için **şifre alanının API’de dönmemesi**, **notların kullanıcıya göre kısıtlanması** ve **not sahipliği kontrolleri** mutlaka eklenmeli. Küçük düzeltmeler (import, @Controller, getNoteById stili, logger) bakımı kolaylaştırır.

**Öncelik sırası önerisi:**  
1) API’de şifre gizleme (DTO veya @JsonIgnore)  
2) Notları oturumdaki kullanıcıya göre filtreleme ve sahiplik kontrolü  
3) Logger, özel exception, Optional/orElseThrow ve import/annotation temizliği  
