import java.util.ArrayList;
import java.util.List;

public class Klassifikator {

    public Tabelle normal;
    public Tabelle gefaehrlich;

    public List<Evaluationstupel> evaluationsDaten;
    // Die Vorwahrscheinlichkeit für die normale Phase
    public final double normalVorwahrscheinlichkeit = 0.9;
    // Die Vorwahrscheinlichkeit für die gefährliche Phase
    public final double gefaehrlichVorwahrscheinlichkeit = 0.1;
    // Anfangswahrscheinlichkeit einer Sequenz
    public final double anfangswahrscheinlichkeit = (1.0/0.3);
    public Klassifikator(Tabelle normal) {
        this.normal = normal;

    }
    public Klassifikator(String normal, String gefaehrlich) {
        this.normal = new Eingabe(normal).relativetabelle;
        this.gefaehrlich = new Eingabe(gefaehrlich).relativetabelle;
    }
    public Klassifikator(String normal, String gefaehrlich,String evaluationsDaten) {
        this.normal = new Eingabe(normal).relativetabelle;
        this.gefaehrlich = new Eingabe(gefaehrlich).relativetabelle;
        ReadEvaluation evaluation = new ReadEvaluation(evaluationsDaten);
        this.evaluationsDaten = evaluation.evaluationsDaten;
    }

    // Methode für die Abfolgen zu erzeugen und abzuspeichern
    public List<String> abfolgenerzeugen(String value) {
        List<String> result = new ArrayList<>();
        value = value.replaceAll(" ", "").trim();
        for (int i = 0; i < value.length() - 1; i++) {
            String temp = "" + value.charAt(i) + value.charAt(i + 1);
            result.add(temp);
        }
        return result;
    }
    // Erstmal nehmen wir uns die Anfangswahrscheinlichkeit und speichern diese in einer Variable
    // Dann lesen wir in in einer Loop
    // die Wahrscheinlichkeiten der jeweiligen Abfolgen aus der Tabelle aus
    // und multiplizieren die rausgelesenen Wahrscheinlichkeiten für die jeweiligen Abfolgen mit dem Wert der bereits
    // in der Variable steht
    public double auswertung(List<String> list,Tabelle tabelle) {
       double result = this.anfangswahrscheinlichkeit;
        for (String wert : list) {
            double wahrscheinlichkeit = 0.0;
            switch (wert.trim()) {
                case "hh" -> wahrscheinlichkeit = tabelle.hh;
                case "hn" -> wahrscheinlichkeit = tabelle.hn;
                case "hm" -> wahrscheinlichkeit = tabelle.hm;
                case "nn" -> wahrscheinlichkeit = tabelle.nn;
                case "nh" -> wahrscheinlichkeit = tabelle.nh;
                case "nm" -> wahrscheinlichkeit = tabelle.nm;
                case "mm" -> wahrscheinlichkeit = tabelle.mm;
                case "mn" -> wahrscheinlichkeit = tabelle.mn;
                case "mh" -> wahrscheinlichkeit = tabelle.mh;
                default -> {
                }

            }
            result *= wahrscheinlichkeit;

        }
        return result;
    }


    // Berechnet die Wahrscheinlichkeit einer übergebenen Sequenz mit der normalen und gefährlichen Tabelle.
    // Die berechneten Wahrscheinlichkeiten werden jeweils mit ihren Vorwahrscheinlichkeiten multipliziert
    // Sollte der Wert der mit der normalen Tabelle berechnet wurde größer als der der gefährlichen Berechnung sein
    // Wird Normal klassifiziert. Ansonsten gefährlich
    public String klassifikation(String ausdruck) {
        List<String> arguments = abfolgenerzeugen(ausdruck);
        double normal = auswertung(arguments,this.normal) * normalVorwahrscheinlichkeit;
        double gefaehrlich = auswertung(arguments,this.gefaehrlich) * gefaehrlichVorwahrscheinlichkeit;
        if (normal > gefaehrlich) {
            return "N";
        }
        return "G";
    }
    // Übergeben die Evaluationsdaten und nutzen den Klassifikator für die Klassifikation
    // Erstellen Konfusionsmatrix mit den jeweilig klassifizierten Werten
    public  ConfusionMatrix runSimulation(List<Evaluationstupel> evaluationsDaten) {
        int nR = 0;
        int nF = 0;
        int gR = 0;
        int gF = 0;
        for (Evaluationstupel temp : evaluationsDaten) {
            String result = klassifikation(temp.sequenz);

            if (temp.actual.equals(result)) {
                if (temp.actual.equals("N")) {

                    nR++;
                } else {

                    gR++;
                }
            } else {
                if (temp.actual.equals("N")) {

                    nF++;
                } else {

                    gF++;
                }
            }
        }


        return new ConfusionMatrix(nR, nF, gR,  gF);
    }

    public static void main(String[] args) {
        Klassifikator test = new Klassifikator("src/main/resources/train_N.txt","src/main/resources/train_G.txt","src/main/resources/eval.txt");
        System.out.println("normal\n" + test.normal);
        System.out.println("gefaehrlich\n" + test.gefaehrlich);
        ConfusionMatrix confusionMatrix = test.runSimulation(test.evaluationsDaten);
        EvalutionsErgebnis ergebnis = new EvalutionsErgebnis(confusionMatrix);
        System.out.println(ergebnis);

    }
}
