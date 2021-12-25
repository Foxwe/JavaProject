import java.util.Calendar;

public class Remittance {
    private String reference;
    private String period;
    private Double value;
    private String status;
    private String units;
    private int magnitude;
    private String subject;
    private String group;
    private String series_title1;
    private String series_title2;

    public Remittance(String reference, String period, Double value, String status, String units, int magnitude, String subject, String group, String series_title1, String series_title2) {
        this.reference = reference;
        this.period = period;
        this.value = value;
        this.status = status;
        this.units = units;
        this.magnitude = magnitude;
        this.subject = subject;
        this.group = group;
        this.series_title1 = series_title1;
        this.series_title2 = series_title2;
    }

    public String getReference() {
        return reference;
    }

    public String getPeriod() {
        return period;
    }

    public Double getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public String getUnits() {
        return units;
    }

    public int getMagnitude() {
        return magnitude;
    }

    public String getSubject() {
        return subject;
    }

    public String getGroup() {
        return group;
    }

    public String getSeries_title1() {
        return series_title1;
    }

    public String getSeries_title2() {
        return series_title2;
    }

}
