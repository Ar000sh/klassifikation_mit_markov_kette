import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Tabelle {
    public double hh;
    public double hm;
    public double hn;
    public double nn;
    public double nm;
    public double nh;
    public double mm;
    public double mh;
    public double mn;

    public Tabelle() {
        this.hh = 0;
        this.hm = 0;
        this.hn = 0;
        this.nn = 0;
        this.nm = 0;
        this.nh = 0;
        this.mm = 0;
        this.mh = 0;
        this.mn = 0;
    }
    public Tabelle(Tabelle value) {
        this.hh = value.hh;
        this.hm = value.hm;
        this.hn = value.hn;
        this.nn = value.nn;
        this.nm = value.nm;
        this.nh = value.nh;
        this.mm = value.mm;
        this.mh = value.mh;
        this.mn = value.mn;
    }

    // Berechnen die Zeilensummen
    public static Map<Integer,Double> rowsSum(Tabelle tabelle) {
        Map<Integer,Double> result = new HashMap<>();
        result.put(1,tabelle.nn + tabelle.nm + tabelle.nh);
        result.put(2,tabelle.mn + tabelle.mm + tabelle.mh);
        result.put(3,tabelle.hn + tabelle.hm + tabelle.hh);
        return result;
    }
    // Tabelle mit Einträgen der Relativen Häufigkeiten
    // Berechnet durch Absolute Häufigkeit / Zeilensummen
    public static Tabelle relativeHäufigkeitsTabelle(Tabelle value) {
        Tabelle tabelle = new Tabelle(value);
        Map<Integer,Double> sumOfRows = rowsSum(tabelle);
        tabelle.nn = (tabelle.nn / sumOfRows.get(1));
        tabelle.nm = (tabelle.nm / sumOfRows.get(1));
        tabelle.nh = (tabelle.nh / sumOfRows.get(1));
        tabelle.mn = (tabelle.mn / sumOfRows.get(2));
        tabelle.mm = (tabelle.mm / sumOfRows.get(2));
        tabelle.mh = (tabelle.mh / sumOfRows.get(2));
        tabelle.hn = (tabelle.hn / sumOfRows.get(3));
        tabelle.hm = (tabelle.hm / sumOfRows.get(3));
        tabelle.hh = (tabelle.hh / sumOfRows.get(3));
        return tabelle;
    }

    // Prüfen ob die Summe der Wahrscheinlichkeiten einer Zeile 1.0 bzw. 100% ergibt
    public boolean check() {
        boolean firstrow = (this.nn + this.nm + this.nh) == 1.0;
        boolean secondrow = (this.mn + this.mm + this.mh) == 1.0;
        boolean thirdrow = (this.hn + this.hm + this.hh) == 1.0;
        return firstrow && secondrow && thirdrow;
    }

    public String toString() {
        String result = "";
        result += "  |      n       |     m       |    h  \n" ;
        result += "n |    " + this.nn + "     |    "+ this.nm + "    |    " + this.nh + "\n"  ;
        result += "m |    " + this.mn + "     |    "+ this.mm + "    |    " + this.mh + "\n"  ;
        result += "h |    " + this.hn + "     |    "+ this.hm + "    |    " + this.hh + "\n"  ;
        return result;
    }
}
