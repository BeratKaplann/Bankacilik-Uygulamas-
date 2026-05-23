package Arayüz;

import javax.swing.*;
import java.awt.*;

public class MenuEkrani extends JFrame {
    // --- DEĞİŞKENLER ---
    private double bakiye = 5450.0; // Kullanıcının mevcut banka bakiyesi
    private JPanel yanMenu, anaIcerik; // Ana düzen bileşenleri
    private JLabel lblBakiye; // Bakiyenin dinamik olarak güncellendiği etiket
    private CardLayout kartYapisi = new CardLayout(); // Paneller arası geçişi sağlayan düzenleyici
    
    // Ekranın üzerine binen karartma efekti (Gece modu için)
    private JPanel parlaklikPerdesi;

    public MenuEkrani() {
        // --- ANA PENCERE AYARLARI ---
        setTitle("Ç.A.N.K.I.R.I B.A.N.K Yönetim");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında başlat
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tam ekran modu
        setLayout(null); // Mutlak konumlandırma kullanıyoruz

        // --- GLASS PANE (PARLAKLIK PERDESİ) ---
        parlaklikPerdesi = new JPanel();
        parlaklikPerdesi.setBounds(0, 0, 1920, 1080);
        parlaklikPerdesi.setBackground(new Color(0, 0, 0, 0)); // Başlangıçta tamamen şeffaf
        parlaklikPerdesi.setOpaque(false);
        parlaklikPerdesi.setFocusable(false); // Tıklamaları engellememesi için
        parlaklikPerdesi.setEnabled(false);
        getLayeredPane().add(parlaklikPerdesi, JLayeredPane.DRAG_LAYER); // En üst katmana ekle

        // --- ARKA PLAN PANELİ ---
        JPanel arkaPlan = new JPanel();
        arkaPlan.setBackground(new Color(10, 25, 47)); // Koyu lacivert tema
        arkaPlan.setBounds(0, 0, 1920, 1080);
        arkaPlan.setLayout(null);
        add(arkaPlan);

        // --- 1. SOL YAN MENÜ ---
        yanMenu = new JPanel();
        yanMenu.setBackground(new Color(2, 6, 23)); // Daha koyu yan menü rengi
        yanMenu.setBounds(0, 0, 350, 1080);
        yanMenu.setLayout(null);
        arkaPlan.add(yanMenu);

        // Menü Başlığı/Logo
        JLabel lblLogo = new JLabel("CONTROLS", SwingConstants.CENTER);
        lblLogo.setForeground(Color.CYAN);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLogo.setBounds(0, 50, 350, 40);
        yanMenu.add(lblLogo);

        // --- 2. ANA İÇERİK ALANI (CardLayout) ---
        anaIcerik = new JPanel(kartYapisi); 
        anaIcerik.setOpaque(false);
        anaIcerik.setBounds(400, 50, 1450, 950);
        anaIcerik.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100, 50)));
        arkaPlan.add(anaIcerik);

        // İşlem panellerini CardLayout'a isim vererek ekliyoruz
        anaIcerik.add(createOzetPanel(), "OZET");
        anaIcerik.add(createYatirPanel(), "YATIR");
        anaIcerik.add(createCekPanel(), "CEK");
        anaIcerik.add(createTransferPanel(), "TRANSFER");
        anaIcerik.add(createAyarlarPanel(), "AYARLAR");

        // --- SOL MENÜ BUTONLARINI OLUŞTURMA ---
        String[] butonlar = {"HESAP ÖZETİ", "PARA YATIR", "PARA ÇEK", "PARA TRANSFERİ", "AYARLAR", "GÜVENLİ ÇIKIŞ"};
        int yPos = 200;
        for (String isim : butonlar) {
            JButton btn = new JButton(isim);
            btn.setBounds(25, yPos, 300, 50);
            styleSideButton(btn); // Butonlara görsel stil uygula
            yanMenu.add(btn);
            yPos += 70;

            // Buton tıklama olayları (Paneller arası geçiş)
            btn.addActionListener(e -> {
                if (isim.equals("HESAP ÖZETİ")) kartYapisi.show(anaIcerik, "OZET");
                else if (isim.equals("PARA YATIR")) kartYapisi.show(anaIcerik, "YATIR");
                else if (isim.equals("PARA ÇEK")) kartYapisi.show(anaIcerik, "CEK");
                else if (isim.equals("PARA TRANSFERİ")) kartYapisi.show(anaIcerik, "TRANSFER");
                else if (isim.equals("AYARLAR")) kartYapisi.show(anaIcerik, "AYARLAR");
                else if (isim.equals("GÜVENLİ ÇIKIŞ")) {
                    this.dispose(); // Mevcut ekranı kapat
                    new LoginEkrani().setVisible(true); // Giriş ekranına dön
                }
            });
        }
    }

    // --- AYARLAR PANELİ OLUŞTURUCU ---
    private JPanel createAyarlarPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);

        JLabel lblBaslik = new JLabel("SİSTEM AYARLARI");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblBaslik.setForeground(Color.LIGHT_GRAY);
        lblBaslik.setBounds(50, 40, 400, 50);
        panel.add(lblBaslik);

        int x = 50, y = 120, width = 400;

        // Kullanıcı Adı Değiştirme Alanı
        JLabel lblKullanici = new JLabel("Görünen Adı Değiştir:");
        lblKullanici.setForeground(Color.GRAY);
        lblKullanici.setBounds(x, y, width, 25);
        panel.add(lblKullanici);

        JTextField txtYeniAd = new JTextField("Admin");
        txtYeniAd.setBounds(x, y + 30, width, 40);
        styleTextField(txtYeniAd);
        panel.add(txtYeniAd);

        // Gece Modu Slider'ı (Parlaklık Ayarı)
        JLabel lblParlaklik = new JLabel("Gece Modu (Ultra Hafif Geçiş):");
        lblParlaklik.setForeground(Color.GRAY);
        lblParlaklik.setBounds(x, y + 90, width, 25);
        panel.add(lblParlaklik);

        JSlider sliderParlaklik = new JSlider(0, 100, 0); 
        sliderParlaklik.setBounds(x, y + 120, width, 40);
        sliderParlaklik.setOpaque(false);

        // Karartma için özel GlassPane bileşeni
        JPanel parlaklikPerdesi = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(getBackground()); 
                g.fillRect(0, 0, getWidth(), getHeight()); 
            }
        };
        parlaklikPerdesi.setOpaque(false);
        parlaklikPerdesi.setBackground(new Color(0, 0, 0, 0));
        this.setGlassPane(parlaklikPerdesi);
        parlaklikPerdesi.setVisible(true);
        
        sliderParlaklik.addChangeListener(e -> {
            int val = sliderParlaklik.getValue();
            int hafifAlpha = val * 2;
            
            if (val < 10) { 
                parlaklikPerdesi.setBackground(new Color(0, 0, 0, 0));
            } else {
                parlaklikPerdesi.setBackground(new Color(0, 0, 0, hafifAlpha));
            }
            parlaklikPerdesi.repaint();
        });
        panel.add(sliderParlaklik);

        // --- BEYAZ AYARLARI ONAYLA BUTONU ---
        JButton btnKaydet = new JButton("ONAYLA");
        btnKaydet.setBounds(x, y + 190, width, 60);
        btnKaydet.setBackground(Color.WHITE); 
        btnKaydet.setForeground(new Color(10, 25, 47)); 
        btnKaydet.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnKaydet.setFocusPainted(false);
        btnKaydet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKaydet.addActionListener(e -> JOptionPane.showMessageDialog(this, "Değişiklikler başarıyla kaydedildi.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE));
        panel.add(btnKaydet);
        
        // Görsel Terminal Dekorasyonu
        JPanel infoPanel = new JPanel(null);
        infoPanel.setBackground(new Color(20, 20, 20, 180));
        infoPanel.setBounds(500, 120, 400, 200);
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), "Terminal Status", 0, 0, null, Color.CYAN));
        panel.add(infoPanel);

        String[] stats = {"Çankırı Bank Ortağı", "> Terminal: Karatekin MühendislikFakültesi", "> Connection: Secure SSL", "> Location: Çankırı/Karatekin"};
        int infoY = 30;
        for (String s : stats) {
            JLabel l = new JLabel(s);
            l.setForeground(Color.GREEN);
            l.setFont(new Font("Monospaced", Font.PLAIN, 14));
            l.setBounds(20, infoY, 350, 25);
            infoPanel.add(l);
            infoY += 30;
        }
        return panel;
    }

    // --- HESAP ÖZETİ PANELİ ---
    private JPanel createOzetPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        JLabel lblAd = new JLabel("HOŞ GELDİNİZ, Admin ");
        lblAd.setForeground(Color.WHITE);
        lblAd.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblAd.setBounds(50, 50, 600, 50);
        panel.add(lblAd);

        JPanel bakiyeKart = new JPanel(null);
        bakiyeKart.setBackground(new Color(30, 144, 255, 40));
        bakiyeKart.setBounds(50, 150, 400, 200);
        bakiyeKart.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
        panel.add(bakiyeKart);

        JLabel lblBaslikBakiye = new JLabel("MEVCUT BAKİYE");
        lblBaslikBakiye.setForeground(Color.LIGHT_GRAY);
        lblBaslikBakiye.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblBaslikBakiye.setBounds(20, 20, 200, 30);
        bakiyeKart.add(lblBaslikBakiye);

        lblBakiye = new JLabel("₺ " + String.format("%.2f", bakiye));
        lblBakiye.setForeground(Color.WHITE);
        lblBakiye.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblBakiye.setBounds(20, 70, 350, 60);
        bakiyeKart.add(lblBakiye);
        return panel;
    }

    // --- PARA TRANSFER PANELİ ---
    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        JLabel lblBaslik = new JLabel("PARA TRANSFERİ (EFT/HAVALE)");
        lblBaslik.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblBaslik.setForeground(new Color(52, 152, 219)); 
        lblBaslik.setBounds(50, 40, 600, 50);
        panel.add(lblBaslik);

        int x = 50, y = 120, width = 400, height = 40;
        
        JLabel lblIban = new JLabel("Alıcı IBAN:");
        lblIban.setForeground(Color.LIGHT_GRAY);
        lblIban.setBounds(x, y, width, 25);
        panel.add(lblIban);

        JTextField txtIban = new JTextField("TR");
        txtIban.setBounds(x, y + 30, width, height);
        styleTextField(txtIban);
        panel.add(txtIban);

        // --- DİNAMİK IBAN KARAKTER SAYACI ---
        JLabel lblIbanSayac = new JLabel("2/26"); 
        lblIbanSayac.setForeground(Color.GRAY);
        lblIbanSayac.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblIbanSayac.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIbanSayac.setBounds(x + width - 60, y + 75, 60, 20); 
        panel.add(lblIbanSayac);

        txtIban.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String icerik = txtIban.getText();
                if (icerik.length() > 26) {
                    icerik = icerik.substring(0, 26);
                    txtIban.setText(icerik);
                }
                lblIbanSayac.setText(icerik.length() + "/26");
            }
        });

        JLabel lblAdSoyad = new JLabel("Alıcı Adı Soyadı:");
        lblAdSoyad.setForeground(Color.LIGHT_GRAY);
        lblAdSoyad.setBounds(x, y + 90, width, 25);
        panel.add(lblAdSoyad);

        JTextField txtAdSoyad = new JTextField();
        txtAdSoyad.setBounds(x, y + 120, width, height);
        styleTextField(txtAdSoyad);
        panel.add(txtAdSoyad);

        JLabel lblTutar = new JLabel("Gönderilecek Tutar (₺):");
        lblTutar.setForeground(Color.LIGHT_GRAY);
        lblTutar.setBounds(x, y + 180, width, 25);
        panel.add(lblTutar);

        JTextField txtTransferTutar = new JTextField();
        txtTransferTutar.setBounds(x, y + 210, width, height);
        styleTextField(txtTransferTutar);
        panel.add(txtTransferTutar);

        // --- BEYAZ "KAYDET" BUTONU ---
        JButton btnGonder = new JButton("KAYDET");
        btnGonder.setBounds(x, y + 280, width, 60);
        btnGonder.setBackground(Color.WHITE); 
        btnGonder.setForeground(new Color(10, 25, 47)); 
        btnGonder.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnGonder.setFocusPainted(false);
        btnGonder.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnGonder.addActionListener(e -> {
            try {
                double miktar = Double.parseDouble(txtTransferTutar.getText());
                if (bakiye >= miktar) {
                    bakiyeGuncelle(-miktar);
                    JOptionPane.showMessageDialog(this, txtAdSoyad.getText() + " kişisine " + miktar + " ₺ başarıyla gönderildi.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE);
                    txtTransferTutar.setText(""); 
                    txtAdSoyad.setText(""); 
                    txtIban.setText("TR");
                    lblIbanSayac.setText("2/26"); 
                } else {
                    JOptionPane.showMessageDialog(this, "Yetersiz Bakiye!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Geçersiz tutar!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE); 
            }
        });
        panel.add(btnGonder);
        return panel;
    }

    // --- PARA YATIRMA PANELİ ---
    private JPanel createYatirPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        JLabel lbl = new JLabel("PARA YATIRMA");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lbl.setForeground(new Color(46, 204, 113));
        lbl.setBounds(50, 50, 400, 50);
        panel.add(lbl);

        int[] miktarlar = {100, 200, 500, 1000, 2000, 5000};
        int xPos = 50, yPos = 130;
        for (int i = 0; i < miktarlar.length; i++) {
            final int miktar = miktarlar[i]; 
            JButton btnMiktar = new JButton(miktar + " ₺");
            btnMiktar.setBounds(xPos, yPos, 100, 40);
            btnMiktar.setContentAreaFilled(false);
            btnMiktar.setForeground(Color.CYAN); 
            btnMiktar.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
            btnMiktar.addActionListener(e -> {
                bakiyeGuncelle(miktar);
                JOptionPane.showMessageDialog(this, miktar + " ₺ başarıyla yatırıldı.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE);
            });
            panel.add(btnMiktar);
            xPos += 110;
            if ((i + 1) % 3 == 0) { xPos = 50; yPos += 50; } 
        }

        JTextField txtYatirTutar = new JTextField();
        txtYatirTutar.setBounds(50, 290, 300, 45);
        styleTextField(txtYatirTutar);
        panel.add(txtYatirTutar);

        // --- BEYAZ PARA YATIRMA ONAY BUTONU ---
        JButton btnOnay = new JButton("ONAYLA");
        btnOnay.setBounds(50, 360, 300, 60);
        btnOnay.setBackground(Color.WHITE); 
        btnOnay.setForeground(new Color(10, 25, 47)); 
        btnOnay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnOnay.setFocusPainted(false);
        btnOnay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOnay.addActionListener(e -> {
            try {
                double miktar = Double.parseDouble(txtYatirTutar.getText());
                bakiyeGuncelle(miktar);
                JOptionPane.showMessageDialog(this, miktar + " ₺ başarıyla yatırıldı.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE);
                txtYatirTutar.setText("");
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Hata: Lütfen geçerli sayısal bir değer girin!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE); 
            }
        });
        panel.add(btnOnay);
        return panel;
    }

    // --- PARA ÇEKME PANELİ ---
    private JPanel createCekPanel() {
        JPanel panel = new JPanel(null);
        panel.setOpaque(false);
        JLabel lbl = new JLabel("PARA ÇEKME");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lbl.setForeground(new Color(255, 165, 0));
        lbl.setBounds(50, 50, 400, 50);
        panel.add(lbl);

        int[] miktarlar = {20, 50, 100, 200, 500, 1000};
        int xPos = 50, yPos = 130;
        for (int i = 0; i < miktarlar.length; i++) {
            final int miktar = miktarlar[i]; 
            JButton btnMiktar = new JButton(miktar + " ₺");
            btnMiktar.setBounds(xPos, yPos, 100, 40);
            btnMiktar.setContentAreaFilled(false);
            btnMiktar.setForeground(Color.ORANGE);
            btnMiktar.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
            btnMiktar.addActionListener(e -> {
                if(bakiye >= miktar) { 
                    bakiyeGuncelle(-miktar); 
                    JOptionPane.showMessageDialog(this, miktar + " ₺ başarıyla çekildi.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE); 
                } else {
                    JOptionPane.showMessageDialog(this, "Yetersiz bakiye!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE);
                }
            });
            panel.add(btnMiktar);
            xPos += 110;
            if ((i + 1) % 3 == 0) { xPos = 50; yPos += 50; }
        }

        JLabel lblOzel = new JLabel("Farklı Bir Tutar Girin:");
        lblOzel.setForeground(Color.WHITE);
        lblOzel.setBounds(50, 250, 300, 30);
        panel.add(lblOzel);

        JTextField txtCekTutar = new JTextField();
        txtCekTutar.setBounds(50, 290, 300, 45);
        styleTextField(txtCekTutar);
        panel.add(txtCekTutar);

        // --- BEYAZ PARA ÇEKME ONAY BUTONU ---
        JButton btnOnay = new JButton("ONAYLA");
        btnOnay.setBounds(50, 360, 300, 60);
        btnOnay.setBackground(Color.WHITE); 
        btnOnay.setForeground(new Color(10, 25, 47)); 
        btnOnay.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnOnay.setFocusPainted(false);
        btnOnay.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOnay.addActionListener(e -> {
            try {
                double m = Double.parseDouble(txtCekTutar.getText());
                if(bakiye >= m) { 
                    bakiyeGuncelle(-m); 
                    JOptionPane.showMessageDialog(this, m + " ₺ başarıyla çekildi.", "BİLDİRİM", JOptionPane.INFORMATION_MESSAGE);
                    txtCekTutar.setText(""); 
                } else {
                    JOptionPane.showMessageDialog(this, "Yetersiz bakiye!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(this, "Hata: Geçersiz tutar!", "BİLDİRİM", JOptionPane.ERROR_MESSAGE); 
            }
        });
        panel.add(btnOnay);
        return panel;
    }

    // --- YARDIMCI METOTLAR (STİL VE MANTIK) ---

    private void styleTextField(JTextField tf) {
        tf.setBackground(new Color(30, 30, 30));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
    }

    private void styleSideButton(JButton btn) {
        btn.setBackground(new Color(2, 6, 23));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50, 50, 50)));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 20, 0, 0));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(30, 144, 255)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { btn.setBackground(new Color(2, 6, 23)); }
        });
    }

    private void bakiyeGuncelle(double miktar) {
        bakiye += miktar;
        if (lblBakiye != null) lblBakiye.setText("₺ " + String.format("%.2f", bakiye));
    }
}