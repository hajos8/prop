package main;

import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static void main() {
        ArrayList<Prop> props = CSVParser.parse("propaganda.csv");

        System.out.println("1) Beolvasott rekordok száma: " + props.size());

        System.out.println();

        Prop legnagyobbPlakat = props.getFirst();

        for(Prop prop : props) {
            Double szelesseg = Double.parseDouble(prop.getPlakatSzelessegM().replace(",", "."));
            Double hosszusag = Double.parseDouble(prop.getPlakatHosszusagM().replace(",", "."));

            Double regiSzelesseg = Double.parseDouble(legnagyobbPlakat.getPlakatSzelessegM().replace(",", "."));
            Double regiHosszusag = Double.parseDouble(legnagyobbPlakat.getPlakatHosszusagM().replace(",", "."));

            if(regiSzelesseg * regiHosszusag < szelesseg * hosszusag) {
                legnagyobbPlakat = prop;
            }
        }

        System.out.println("2) Legnagyobb méretű plakát(ok) (terület alapján):");
        printProp(legnagyobbPlakat);
        System.out.println();

        System.out.println("3) Fideszes plakátok (megjelenés dátuma szerint csökkenő sorrend):");

        props.sort(Comparator.comparing(Prop::getKihelyezesDatuma));

        for(Prop prop : props) {
            if(Objects.equals(prop.getPart(), "FIDESZ")){
                System.out.print("- sorszám:  " + prop.getSorszam() + ";");
                System.out.print(" dátum:  " + prop.getKihelyezesDatuma() + ";");
                System.out.print(" méret:  " + prop.getPlakatSzelessegM() + " x " + prop.getPlakatHosszusagM() + ";");
                System.out.print(" szöveg:  " + prop.getPlakatSzoveg());
                System.out.println();
            }
        }

        ArrayList<String> egyediPartok = new ArrayList<>();

        for(Prop prop : props) {
            if(!egyediPartok.contains(prop.getPart())) {
                egyediPartok.add(prop.getPart());
            }
        }

        Random rand = new Random();

        String randomPart = egyediPartok.get(rand.nextInt(egyediPartok.size()));

        int plakatok = 0;

        for(Prop prop : props) {
            if(Objects.equals(prop.getPart(), randomPart)) {
                plakatok++;
            }
        }

        System.out.println("4) Egyedi kihelyező pártok száma: " + egyediPartok.size());
        System.out.println("Véletlenszerűen kiválasztott párt: " + randomPart);
        System.out.println(randomPart + " által kihelyezett plakátok száma: " +  plakatok);

        System.out.println();

        props.sort(Comparator.comparing(prop -> {
            return szoSzamlalo(prop.getPlakatSzoveg());
        }));

        System.out.println("5) Plakátszöveg szavainak számláló függvénye:");
        printProp(props.getLast());
        System.out.println("   szavak száma: " + szoSzamlalo(props.getLast().getPlakatSzoveg()));
        System.out.println();

        System.out.println("6) Statisztika pártonként (a kiírt évek növekvő sorrendben, évek vesszővel" +
                " elválasztva; utolsó után nincs vessző):");

        props.sort(Comparator.comparing(Prop::getKihelyezesDatuma));

        HashMap<String, String> partokEsDatumok = new HashMap<>();

        for(Prop prop : props) {
            if(!partokEsDatumok.containsKey(prop.getPart())) {
                partokEsDatumok.put(prop.getPart(), prop.getKihelyezesDatuma().split("\\.")[0]);
            }
            else{
                partokEsDatumok.put(prop.getPart(), partokEsDatumok.get(prop.getPart()) + "," + prop.getKihelyezesDatuma().split("\\.")[0]);
            }
        }

        Map<String, String> rendezettPartok = partokEsDatumok.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> Integer.parseInt(e.getValue().split(",")[0].trim())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        for (Map.Entry<String, String> e : rendezettPartok.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }

        System.out.println();

        String fileName = "csorok.txt";

        System.out.println("7) A pontosan egy plakátot kihelyező pártok adatai elmentve: " + fileName);

        ArrayList<Prop> csorok = new ArrayList<>();

        for(String part : egyediPartok) {
            Prop csoro = null;
            int counter = 0;
            for(Prop prop : props) {
                if(Objects.equals(prop.getPart(), part)) {
                    counter++;
                    csoro = prop;
                }
                if(counter > 1) {
                    break;
                }
            }
            if(counter == 1){
                csorok.add(csoro);
            }
        }

        try{
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("sorszám;kihelyezés_dátuma;párt;plakát_szélesség_m;plakát_hosszúság_m;plakát_szöveg\n");

            for(Prop prop : csorok) {
                stringBuilder.append(prop.getSorszam());
                stringBuilder.append(";");
                stringBuilder.append(prop.getKihelyezesDatuma());
                stringBuilder.append(";");
                stringBuilder.append(prop.getPart());
                stringBuilder.append(";");
                stringBuilder.append(prop.getPlakatSzelessegM());
                stringBuilder.append(";");
                stringBuilder.append(prop.getPlakatHosszusagM());
                stringBuilder.append(";");
                stringBuilder.append(prop.getPlakatSzoveg());
                stringBuilder.append("\n");
            }

            FileWriter writer = new FileWriter(fileName);

            writer.write(stringBuilder.toString());

            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void printProp(Prop prop) {
        System.out.println(" - " + "sorszám: " + prop.getSorszam());
        System.out.println("   " + "kihelyezés dátuma: " + prop.getKihelyezesDatuma());
        System.out.println("   " + "párt: " + prop.getPart());
        System.out.println("   " + "szélesség_m: " + prop.getPlakatSzelessegM());
        System.out.println("   " + "hosszúság_m: " + prop.getPlakatHosszusagM());
        System.out.println("   " + "terület_m2: " + (Double.parseDouble(prop.getPlakatSzelessegM().replace(",", ".")) * Double.parseDouble(prop.getPlakatHosszusagM().replace(",", "."))));
        System.out.println("   " + "szöveg: " + prop.getPlakatSzoveg());
    }

    public static int szoSzamlalo(String szo){
        return Objects.equals(szo, "") ? 0 : szo.split(" ").length;
    }
}
