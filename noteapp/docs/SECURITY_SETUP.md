# Güvenlik ve Şifreleme Kurulumu (Clean Code)

Bu belge, NoteApp backend'ine adım adım eklenen güvenlik ve şifreleme yapısını açıklar.

---

## 1. SecurityConstants (Tek Sorumluluk)

**Dosya:** `Security/SecurityConstants.java`

- **Amaç:** URL path'leri ve rol önekleri tek yerde toplanır. Değişiklik tek noktadan yapılır; magic string kullanılmaz.
- **Sabitler:**
  - `REGISTER_PATH`: Kayıt endpoint'i (`/users`) — kimlik doğrulama gerektirmez.
  - `NOTES_PATH`: Not API path'i (`/notes/**`) — giriş gerekir.
  - `ROLE_PREFIX`: Yetki öneki (`ROLE_`) — Spring Security ile uyum için.

---

## 2. UserRepository — findByUsername

**Dosya:** `com.bugrahan.noteapp.Repository.UserRepository`

- **Eklenen:** `Optional<Users> findByUsername(String username)`
- **Amaç:** Giriş sırasında kullanıcıyı kullanıcı adına göre bulmak. `CustomUserDetailsService` bu metodu kullanır.

---

## 3. CustomUserDetailsService (Mevcut Tablo ile Entegrasyon)

**Dosya:** `Security/CustomUserDetailsService.java`

- **Amaç:** Uygulamanızdaki `users` tablosunu kullanarak giriş doğrulaması. Spring Security, her giriş denemesinde kullanıcıyı bu servis üzerinden yükler.
- **Sorumluluk:** Sadece kullanıcı adına göre `UserDetails` döndürmek.
- **Davranış:**
  - `loadUserByUsername(username)` → Veritabanından kullanıcıyı bulur, yoksa `UsernameNotFoundException` fırlatır.
  - Rol (örn. `USER`) → `ROLE_USER` formatına çevrilir (Spring Security uyumu).
  - Rol boşsa varsayılan `ROLE_USER` atanır.

---

## 4. DemoSecurity (Merkez Yapılandırma)

**Dosya:** `Security/DemoSecurity.java`

- **PasswordEncoder bean:** BCrypt kullanılır. Hem kayıt hem girişte aynı encoder kullanılmalıdır.
- **SecurityFilterChain:**
  - `POST /users` → Herkese açık (kayıt).
  - `/notes/**` ve diğer tüm istekler → Kimlik doğrulama gerekir.
  - Form login ve logout varsayılan ayarlarla açıktır.
- **AuthenticationManager:** `AuthenticationConfiguration` üzerinden alınır; context'teki `UserDetailsService` ve `PasswordEncoder` otomatik kullanılır.
- **Clean code:** Path'ler `SecurityConstants` üzerinden okunur; her bean tek sorumluluk taşır.

---

## 5. UserServiceImpl — Kayıt Öncesi Şifre Hashleme

**Dosya:** `com.bugrahan.noteapp.Service.UserServiceImpl`

- **PasswordEncoder** constructor ile enjekte edilir.
- **saveUser(Users user):**
  - Şifre düz metin (plain text) geliyorsa → `passwordEncoder.encode()` ile hash'lenir ve entity'ye set edilir.
  - Şifre zaten BCrypt hash ise (`$2a$` ile başlıyorsa) → Tekrar encode edilmez (güncelleme senaryosu için).
- **isAlreadyEncoded(String):** BCrypt hash'leri tekrar encode etmeyi önler.

---

## Akış Özeti

1. **Kayıt (POST /users):** İstek herkese açık. `UserController` → `UserService.saveUser()` → Şifre hash'lenir → Veritabanına yazılır.
2. **Giriş:** Kullanıcı form/login ile girer → Spring Security `CustomUserDetailsService.loadUserByUsername()` ile kullanıcıyı yükler → Şifre `PasswordEncoder` ile karşılaştırılır.
3. **Not API (/notes/**):** Sadece giriş yapmış kullanıcılar erişebilir.

---

## Veritabanı Notu

- Mevcut `users` tablonuz (username, password, email, role) kullanılıyor; ekstra Spring Security tabloları gerekmez.
- Yeni kayıtlarda `password` sütununda BCrypt hash (örn. `$2a$10$...`) saklanır. Eski düz metin şifrelerle giriş yapılamaz; gerekirse kullanıcıların şifrelerini sıfırlaması veya yeniden kayıt olması gerekir.
