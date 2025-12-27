package bankacýlýk;

import java.util.Scanner;

public class Bankacýlýk {

    
    static int hesapNo;
    static String ad;
    static String soyad;
    static double bakiye;
    static boolean hesapOlustuMu = false;

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        int secim;

        do {
            menuGoster();
            secim = input.nextInt();

            switch (secim) {
                case 1:
                    hesapOlustur();
                    break;
                case 2:
                    paraYatir();
                    break;
                case 3:
                    paraCek();
                    break;
                case 4:
                    bakiyeSorgula();
                    break;
                case 5:
                    System.out.println("Programdan çýkýlýyor.");
                    break;
                default:
                    System.out.println("Hatalý seçim! Tekrar deneyiniz.");
            }

        } while (secim != 5);
    }

    
    static void menuGoster() {
        System.out.println("\n--- BANKA MENÜSÜ ---");
        System.out.println("1- Hesap Oluþtur");
        System.out.println("2- Para Yatýr");
        System.out.println("3- Para Çek");
        System.out.println("4- Bakiye Sorgula");
        System.out.println("5- Çýkýþ");
        System.out.print("Seçiminiz: ");
    }

    
    static void hesapOlustur() {

        if (hesapOlustuMu) {
            System.out.println("Zaten bir hesap oluþturulmuþ.");
            return;
        }

        System.out.print("Hesap No: ");
        hesapNo = input.nextInt();

        input.nextLine(); 

        System.out.print("Ad: ");
        ad = input.nextLine();

        System.out.print("Soyad: ");
        soyad = input.nextLine();

        System.out.print("Baþlangýç Bakiyesi: ");
        bakiye = input.nextDouble();

        hesapOlustuMu = true;
        System.out.println("Hesap baþarýyla oluþturuldu.");
    }

    
    static void paraYatir() {

        if (!hesapOlustuMu) {
            System.out.println("Önce hesap oluþturmalýsýnýz.");
            return;
        }

        System.out.print("Yatýrýlacak tutar: ");
        double tutar = input.nextDouble();

        if (tutar <= 0) {
            System.out.println("Geçersiz tutar.");
        } else {
            bakiye += tutar;
            System.out.println("Para yatýrýldý. Güncel bakiye: " + bakiye);
        }
    }

    
    static void paraCek() {

        if (!hesapOlustuMu) {
            System.out.println("Önce hesap oluþturmalýsýnýz.");
            return;
        }

        System.out.print("Çekilecek tutar: ");
        double tutar = input.nextDouble();

        if (tutar <= 0) {
            System.out.println("Geçersiz tutar.");
        } else if (tutar > bakiye) {
            System.out.println("Yetersiz bakiye!");
        } else {
            bakiye -= tutar;
            System.out.println("Para çekildi. Güncel bakiye: " + bakiye);
        }
    }

    
    static void bakiyeSorgula() {

        if (!hesapOlustuMu) {
            System.out.println("Önce hesap oluþturmalýsýnýz.");
            return;
        }

        System.out.println("\n--- HESAP BÝLGÝLERÝ ---");
        System.out.println("Hesap No: " + hesapNo);
        System.out.println("Ad Soyad: " + ad + " " + soyad);
        System.out.println("Bakiye: " + bakiye);
    }
}
