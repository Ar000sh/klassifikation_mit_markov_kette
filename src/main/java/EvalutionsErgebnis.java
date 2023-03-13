import java.math.BigDecimal;
import java.math.RoundingMode;

public class EvalutionsErgebnis {
    // Kosten für Normale Richtig
    public int costNR = 0;
    // Kosten für Normale Falsch
    public int costNF = 2;
    // Kosten für gefährlich Richtig
    public int costGR = 2;
    // Kosten für gefährlich Falsch
    public int costGF = 5;
    // Konfusionsmatrix
    public ConfusionMatrix confusionMatrix;
    // Gesamtkosten ohne Klassifikator
    public double gesamtKostenOhne;
    // Gesamtkosten mit Klassifikator
    public double gesamtKostenMit;
    // Precision für normale Phase
    public double precisionNormal;
    // Precision für gefährliche Phase
    public double precisionGefaehrlich;
    // Recall für normale Phase
    public double recallNormal;
    // Recall für gefährliche Phase
    public double recallGefaehrlich;
    // Wert für die Prozentualle Verbesserung durch Klassifikator
    public double prozentVerbesserung;

    public EvalutionsErgebnis(ConfusionMatrix confusionMatrix) {
        this.confusionMatrix = confusionMatrix;
        this.gesamtKostenOhne = ((confusionMatrix.gR * costGF) + (confusionMatrix.gF * costGF));
        this.gesamtKostenMit = ((confusionMatrix.nR * costNR) + (confusionMatrix.nF * costNF) +(confusionMatrix.gR * costGR) + (confusionMatrix.gF * costGF));
        this.prozentVerbesserung = prozentualeverbesserung(gesamtKostenMit,gesamtKostenOhne);
        this.precisionNormal = precision(confusionMatrix.nR ,confusionMatrix.nF);
        this.recallNormal = recall(confusionMatrix.nR,confusionMatrix.gF);
        this.precisionGefaehrlich = precision(confusionMatrix.gR ,confusionMatrix.gF);
        this.recallGefaehrlich = recall(confusionMatrix.gR,confusionMatrix.nF);
    }
    // Aufrundung auf 2 Dezimalzahlen
    public static double roundTo2decimals(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
    // Prozentrechnung aufgerundet
    public static double prozentualeverbesserung(double obtained, double total) {
        return roundTo2decimals(100 - (obtained * 100 / total));
    }
    // Funktion zur Berechnung des Recalls
    public static double recall(double trueA, double falseB) {
        return roundTo2decimals(trueA / (trueA + falseB));
    }
    // Funktion zur Berechnung der Precision
    public static double precision(double trueA, double falseA) {
        return roundTo2decimals(trueA / (trueA + falseA));
    }
    public String toString() {
        String result = "Anzahl der normal richtigen: " + this.confusionMatrix.nR + "\nAnzahl der normal falschen: " +this.confusionMatrix.nF + "\n";
        result += "Anzahl der gefährlich richtigen: " + this.confusionMatrix.gR + "\nAnzahl der gefährlich falschen: " +this.confusionMatrix.gF + "\n";
        result += "Gesamt verlust ohne Abschaltung der Maschine: " + this.gesamtKostenOhne + "\n";
        result += "Gesamt verlust mit Abschaltung der Maschine: " + this.gesamtKostenMit + "\n";
        result += "prozentuale verbesserung: " + this.prozentVerbesserung + "\nprecision für normal: " + this.precisionNormal + "\n";
        result += "recall normal: " + this.recallNormal + "\nprecision für gefährlich: " + this.precisionGefaehrlich + "\nrecall gefährlich: " + this.recallGefaehrlich;

        return result;
    }
}
