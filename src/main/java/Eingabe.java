
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Eingabe {

    public Map<Integer,List<String>> sequenzen;
    public Tabelle tabelle;
    public Tabelle relativetabelle;


    // Lesen file
    // zuerst sequenzen in Map initialisieren
    // Dann Tabelle für relative Häufigkeit initialisieren
    // Zu guter letzt Koordinaten der Wohnorte
    public Eingabe(String filename) {
        this.sequenzen  = initializeMap(readFileInList(filename));
        this.tabelle = initializeTabelle();
        this.relativetabelle = Tabelle.relativeHäufigkeitsTabelle(this.tabelle);
    }
    // Für jede Sequenz wird ein Map Eintrag erstellt
    // Mit einem Array in dem die jeweiligen Messwerte stehen
    public static Map<Integer,List<String>> initializeMap(List<String> list) {
        Map<Integer,List<String>> result = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            String sequenz = list.get(i).replaceAll(" ", "").trim();
            result.put((i + 1), new ArrayList<>());
            for(int j = 0; j < sequenz.length(); j++) {
                result.get((i + 1)).add(String.valueOf(sequenz.charAt(j)));
            }
        }
        return result;
    }
    // Tabelle mit allen Sequenzen wird initialisiert
    public Tabelle initializeTabelle() {
        Tabelle result = new Tabelle();
        for (int i = 0; i < sequenzen.size(); i++) {
            initializeTabellesequenz(result,sequenzen.get((i + 1)));
        }
        return result;
    }
    // Methode um eine Tabelle mit der Häufigkeit einer Sequenz zu befüllen
    // Indem die Anzahl der Paare aufeinanderfolgender Messwerte gezählt werden
    public void initializeTabellesequenz(Tabelle tabelle,List<String> list) {

        for (int i = 0; i < list.size() - 1; i++) {
            String wert = list.get(i) + list.get(i + 1);
            switch (wert) {
                case "hh" -> tabelle.hh = tabelle.hh + 1;
                case "hn" -> tabelle.hn = tabelle.hn + 1;
                case "hm" -> tabelle.hm = tabelle.hm + 1;
                case "nn" -> tabelle.nn = tabelle.nn + 1;
                case "nh" -> tabelle.nh = tabelle.nh + 1;
                case "nm" -> tabelle.nm = tabelle.nm + 1;
                case "mm" -> tabelle.mm = tabelle.mm + 1;
                case "mn" -> tabelle.mn = tabelle.mn + 1;
                case "mh" -> tabelle.mh = tabelle.mh + 1;
                default -> {
                }
            }
        }


    }

    // Lesen der Datei und speichern die Zeilen in einer List<String>
    // Diese Liste wird im Konstruktor genutzt um alle Attribute der Klasse zu initialisieren
    public static List<String> readFileInList(String fileName)
    {

        List<String> lines = Collections.emptyList();
        try
        {
            lines =
                    Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        }

        catch (IOException e)
        {

            // do something
            e.printStackTrace();
        }
        return lines;
    }


    public static void main(String[] args) throws FileNotFoundException {
        Eingabe test = new Eingabe("src/main/resources/train_N.txt");
        System.out.println("sequenzMap\n" +test.sequenzen);
        System.out.println("häufigkeitsTabelle\n" +test.tabelle);
        System.out.println("relativeHäufigkeitsTabelle\n" +test.relativetabelle);

        System.out.println("check " + test.relativetabelle.check());


    }

}
