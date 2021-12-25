import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;

public class SqliteWork {

    public static void Initialize(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement st = cn.createStatement();
            String table_1 = "CREATE TABLE remittance(id INTEGER PRIMARY KEY AUTOINCREMENT,period VARCHAR(10),value DOUBLE)";
            String table_2 = "CREATE TABLE remittanceInfo(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "reference VARCHAR(15)," +
                    "status VARCHAR(5)," +
                    "units VARCHAR(10)," +
                    "magnitude DOUBLE," +
                    "subject VARCHAR(25)," +
                    "'group' VARCHAR(255)," +
                    "title1 VARCHAR(15)," +
                    "title2 VARCHAR(30)," +
                    "FOREIGN KEY (id) REFERENCES remittance(id))";
            st.execute(table_1);
            st.execute(table_2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void InsertDataRemittance() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement st = cn.createStatement();

            for (Remittance remittance : DataParse.remittances) {
                st.execute("INSERT INTO remittance(period,value) VALUES("+remittance.getPeriod()+","+remittance.getValue()+")");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InsertDataRemittanceInfo()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            String text = "INSERT INTO remittanceInfo(reference,status,units,magnitude,subject,'group',title1,title2) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
            for (Remittance remittance : DataParse.remittances) {
                PreparedStatement statement = cn.prepareStatement(text);
                statement.setString(1, remittance.getReference());
                statement.setString(2, remittance.getStatus());
                statement.setString(3, remittance.getUnits());
                statement.setDouble(4,remittance.getMagnitude());
                statement.setString(5, remittance.getSubject());
                statement.setString(6,remittance.getGroup());
                statement.setString(7, remittance.getSeries_title1());
                statement.setString(8,remittance.getSeries_title2());
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void DropData(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection cn = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement st = cn.createStatement();
            st.execute("DROP TABLE remittance");
            st.execute("DROP TABLE remittanceInfo");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void HistogramData(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement statement = con.createStatement();
            var dataset = new DefaultCategoryDataset();
            var rs = statement.executeQuery("SELECT substr(period,6,9) as month,sum(VALUE) FROM remittance INNER JOIN remittanceInfo ON remittanceInfo.id = remittance.id WHERE substr(period,0,5) = '2020' AND units = 'Dollars' GROUP BY month");
            while (rs.next())
            {
                System.out.println(rs.getString(1)+"--"+rs.getString(2));
                dataset.addValue(rs.getDouble(2),rs.getString(1),"Номер месяца");
            }
            JFreeChart chart = ChartFactory.createBarChart("График суммы переводов","Месяц","Сумма переводов",dataset);
            ChartFrame chartFrame = new ChartFrame("dataset",chart);
            chartFrame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void AverageData(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement statement = con.createStatement();
            var rs = statement.executeQuery("SELECT period,avg(value) as a ,count(value) as b FROM remittance INNER JOIN remittanceInfo ON remittanceInfo.id = remittance.id WHERE units = 'Dollars' GROUP BY period");
            while(rs.next()){
                System.out.println("Период:"+rs.getString(1)+", Средний размер перевода за период:"+rs.getString(2)+", Количество переводов:"+rs.getString(3));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void MaxMinData(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:remittances.db");
            Statement statement = con.createStatement();
            var rs = statement.executeQuery("SELECT MAX(value),MIN(value) FROM remittance INNER JOIN remittanceInfo ON remittanceInfo.id = remittance.id WHERE substr(period,0,5) IN ('2020','2014','2016') AND units = 'Dollars'");
            while(rs.next())
            {
                System.out.println("Максимальный перевод за периоды:"+rs.getString(1));
                System.out.println("Минимальный перевод за периоды:"+rs.getString(2));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

