package main.propgui;

public class Prop {
    private final Integer sorszam;
    private final String kihelyezesDatuma;
    private final String part;
    private final String plakatSzelessegM;
    private final String plakatHosszusagM;
    private final String plakatSzoveg;

    public Prop(Integer sorszam, String kihelyezesDatuma, String part, String plakatSzelessegM, String plakatHosszusagM, String plakatSzoveg) {
        this.sorszam = sorszam;
        this.kihelyezesDatuma = kihelyezesDatuma;
        this.part = part;
        this.plakatSzelessegM = plakatSzelessegM;
        this.plakatHosszusagM = plakatHosszusagM;
        this.plakatSzoveg = plakatSzoveg;
    }

    public Integer getSorszam() { return sorszam; }
    public String getKihelyezesDatuma() { return kihelyezesDatuma; }
    public String getPart() { return part; }
    public String getPlakatSzelessegM() { return plakatSzelessegM; }
    public String getPlakatHosszusagM() { return plakatHosszusagM; }
    public String getPlakatSzoveg() { return plakatSzoveg; }

}
