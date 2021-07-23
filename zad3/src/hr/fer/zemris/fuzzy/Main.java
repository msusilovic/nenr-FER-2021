package hr.fer.zemris.fuzzy;

import hr.fer.zemris.fuzzy.controlSystem.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        int L, D, LK, DK, V, S, akcel = 0, kormilo = 0;

        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fsAkcel = new AkcelFuzzySystemProduct(def);
        FuzzySystem fsKormilo = new KormiloFuzzySystemProduct(def);

        String line;
        while (true) {
            if ((line = input.readLine()) != null) {
                if (line.charAt(0) == 'K') break;
                Scanner s = new Scanner(line);
                L = s.nextInt();
                D = s.nextInt();
                LK = s.nextInt();
                DK = s.nextInt();
                V = s.nextInt();
                S = s.nextInt();

                akcel = fsAkcel.infer(L, D, LK, DK, V, S);
                kormilo = fsKormilo.infer(L, D, LK, DK, V, S);
            }

            System.out.println(akcel + " " + kormilo);
            System.out.flush();
        }
    }
}
