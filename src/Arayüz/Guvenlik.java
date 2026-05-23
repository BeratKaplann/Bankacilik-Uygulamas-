package Arayüz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Guvenlik {
    
    // Varsayılan Admin hesabı yine dursun (Yedek olarak)
    private static final String VARSAYILAN_KULLANICI = "Admin";
    private static final String VARSAYILAN_SIFRE = "1453";

    /**
     * Kullanıcı girişini not defterinden kontrol eder.
     * @return 1: Başarılı Giriş, 2: Acil Durum Kodu, 0: Hatalı Giriş
     */
    public static int girisKontrolEt(String girilenKullanici, String girilenSifre) {
        
        // 1. Önce Varsayılan Admin Kontrolü
        String varsayilanAcilSifre = new StringBuilder(VARSAYILAN_SIFRE).reverse().toString(); // "3541"
        if (girilenKullanici.equals(VARSAYILAN_KULLANICI)) {
            if (girilenSifre.equals(VARSAYILAN_SIFRE)) return 1;
            if (girilenSifre.equals(varsayilanAcilSifre)) return 2;
        }

        // 2. Not Defterindeki (`kullanicilar.txt`) Kayıtları Tarama
        // Not: Kayıt olurken "Ad Soyad" olarak kaydediyoruz. Giriş yaparken kolaylık olsun diye
        // ad alanına girilen metni dosyadaki kayıtlarla eşleştirmeye çalışıyoruz.
        try (FileReader dosyaOkuyucu = new FileReader("kullanicilar.txt");
             BufferedReader tamponOkuyucu = new BufferedReader(dosyaOkuyucu)) {
            
            String satir;
            String mevcutAdSoyad = "";
            String mevcutSifre = "";

            // Dosyayı satır satır sonuna kadar oku
            while ((satir = tamponOkuyucu.readLine()) != null) {
                
                if (satir.startsWith("Ad Soyad: ")) {
                    mevcutAdSoyad = satir.replace("Ad Soyad: ", "").trim();
                } 
                else if (satir.startsWith("Şifre: ")) {
                    mevcutSifre = satir.replace("Şifre: ", "").trim();
                }
                else if (satir.startsWith("----------------------------")) {
                    // Bir kullanıcının verisi bittiğinde, girilen bilgilerle eşleşiyor mu bak
                    // Kullanıcı sadece adını girerek de giriş yapabilsin diye "startsWith" veya "contains" kullanılabilir
                    if (!mevcutAdSoyad.isEmpty() && !mevcutSifre.isEmpty()) {
                        
                        // Acil durum (Panik) şifresi (Dosyadaki şifrenin tersi)
                        String acilDurumSifresi = new StringBuilder(mevcutSifre).reverse().toString();

                        // Giriş kontrolü (Kullanıcı adı Ad Soyad içinde geçiyor mu ve şifre doğru mu?)
                        if (mevcutAdSoyad.toLowerCase().contains(girilenKullanici.toLowerCase()) && girilenSifre.equals(mevcutSifre)) {
                            return 1; // Başarılı giriş
                        }
                        // Acil durum şifresi kontrolü
                        else if (mevcutAdSoyad.toLowerCase().contains(girilenKullanici.toLowerCase()) && girilenSifre.equals(acilDurumSifresi)) {
                            return 2; // Acil durum şifresi aktif
                        }
                    }
                    // Yeni kullanıcı bloğuna geçmeden önce geçici değişkenleri sıfırla
                    mevcutAdSoyad = "";
                    mevcutSifre = "";
                }
            }

        } catch (IOException e) {
            // Eğer henüz hiç kayıt yapılmadıysa dosya bulunamayabilir, konsola yazdırıp geçiyoruz
            System.out.println("Sistem uyarısı: Kullanıcı veritabanı dosyası henüz okunamadı.");
        }

        return 0; // Hiçbir kayıtla eşleşmediyse erişim engellenir
    }
}