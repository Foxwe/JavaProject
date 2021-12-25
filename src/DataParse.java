import java.io.BufferedReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataParse {
    public static ArrayList<Remittance> remittances = new ArrayList<>();

    public static void ParseData(String path)
    {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(path))) {
            String line = br.readLine();
            while ((line = br.readLine())!=null){
                double data_value = 0;
                String[] values = line.split(",");
                if (!values[2].isEmpty())
                {
                    data_value = Double.parseDouble(values[2]);
                }
                remittances.add(new Remittance(
                        values[0],values[1],data_value,values[4],values[5],Integer.parseInt(values[6]),values[7],values[8],values[9],values[10]));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
