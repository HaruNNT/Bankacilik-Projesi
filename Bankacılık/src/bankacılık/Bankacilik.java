package bankacilik;

import java.util.Scanner;
import java.io.*;

public class Bankacilik {

    static int MAX_HESAP = 10;

    static int[] hesapNo = new int[MAX_HESAP];
    static String[] sifre = new String[MAX_HESAP];
    static String[] ad = new String[MAX_HESAP];
    static String[] soyad = new String[MAX_HESAP];
    static double[] bakiye = new double[MAX_HESAP];

    static int hesapSayisi = 0;
    static int aktifHesap = -1;

    static String DOSYA_YOLU = "data/hesaplar.txt";

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        dosyadanOku();

        int secim;

        do {
            anaMenu();
            secim = input.nextInt();
            input.nextLine();

            switch (secim) {
                case 1:
                    hesapOlustur();
                    break;
                case 2:
                    girisYap();
                    break;
                case 3:
                    System.out.println("Programdan çýkýlýyor.");
                    break;
                default:
                    System.out.println("Hatalý seçim!");
            }

        } while (secim != 3);
    }

    static void anaMenu() {
        System.out.println("\n--- ANA MENÜ ---");
        System.out.println("1- Hesap Oluþtur");
        System.out.println("2- Giriþ Yap");
        System.out.println("3- Çýkýþ");
        System.out.print("Seçiminiz: ");
    }

    static void hesapOlustur() {

        if (hesapSayisi >= MAX_HESAP) {
            System.out.println("Maksimum hesap sayýsýna ulaþýldý.");
            return;
        }

        System.out.print("Hesap No: ");
        int yeniNo = input.nextInt();
        input.nextLine();

        for (int i = 0; i < hesapSayisi; i++) {
            if (hesapNo[i] == yeniNo) {
                System.out.println("Bu hesap numarasý zaten var!");
                return;
            }
        }

        hesapNo[hesapSayisi] = yeniNo;

        System.out.print("Þifre: ");
        sifre[hesapSayisi] = input.nextLine();

        System.out.print("Ad: ");
        ad[hesapSayisi] = input.nextLine();

        System.out.print("Soyad: ");
        soyad[hesapSayisi] = input.nextLine();

        System.out.print("Baþlangýç Bakiyesi: ");
        bakiye[hesapSayisi] = input.nextDouble();
        input.nextLine();

        hesapSayisi++;
        dosyayaYaz();

        System.out.println("Hesap baþarýyla oluþturuldu.");
    }

    static void girisYap() {

        System.out.print("Hesap No: ");
        int no = input.nextInt();
        input.nextLine();

        System.out.print("Þifre: ");
        String sf = input.nextLine();

        for (int i = 0; i < hesapSayisi; i++) {
            if (hesapNo[i] == no && sifre[i].equals(sf)) {
                aktifHesap = i;
                kullaniciMenu();
                return;
            }
        }

        System.out.println("Hatalý hesap no veya þifre!");
    }

    static void kullaniciMenu() {

        int secim;

        do {
            System.out.println("\n--- KULLANICI MENÜSÜ ---");
            System.out.println("1- Para Yatýr");
            System.out.println("2- Para Çek");
            System.out.println("3- Bakiye Sorgula");
            System.out.println("4- Hesap Sil");
            System.out.println("5- Çýkýþ Yap");
            System.out.print("Seçim: ");

            secim = input.nextInt();
            input.nextLine();

            switch (secim) {
                case 1:
                    paraYatir();
                    break;
                case 2:
                    paraCek();
                    break;
                case 3:
                    bakiyeSorgula();
                    break;
                case 4:
                    hesapSil();
                    return;
                case 5:
                    aktifHesap = -1;
                    System.out.println("Çýkýþ yapýldý.");
                    break;
                default:
                    System.out.println("Hatalý seçim!");
            }

        } while (secim != 5);
    }

    static void paraYatir() {

        System.out.print("Yatýrýlacak tutar: ");
        double tutar = input.nextDouble();

        if (tutar > 0) {
            bakiye[aktifHesap] += tutar;
            dosyayaYaz();
            System.out.println("Yeni bakiye: " + bakiye[aktifHesap]);
        } else {
            System.out.println("Geçersiz tutar!");
        }
    }

    static void paraCek() {

        System.out.print("Çekilecek tutar: ");
        double tutar = input.nextDouble();

        if (tutar > bakiye[aktifHesap]) {
            System.out.println("Yetersiz bakiye!");
        } else if (tutar <= 0) {
            System.out.println("Geçersiz tutar!");
        } else {
            bakiye[aktifHesap] -= tutar;
            dosyayaYaz();
            System.out.println("Yeni bakiye: " + bakiye[aktifHesap]);
        }
    }

    static void bakiyeSorgula() {

        System.out.println("\n--- HESAP BÝLGÝLERÝ ---");
        System.out.println("Hesap No: " + hesapNo[aktifHesap]);
        System.out.println("Ad Soyad: " + ad[aktifHesap] + " " + soyad[aktifHesap]);
        System.out.println("Bakiye: " + bakiye[aktifHesap]);
    }

    static void hesapSil() {

        if (bakiye[aktifHesap] != 0) {
            System.out.println("Bakiye sýfýr olmadan hesap silinemez!");
            return;
        }

        for (int i = aktifHesap; i < hesapSayisi - 1; i++) {
            hesapNo[i] = hesapNo[i + 1];
            sifre[i] = sifre[i + 1];
            ad[i] = ad[i + 1];
            soyad[i] = soyad[i + 1];
            bakiye[i] = bakiye[i + 1];
        }

        hesapSayisi--;
        aktifHesap = -1;
        dosyayaYaz();

        System.out.println("Hesap silindi.");
    }

    static void dosyadanOku() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(DOSYA_YOLU));
            String satir;

            while ((satir = br.readLine()) != null) {
                String[] p = satir.split(";");

                hesapNo[hesapSayisi] = Integer.parseInt(p[0]);
                sifre[hesapSayisi] = p[1];
                ad[hesapSayisi] = p[2];
                soyad[hesapSayisi] = p[3];
                bakiye[hesapSayisi] = Double.parseDouble(p[4]);

                hesapSayisi++;
            }

            br.close();
        } catch (Exception e) {
            System.out.println("Dosya okunamadý.");
        }
    }

    static void dosyayaYaz() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(DOSYA_YOLU));

            for (int i = 0; i < hesapSayisi; i++) {
                bw.write(
                    hesapNo[i] + ";" +
                    sifre[i] + ";" +
                    ad[i] + ";" +
                    soyad[i] + ";" +
                    bakiye[i]
                );
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            System.out.println("Dosya yazýlamadý.");
        }
    }
}
