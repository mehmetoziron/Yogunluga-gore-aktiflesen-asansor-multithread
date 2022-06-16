/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avm;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oziron
 */
public class AVM {

    /**
     * @param args the command line arguments
     */
    static int aGiris, aGirenKat, aCikis, aCikankat, aToplam = 0;
    static int[] AsansorKapasite = new int[6];         // [4.asansörün] = doluluğu
    static int[] AsansorKat = new int[6];              // [2.asansörün] = bulunduğu kat
    static int[][][] AsansorKuyruk = new int[6][5][5]; // [3.asansörün][1.kat kuyruğunda][4.kata gitmek isteyen] = kişilerin sayısı
    static int[][] AKuyrukKapasite = new int[6][5];    // [2.asansörün][3.kat kuyruğunda] = kaç kişi var
    static int[][] AsansorInis = new int[6][5];        // [3.asansörün][1.katında] = kişi inecek
    static int[] AsansorYön = new int[6];              // [5.asansörün] = Yönü

    static int[] kGezen = new int[5];                  // [1.katta] = 35 kişi geziyor

    public static void main(String[] args) {

//        for(int i=0; i<6;i++)
//            for(int j =0; j<5; j++)
//                for(int k=0;k<5;k++)
//                    System.out.println(AsansorKuyruk[i][j][k]);
        Random rand = new Random();

        Thread tGiris = new Thread() {
            public void run() {
                while (true) {
                    aGiris = rand.nextInt(10) + 1;
                    aGirenKat = rand.nextInt(4) + 1;

                    yazdir();
                    aToplam += aGiris;
                    //System.out.println("giris: " + aToplam);

                    for (int j = 1; j < 6; j++) {
                        int kToplam = 0;
                        for (int k = 0; k < 5; k++) {
                            //System.out.println(AsansorKuyruk[j][0][k]);
                            kToplam += AsansorKuyruk[j][0][k];
                        }
                        if (kToplam < 20) {
                            if (kToplam + aGiris <= 20) {
                                AsansorKuyruk[j][0][aGirenKat] += aGiris;
                                AKuyrukKapasite[j][0] += aGiris;
                                break;
                            } else {
                                AsansorKuyruk[j][0][aGirenKat] += 20 - kToplam;
                                AKuyrukKapasite[j][0] += 20 - kToplam;
                                aGiris = kToplam + aGiris - 20;
                            }
                        }

                    }

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        System.out.println("tGiris uyutulamadı");
                    }
                }

            }
        };
        Thread tCikis = new Thread() {
            public void run() {
                while (true) {
                    aCikis = rand.nextInt(5) + 1;
                    aCikankat = rand.nextInt(4) + 1;
//                    if (kGezen[aCikankat] >= aCikis) {
//                        kGezen[aCikankat]-= aCikis;
//                        for(int i=0;i<6;i++)
//                        {
//                            
//                        }
//                    }
                    if (kGezen[aCikankat] >= aCikis) {
                        for (int j = 1; j < 6; j++) {
                            int kToplam = 0;
                            for (int k = 0; k < 5; k++) {
                                //System.out.println(AsansorKuyruk[j][0][k]);
                                kToplam += AsansorKuyruk[j][aCikankat][k];
                            }
                            if (kToplam < 20) {
                                if (kToplam + aCikis <= 20) {
                                    kGezen[AsansorKat[aCikankat]] -= aCikis;
                                    AsansorKuyruk[j][aCikankat][0] += aCikis;
                                    AKuyrukKapasite[j][aCikankat] += aCikis;
                                    break;
                                } else {
                                    AsansorKuyruk[j][aCikankat][0] += 20 - kToplam;
                                    AKuyrukKapasite[j][aCikankat] += 20 - kToplam;
                                    kGezen[AsansorKat[aCikankat]] -= 20 - kToplam;
                                    aCikis = kToplam + aCikis - 20;
                                }
                            }

                        }
                    } else {
                        continue;
                    }

                    //System.out.println("cikis: " + aToplam);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        System.out.println("tcikis uyutulamadı");
                    }
                }

            }
        };
        Thread tAsansor1 = new Thread() {
            public void run() {
                while (true) {
                    AsansorIn(1);
                    AsansorBin(1);

//                    if (AKuyrukBosMu(1) && (AsansorKapasite[1] == 0)) {
//                        AsansorYön[1] = 0;
//                    } else
                    if (AsansorKat[1] == 0) {
                        AsansorYön[1] = 1;
                    } else if (AsansorKat[1] == 4) {
                        AsansorYön[1] = -1;
                    }
                    AsansorKat[1] += AsansorYön[1];

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println("tcikis uyutulamadı");
                    }

                }
            }
        };
        Thread tAsansor2 = new Thread() {
            public void run() {
                while (true) {
                    AsansorIn(2);
                    AsansorBin(2);

//                    if (AKuyrukBosMu(2) && (AsansorKapasite[2] == 0)) {
//                        AsansorYön[2] = 0;
//                    } else
                    if (AsansorKat[2] == 0) {
                        AsansorYön[2] = 1;
                    } else if (AsansorKat[2] == 4) {
                        AsansorYön[2] = -1;
                    }
                    AsansorKat[2] += AsansorYön[2];

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println("tcikis uyutulamadı");
                    }

                }
            }
        };
        Thread tAsansor3 = new Thread() {
            public void run() {
                while (true) {
                    AsansorIn(3);
                    AsansorBin(3);
//                    boolean kBosmu = true;
//                    for (int k = 0; k < 5; k++) {
//                        for (int i = 0; i < 5; i++) {
//                            if (0 != AsansorKuyruk[1][k][i]) {
//                                kBosmu = false;
//                            }
//                        }
//                    }
//                    if (kBosmu && AsansorKapasite[3] == 0) {
//                        AsansorYön[3] = 0;
//                    } else
                    if (AsansorKat[3] == 0) {
                        AsansorYön[3] = 1;
                    } else if (AsansorKat[3] == 4) {
                        AsansorYön[3] = -1;
                    }
                    AsansorKat[3] += AsansorYön[3];
                    //System.out.println("    yön ("+AsansorYön[1]+","+AsansorYön[2]+","+AsansorYön[3] +")");
                    //System.out.println("    yön (" + AKuyrukKapasite[1][0] + "," + AKuyrukKapasite[2][0] + "," + AKuyrukKapasite[3][0] + ")");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println("tcikis uyutulamadı");
                    }

                }
            }
        };
        Thread tAsansor4 = new Thread() {
            public void run() {
                while (true) {
                    AsansorIn(4);
                    AsansorBin(4);

//                    if (AKuyrukBosMu(4) && (AsansorKapasite[4] == 0)) {
//                        AsansorYön[4] = 0;
//                    } else
                    if (AsansorKat[4] == 0) {
                        AsansorYön[4] = 1;
                    } else if (AsansorKat[4] == 4) {
                        AsansorYön[4] = -1;
                    }
                    AsansorKat[4] += AsansorYön[4];

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println("tcikis uyutulamadı");
                    }

                }
            }
        };
        Thread tAsansor5 = new Thread() {
            public void run() {
                while (true) {
                    AsansorIn(5);
                    AsansorBin(5);

//                    if (AKuyrukBosMu(5) && (AsansorKapasite[5] == 0)) {
//                        AsansorYön[5] = 0;
//                    } else
                    if (AsansorKat[5] == 0) {
                        AsansorYön[5] = 1;
                    } else if (AsansorKat[5] == 4) {
                        AsansorYön[5] = -1;
                    }
                    AsansorKat[5] += AsansorYön[5];

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println("tcikis uyutulamadı");
                    }

                }
            }
        };

        tGiris.start();
        tCikis.start();
        tAsansor1.start();
        tAsansor2.start();
        tAsansor3.start();
        tAsansor4.start();
        tAsansor5.start();
    }

    public static void AsansorBin(int id) {
        for (int k = 0; k < 5; k++) {
            if (10 > AsansorKapasite[id]) {
                if (10 >= AsansorKapasite[id] + AsansorKuyruk[id][AsansorKat[id]][k]) {
                    AsansorKapasite[id] += AsansorKuyruk[id][AsansorKat[id]][k];           // kuyruktan asansöre kişi al
                    AsansorInis[id][k] += AsansorKuyruk[id][AsansorKat[id]][k];            // alınanın neredeineceğini tut
                    AKuyrukKapasite[id][AsansorKat[id]] -= AsansorKuyruk[id][AsansorKat[id]][k];
                    AsansorKuyruk[id][AsansorKat[id]][k] = 0;                             // kişiyi azalt
                } else {
                    AsansorKuyruk[id][AsansorKat[id]][k] += AsansorKapasite[id] - 10;      // artan kişileri kuyrukta bırak
                    AKuyrukKapasite[id][AsansorKat[id]] += AsansorKapasite[id] - 10;
                    AsansorInis[id][k] += 10 - AsansorKapasite[id];            // alınanın neredeineceğini tut
                    AsansorKapasite[id] = 10;                    // Asansör full
                    break;
                }
            }
        }
    }

    public static void AsansorIn(int id) {

        if (0 < AsansorInis[id][AsansorKat[id]]) {
            if (AsansorKat[id] != 0) {
                kGezen[AsansorKat[id]] += AsansorInis[id][AsansorKat[id]];
            } else {
                kGezen[AsansorKat[id]] -= AsansorInis[id][AsansorKat[id]];
            }
            AsansorKapasite[id] -= AsansorInis[id][AsansorKat[id]];
            int tmp = AsansorInis[id][AsansorKat[id]];
            AsansorInis[id][AsansorKat[id]] = 0;
            if (AsansorKat[id] == 0)// 0 a gelen çıkar
            {
                aToplam -= tmp;
//                System.out.println("saa çıktıı kişi"+tmp);
            }
        }
    }

    public static boolean AKuyrukBosMu(int id) {
        boolean kBosmu = true;
        for (int k = 0; k < 5; k++) {
            if (0 != AKuyrukKapasite[id][k]) {
                kBosmu = false;
            }
        }

        return kBosmu;
//        for (int j = 0; j < 5; j++) {
//            for (int k = 0; k < 5; k++) {
//                if (0 < AsansorKuyruk[id][j][k]) {
//                    System.out.println("    "+id+"    "+AsansorKuyruk[id][j][k]);
//                    return false;
//                }
//            }
//        }
//        
//        return true;
    }

    public static void yazdir() {
        System.out.println("0. kat; Toplam Çıkan: "+kGezen[0]+"\n"
                + "1.Asansör Kuyruğu " + AsansorKuyruk[1][0][1] + " " + AsansorKuyruk[1][0][2] + " " + AsansorKuyruk[1][0][3] + " " + AsansorKuyruk[1][0][4] + " \n"
                + "2.Asansör Kuyruğu " + AsansorKuyruk[2][0][1] + " " + AsansorKuyruk[2][0][2] + " " + AsansorKuyruk[2][0][3] + " " + AsansorKuyruk[2][0][4] + " \n"
                + "3.Asansör Kuyruğu " + AsansorKuyruk[3][0][1] + " " + AsansorKuyruk[3][0][2] + " " + AsansorKuyruk[3][0][3] + " " + AsansorKuyruk[3][0][4] + " \n"
                + "4.Asansör Kuyruğu " + AsansorKuyruk[4][0][1] + " " + AsansorKuyruk[4][0][2] + " " + AsansorKuyruk[4][0][3] + " " + AsansorKuyruk[4][0][4] + " \n"
                + "5.Asansör Kuyruğu " + AsansorKuyruk[5][0][1] + " " + AsansorKuyruk[5][0][2] + " " + AsansorKuyruk[5][0][3] + " " + AsansorKuyruk[5][0][4] + " \n"
                + "1. kat; yoğunluk: " + kGezen[1] + "\n"
                + "1.Asansör Kuyruğu " + AsansorKuyruk[1][1][0] + " \n"
                + "2.Asansör Kuyruğu " + AsansorKuyruk[2][1][0] + " \n"
                + "3.Asansör Kuyruğu " + AsansorKuyruk[3][1][0] + " \n"
                + "4.Asansör Kuyruğu " + AsansorKuyruk[4][1][0] + " \n"
                + "5.Asansör Kuyruğu " + AsansorKuyruk[5][1][0] + " \n"
                + "2. kat; yoğunluk: " + kGezen[2] + "\n"
                + "1.Asansör Kuyruğu " + AsansorKuyruk[1][2][0] + " \n"
                + "2.Asansör Kuyruğu " + AsansorKuyruk[2][2][0] + " \n"
                + "3.Asansör Kuyruğu " + AsansorKuyruk[3][2][0] + " \n"
                + "4.Asansör Kuyruğu " + AsansorKuyruk[4][2][0] + " \n"
                + "5.Asansör Kuyruğu " + AsansorKuyruk[5][2][0] + " \n"
                + "3. kat; yoğunluk: " + kGezen[3] + "\n"
                + "1.Asansör Kuyruğu " + AsansorKuyruk[1][3][0] + " \n"
                + "2.Asansör Kuyruğu " + AsansorKuyruk[2][3][0] + " \n"
                + "3.Asansör Kuyruğu " + AsansorKuyruk[3][3][0] + " \n"
                + "4.Asansör Kuyruğu " + AsansorKuyruk[4][3][0] + " \n"
                + "5.Asansör Kuyruğu " + AsansorKuyruk[5][3][0] + " \n"
                + "4. kat; yoğunluk: " + kGezen[4] + "\n"
                + "1.Asansör Kuyruğu " + AsansorKuyruk[1][4][0] + " \n"
                + "2.Asansör Kuyruğu " + AsansorKuyruk[2][4][0] + " \n"
                + "3.Asansör Kuyruğu " + AsansorKuyruk[3][4][0] + " \n"
                + "4.Asansör Kuyruğu " + AsansorKuyruk[4][4][0] + " \n"
                + "5.Asansör Kuyruğu " + AsansorKuyruk[5][4][0] + " \n"
        );
    }
}
