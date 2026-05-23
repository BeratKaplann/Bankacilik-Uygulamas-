package Arayüz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginEkrani extends JFrame {
    private JTextField txtKullaniciAdi;
    private JPasswordField txtSifre;
    private JButton btnGiris, btnKayitOl;

    public LoginEkrani() {
        setTitle("Çankırı Bankası Giriş");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran açılması için

        // 1. Arka Plan Paneli (Koyu Lacivert / Siyah Geçişi)
        JPanel anaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                // Yukarıdan aşağıya renk geçişi (Gradient)
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 25, 47), 0, getHeight(), new Color(2, 6, 23));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        anaPanel.setLayout(null);

        // 2. Orta Giriş Kartı (Bileşenleri içine alacak şık kutu)
        JPanel kartPanel = new JPanel();
        kartPanel.setBackground(new Color(255, 255, 255, 20)); // Yüzde 20 şeffaf beyaz
        kartPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100, 50), 1));
        kartPanel.setLayout(null);
        kartPanel.setBounds(565, 200, 400, 530); // Sayaç için yüksekliği hafifçe artırdık (500 -> 530)
        anaPanel.add(kartPanel);

        // --- Kart İçindeki Bileşenler ---
        Font anaFont = new Font("Segoe UI", Font.BOLD, 16);

        // Logo Alanı (Metin olarak)
        JLabel lblLogo = new JLabel("Çankırı BANK", SwingConstants.CENTER);
        lblLogo.setForeground(new Color(200, 200, 200));
        lblLogo.setFont(new Font("Segoe UI", Font.ITALIC, 28));
        lblLogo.setBounds(0, 40, 400, 40);
        kartPanel.add(lblLogo);

        // Kullanıcı Adı
        JLabel lblUser = new JLabel("Kullanıcı Adı:");
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(anaFont);
        lblUser.setBounds(50, 120, 300, 25);
        kartPanel.add(lblUser);

        txtKullaniciAdi = new JTextField();
        txtUserStyle(txtKullaniciAdi);
        txtKullaniciAdi.setBounds(50, 150, 300, 45);
        kartPanel.add(txtKullaniciAdi);

        // Şifre
        JLabel lblPass = new JLabel("Şifre:");
        lblPass.setForeground(Color.WHITE);
        lblPass.setFont(anaFont);
        lblPass.setBounds(50, 220, 300, 25);
        kartPanel.add(lblPass);

        txtSifre = new JPasswordField();
        txtUserStyle(txtSifre);
        txtSifre.setBounds(50, 250, 300, 45);
        kartPanel.add(txtSifre);

        // --- DİNAMİK ŞİFRE KARAKTER SAYACI VE SINIRLANDIRICI ---
        JLabel lblSifreSayac = new JLabel("0/4");
        lblSifreSayac.setForeground(Color.GRAY);
        lblSifreSayac.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSifreSayac.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSifreSayac.setBounds(290, 298, 60, 20); // Şifre kutusunun sağ altına hizalandı
        kartPanel.add(lblSifreSayac);

        txtSifre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String sifreIcerik = new String(txtSifre.getPassword());
                
                // 4 Karakter sınır kontrolü
                if (sifreIcerik.length() > 4) {
                    sifreIcerik = sifreIcerik.substring(0, 4);
                    txtSifre.setText(sifreIcerik);
                }
                
                // Sayacı anlık güncelle
                lblSifreSayac.setText(sifreIcerik.length() + "/4");
            }
            
            @Override
            public void keyTyped(KeyEvent e) {
                String sifreIcerik = new String(txtSifre.getPassword());
                // Kullanıcı yazarken anlık olarak 4'ü geçerse girişi tüketip engeller
                if (sifreIcerik.length() >= 4) {
                    e.consume();
                }
            }
        });

        // Giriş Butonu (Mavi ve Parlak)
        btnGiris = new JButton("SİSTEME GİR");
        btnGiris.setBounds(50, 360, 300, 50); // Sayaç eklendiği için Y koordinatı hafif aşağı kaydırıldı
        btnGiris.setBackground(new Color(30, 144, 255));
        btnGiris.setForeground(Color.WHITE);
        btnGiris.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGiris.setFocusPainted(false);
        btnGiris.setBorder(null);
        btnGiris.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        btnGiris.addActionListener(e -> {
            String ad = txtKullaniciAdi.getText();
            String sifre = new String(txtSifre.getPassword());

            // Kontrol işlemini aynı paketteki Guvenlik sınıfına devrediyoruz
            int girisDurumu = Guvenlik.girisKontrolEt(ad, sifre);

            if (girisDurumu == 1) {
                // NORMAL GİRİŞ
                JOptionPane.showMessageDialog(this, "Kimlik Doğrulandı. Çankırı Bankası'na Hoş Geldiniz.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new MenuEkrani().setVisible(true); 
            } 
            else if (girisDurumu == 2) {
                // ACİL DURUM (Bakım Modu Senaryosu)
                System.err.println(">>> GÜVENLİK PROTOKOLÜ: Acil durum şifresi girildi.");
                Object[] secenekler = {"Tamam"};
                JOptionPane.showOptionDialog(this, 
                    "Sistem şu anda planlı güncelleme ve veri tabanı bakımı aşamasındadır.\n" +
                    "Güvenliğiniz için tüm işlemler geçici olarak askıya alınmıştır.\n\n" +
                    "Hata Kodu: ERR_CONNECTION_REFUSED_503", 
                    "BİLDİRİM", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.WARNING_MESSAGE, 
                    null, secenekler, secenekler[0]);
                System.exit(0);
            } 
            else {
                // HATALAL GİRİŞ
                JOptionPane.showMessageDialog(this, "Hatalı Kullanıcı Adı veya Şifre!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        kartPanel.add(btnGiris);

        // Kayıt Ol Butonu 
        btnKayitOl = new JButton("Yeni Kaydı Oluştur");
        btnKayitOl.setBounds(50, 430, 300, 30);
        btnKayitOl.setForeground(new Color(150, 150, 150));
        btnKayitOl.setContentAreaFilled(false);
        btnKayitOl.setBorderPainted(false);
        btnKayitOl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKayitOl.addActionListener(e -> {
            this.dispose(); // Mevcut giriş ekranını kapatır
            new KayitEkrani().setVisible(true); // Kayıt ekranını açar
        });
        kartPanel.add(btnKayitOl);

        add(anaPanel);
    }

    // Input kutularına modern görünüm veren yardımcı metod
    private void txtUserStyle(JTextField field) {
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.CYAN);
        field.setCaretColor(Color.WHITE); // Yazma imleci beyaz
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }
}