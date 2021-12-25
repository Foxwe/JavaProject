import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;

public class Main {

    public static void main(String[] args)
    {
        String path = "Переводы.csv";
        DataParse.ParseData(path);
        SqliteWork.HistogramData();
        SqliteWork.AverageData();
        SqliteWork.MaxMinData();
        try{
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement st = cn.createStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
