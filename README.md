# 🏦 Ç.A.N.K.I.R.I B.A.N.K - Masaüstü Bankacılık Otomasyonu

Ç.A.N.K.I.R.I B.A.N.K, Java ve Java Swing kütüphanesi kullanılarak geliştirilmiş, modern arayüze ve yenilikçi güvenlik protokollerine sahip nesne yönelimli bir masaüstü bankacılık uygulamasıdır. 

Uygulama, kullanıcı dostu şık tasarımı, dinamik karakter sayaçları ve özel **"Panik Modu (Acil Durum Şifresi)"** mimarisi ile hem estetiği hem de güvenliği ön planda tutmaktadır.

---

## 🚀 Öne Çıkan Özellikler

* **🛡️ Gelişmiş Güvenlik ve Panik Modu:** Kullanıcı şifresinin tam tersi girildiğinde sistem otomatik olarak acil durum (bakım modu) protokolünü devreye sokar, işlemleri askıya alır ve `ERR_CONNECTION_REFUSED_503` uyarısıyla sistemi güvenli şekilde kapatır.
* **🎨 Modern ve Şık Arayüz:** Koyu lacivert ve siyah geçişli (`GradientPaint`) şeffaf kart tasarımları, siber punk esintili neon detaylar ve akıcı buton animasyonları.
* **⚡ Dinamik Kontroller (Canlı Sayaçlar):** TC Kimlik, Telefon, Şifre ve IBAN alanlarında veri girişini anlık olarak kısıtlayan ve karakter sayısını gösteren akıllı sayaç yapısı (`KeyAdapter`).
* **💾 Dosya Tabanlı Veritabanı:** Kullanıcı kayıtları ve kimlik doğrulama işlemleri nesne yönelimli olarak `kullanicilar.txt` not defteri üzerinden satır satır taranarak işlenir.
* **🌘 Ultra Hafif Gece Modu Slider'ı:** Ayarlar panelinde yer alan `JSlider` ve `GlassPane` entegrasyonu sayesinde ekran parlaklığını canlı olarak kısma simülasyonu.

---

## 🛠️ Kullanılan Teknolojiler ve Mimari

* **Dil:** Java (JDK 8 veya üzeri)
* **Arayüz Teknolojisi:** Java Swing & AWT (`JFrame`, `JPanel`, `CardLayout`, `Graphics2D`, `JPasswordField`)
* **Dosya Yönetimi (I/O):** `BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter` (Veri persistence yapısı için)
* **Tasarım Desenleri ve Temel İlkeler:** * **Encapsulation (Kapsülleme):** Form elemanlarının güvenliği için katı `Getter/Setter` metot yapısı.
    * **Asenkron GUI Yönetimi:** Arayüzün thread-safe çalışması için `SwingUtilities.invokeLater` kullanımı.

---

## 📁 Proje Dosya Yapısı (Arayüz Paketi)

* `Main.java`: Uygulamanın ana giriş kapısı. Thread yönetimini başlatır.
* `LoginEkrani.java`: Kullanıcı adı ve şifre doğrulama alanı. Akıllı sayaç korumalı arayüz.
* `KayitEkrani.java`: Rakam korumalı ve 11 hane sınırlandırmalı yeni kullanıcı kayıt formu.
* `Guvenlik.java`: Veritabanı (txt) taramalarını, şifre eşleştirmelerini ve Panik Modu mantıksal algoritmasını yöneten çekirdek sınıf.
* `MenuEkrani.java`: Hesap özeti, para çekme/yatırma, EFT/Havale transferleri ve gece modu slider'ının yer aldığı ana yönetim paneli (`CardLayout` mimarisi).

---



