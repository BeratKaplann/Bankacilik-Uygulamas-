package Arayüz;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Swing arayüz işlemlerinin (GUI) güvenli ve doğru bir iş parçacığında (thread) 
        // çalışması için invokeLater kullandık.
        SwingUtilities.invokeLater(() -> {
            
            // Uygulamanın ana giriş kapısı olan LoginEkrani'ni çağırıyoruz
            LoginEkrani baslangicEkrani = new LoginEkrani();
            baslangicEkrani.setVisible(true);
            
        });
    }
}