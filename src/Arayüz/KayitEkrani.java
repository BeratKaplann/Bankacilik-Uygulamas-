package Arayüz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class KayitEkrani extends JFrame {
    private JTextField txtAd, txtSoyad, txtTC, txtTel;
    private JPasswordField txtSifre, txtSifreTekrar;
    private JButton btnKayitOl, btnGeri;

    public KayitEkrani() {
        setTitle("Ç.A.N.K.I.R.I Bank - Yeni Kayıt");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 1. Arka Plan (Login ile aynı Gradient tema)
        JPanel anaPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 25, 47), 0, getHeight(), new Color(2, 6, 23));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        anaPanel.setLayout(null);

        // 2. Kayıt Kartı
        JPanel kartPanel = new JPanel();
        kartPanel.setBackground(new Color(255, 255, 255, 15)); 
        kartPanel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100, 50), 1));
        kartPanel.setLayout(null);
        kartPanel.setBounds(525, 60, 525, 820); 
        anaPanel.add(kartPanel);

        // --- Başlık ---
        JLabel lblBaslik = new JLabel("YENİ KAYIT", SwingConstants.CENTER);
        lblBaslik.setForeground(Color.CYAN);
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBaslik.setBounds(0, 30, 525, 40);
        kartPanel.add(lblBaslik);

        // --- Form Elemanlarını Yerleştirme ---
        int baslangicY = 80;
        int aralik = 95; 

        // Ad
        olusturFormElemani(kartPanel, "Adınız:", txtAd = new JTextField(), baslangicY);
        // Soyad
        olusturFormElemani(kartPanel, "Soyadınız:", txtSoyad = new JTextField(), baslangicY + aralik);
        // TC (Sadece rakam, maks 11 hane ve canlı sayaçlı)
        olusturFormElemani(kartPanel, "TC Kimlik No:", txtTC = new JTextField(), baslangicY + (aralik * 2));
        // Telefon (Sadece rakam, maks 11 hane ve canlı sayaçlı)
        olusturFormElemani(kartPanel, "Telefon:", txtTel = new JTextField(), baslangicY + (aralik * 3));
        // Şifre
        olusturFormElemani(kartPanel, "Şifre Belirleyin: (!Tersi güvenlik şifrenizdir !)", txtSifre = new JPasswordField(), baslangicY + (aralik * 4));
        // Şifre Tekrar
        olusturFormElemani(kartPanel, "Şifre Tekrar:", txtSifreTekrar = new JPasswordField(), baslangicY + (aralik * 5));

        // --- Butonlar ---
        btnKayitOl = new JButton("KAYDI TAMAMLA");
        btnKayitOl.setBounds(112, 670, 300, 50);
        styleButton(btnKayitOl, new Color(46, 204, 113)); 
        btnKayitOl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kartPanel.add(btnKayitOl);
    
        btnKayitOl.addActionListener(e -> {
            String ad = txtAd.getText();
            String soyad = txtSoyad.getText();
            String tc = txtTC.getText();
            String tel = txtTel.getText();
            String sifre = new String(txtSifre.getPassword());
            String sifreTekrar = new String(txtSifreTekrar.getPassword());

            if (ad.isEmpty() || soyad.isEmpty() || tc.isEmpty() || tel.isEmpty() || sifre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "BİLDİRİM", JOptionPane.WARNING_MESSAGE);
            } else if (!sifre.equals(sifreTekrar)) {
                JOptionPane.showMessageDialog(this, "Şifreler birbiriyle eşleşmiyor!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE);
            } else if (tc.length() != 11) {
                JOptionPane.showMessageDialog(this, "TC Kimlik Numarası 11 hane olmalıdır!", "BİLDİRİM", JOptionPane.WARNING_MESSAGE);
            } else if (tel.length() != 11) {
                JOptionPane.showMessageDialog(this, "Telefon Numarası 11 hane olmalıdır!", "BİLDİRİM", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    FileWriter dosyaYazici = new FileWriter("kullanicilar.txt", true);
                    BufferedWriter tamponYazici = new BufferedWriter(dosyaYazici);

                    tamponYazici.write("=== YENİ KULLANICI KAYDI ===");
                    tamponYazici.newLine();
                    tamponYazici.write("Ad Soyad: " + ad + " " + soyad);
                    tamponYazici.newLine();
                    tamponYazici.write("TC Kimlik: " + tc);
                    tamponYazici.newLine();
                    tamponYazici.write("Telefon: " + tel);
                    tamponYazici.newLine();
                    tamponYazici.write("Şifre: " + sifre);
                    tamponYazici.newLine();
                    tamponYazici.write("----------------------------");
                    tamponYazici.newLine();

                    tamponYazici.close(); 
                } catch (IOException ex) {
                    System.err.println("Not defterine yazılırken bir hata oluştu: " + ex.getMessage());
                }

                JOptionPane.showMessageDialog(this, "Tebrikler " + ad + "!\nKaydınız başarıyla tamamlandı ve veritabanına işlendi. Giriş ekranına aktarılıyorsunuz.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); 
                new LoginEkrani().setVisible(true); 
            }
        });
        
        btnGeri = new JButton("Giriş Ekranına Dön");
        btnGeri.setBounds(112, 735, 300, 30);
        btnGeri.setForeground(Color.LIGHT_GRAY);
        btnGeri.setContentAreaFilled(false);
        btnGeri.setBorderPainted(false);
        btnGeri.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnGeri.addActionListener(e -> {
            this.dispose(); 
            new LoginEkrani().setVisible(true); 
        });
        kartPanel.add(btnGeri);

        add(anaPanel);
    }

    private void olusturFormElemani(JPanel panel, String etiket, JTextField field, int y) {
        JLabel lbl = new JLabel(etiket);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setBounds(112, y, 300, 20);
        panel.add(lbl);

        field.setBounds(112, y + 25, 300, 35);
        field.setBackground(new Color(30, 30, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        panel.add(field);

        // --- ŞİFRE ALANLARI İÇİN DİNAMİK SAYAÇ VE SINIRLANDIRICI ---
        if (field instanceof JPasswordField) {
            JPasswordField passwordField = (JPasswordField) field;
            
            JLabel lblSayac = new JLabel("0/4");
            lblSayac.setForeground(Color.GRAY);
            lblSayac.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblSayac.setHorizontalAlignment(SwingConstants.RIGHT);
            lblSayac.setBounds(352, y + 62, 60, 15); 
            panel.add(lblSayac);

            passwordField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String icerik = new String(passwordField.getPassword());
                    if (icerik.length() > 4) {
                        icerik = icerik.substring(0, 4);
                        passwordField.setText(icerik);
                    }
                    lblSayac.setText(icerik.length() + "/4");
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    String icerik = new String(passwordField.getPassword());
                    if (icerik.length() >= 4) {
                        e.consume();
                    }
                }
            });
        }
        // --- TC KİMLİK VE TELEFON ALANI İÇİN ORTAK DİNAMİK SAYAÇ VE RAKAM KORUMASI ---
        else if (field == txtTC || field == txtTel) {
            JLabel lblSayac = new JLabel("0/11");
            lblSayac.setForeground(Color.GRAY);
            lblSayac.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblSayac.setHorizontalAlignment(SwingConstants.RIGHT);
            lblSayac.setBounds(352, y + 62, 60, 15); 
            panel.add(lblSayac);

            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String icerik = field.getText();
                    if (icerik.length() > 11) {
                        icerik = icerik.substring(0, 11);
                        field.setText(icerik);
                    }
                    lblSayac.setText(icerik.length() + "/11");
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    String icerik = field.getText();
                    char c = e.getKeyChar();
                    
                    // 1. Sadece rakam girişine izin ver (Harfleri, boşlukları ve sembolleri engeller)
                    if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                        e.consume(); // Tuş vuruşunu iptal et, kutuya yazdırma
                        return;
                    }
                    
                    // 2. Maksimum 11 karakter sınırı kontrolü
                    if (icerik.length() >= 11) {
                        e.consume(); // 11 haneden fazlasını kutuya yazdırma
                    }
                }
            });
        }
    }

    private void styleButton(JButton btn, Color renk) {
        btn.setBackground(renk);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(null);
    }

    // ==========================================
    // GETTER VE SETTER METOTLARI 
    // ==========================================

    public JTextField getTxtAd() { return txtAd; }
    public void setTxtAd(JTextField txtAd) { this.txtAd = txtAd; }
    public JTextField getTxtSoyad() { return txtSoyad; }
    public void setTxtSoyad(JTextField txtSoyad) { this.txtSoyad = txtSoyad; }
    public JTextField getTxtTC() { return txtTC; }
    public void setTxtTC(JTextField txtTC) { this.txtTC = txtTC; }
    public JTextField getTxtTel() { return txtTel; }
    public void setTxtTel(JTextField txtTel) { this.txtTel = txtTel; }
    public JPasswordField getTxtSifre() { return txtSifre; }
    public void setTxtSifre(JPasswordField txtSifre) { this.txtSifre = txtSifre; }
    public JPasswordField getTxtSifreTekrar() { return txtSifreTekrar; }
    public void setTxtSifreTekrar(JPasswordField txtSifreTekrar) { this.txtSifreTekrar = txtSifreTekrar; }
    public JButton getBtnKayitOl() { return btnKayitOl; }
    public void setBtnKayitOl(JButton btnKayitOl) { this.btnKayitOl = btnKayitOl; }
    public JButton getBtnGeri() { return btnGeri; }
    public void setBtnGeri(JButton btnGeri) { this.btnGeri = btnGeri; }
}