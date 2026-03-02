package main.propgui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSVParser {
    public static ArrayList<Prop> parse(String filename) {
        ArrayList<Prop> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                String part0 = parts.length > 0 ? parts[0] : "";
                Integer sorszamValue = part0 == null || part0.isEmpty() ? null : Integer.parseInt(part0);
                String part1 = parts.length > 1 ? parts[1] : "";
                String kihelyezesDatumaValue = part1 == null || part1.isEmpty() ? null : part1;
                String part2 = parts.length > 2 ? parts[2] : "";
                String partValue = part2 == null || part2.isEmpty() ? null : part2;
                String part3 = parts.length > 3 ? parts[3] : "";
                String plakatSzelessegMValue = part3 == null || part3.isEmpty() ? null : part3;
                String part4 = parts.length > 4 ? parts[4] : "";
                String plakatHosszusagMValue = part4 == null || part4.isEmpty() ? null : part4;
                String part5 = parts.length > 5 ? parts[5] : "";
                String plakatSzovegValue = part5 == null || part5.isEmpty() ? null : part5;
                Prop rec = new Prop(
                        sorszamValue,
                        kihelyezesDatumaValue,
                        partValue,
                        plakatSzelessegMValue,
                        plakatHosszusagMValue,
                        plakatSzovegValue
                );
                list.add(rec);
            }
        } catch (Exception e) { System.out.println("Parse error: " + e.getMessage()); }
        return list;
    }
}
