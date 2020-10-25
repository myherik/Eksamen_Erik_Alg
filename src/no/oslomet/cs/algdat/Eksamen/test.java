package no.oslomet.cs.algdat.Eksamen;

import java.util.Comparator;

public class test {
    public static void main(String[] args) {
        int[] a = {4,7,2,9,4,10,8,7,4,6,1};
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<Integer>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.fjern(8));
        System.out.println(tre.antall());

    }
}
