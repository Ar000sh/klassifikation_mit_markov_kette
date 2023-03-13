import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReadEvaluation {

    public List<Evaluationstupel> evaluationsDaten;

    public ReadEvaluation(String filename) {
        this.evaluationsDaten = create(readFileInList(filename));
    }
    // Erzeugen evaluationstupel mit den Sequenzen und der jeweiligen tatsächlichen Phase
    public List<Evaluationstupel> create(List<String> list) {
        List<Evaluationstupel> result = new ArrayList<>();

        for (String s : list) {
            String temp = s.replaceAll(" ", "").trim();
            result.add(new Evaluationstupel(("" + temp.charAt(0)), temp.substring(1)));

        }
        return result;
    }
    // lesen die Sequenzen aus den Testdaten gespeichert in Listen
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
}
