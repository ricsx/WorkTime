package uk.co.computerxpert.worktime.data.model;

public class Worktimes {

    public static final String TAG = Worktimes.class.getSimpleName();
    public static final String TABLE = "Worktime";

    public static final String KEY_wt_id = "wt_id";
    public static final String KEY_wt_comp_id = "wt_comp_id";
    public static final String KEY_wt_startdate = "wt_startdate";
    public static final String KEY_wt_enddate = "wt_enddate";
    public static final String KEY_wt_rem = "wt_rem";
    public static final String KEY_wt_week = "wt_week";
    public static final String KEY_wt_year = "wt_year";
    public static final String KEY_wt_strsdate = "wt_strsdate";
    public static final String KEY_wt_stredate = "wt_stredate";
    public static final String KEY_wt_hours = "wt_hours";
    public static final String KEY_wt_salary = "wt_salary";
    public static final String KEY_wt_strstime = "wt_strstime";
    public static final String KEY_wt_stretime = "wt_stretime";
    public static final String KEY_wt_unpaidBreak = "wt_unpbr";
    public static final String KEY_wt_agency_id = "wt_agency_id";
    public static final String KEY_wt_otwage = "wt_otwage";
    public static final String KEY_wt_holiday = "wt_holiday";


    private int _wt_comp_id;
    private double _wt_startdate;
    private double _wt_enddate;
    private String _wt_rem;
    private int _wt_week;
    private int _wt_year;
    private String _wt_strsdate;
    private String _wt_stredate;
    private String _wt_strstime;
    private String _wt_stretime;
    private String _wt_hours;
    private String _wt_salary;
    private int _wt_unpbr;
    private int _wt_agency_id;
    private String _wt_otwage;
    private String _wt_holiday;

    public Worktimes() {

    }

    public Worktimes(int wt_comp_id, double wt_startdate, double wt_enddate, String wt_rem, int wt_week, int wt_year,
                     int wt_unpbr, int wt_agency_id, String wt_otwage) {
        this._wt_comp_id = wt_comp_id;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
        this._wt_week = wt_week;
        this._wt_year = wt_year;
        this._wt_unpbr = wt_unpbr;
        this._wt_agency_id = wt_agency_id;
        this._wt_otwage = wt_otwage;
    }

    public Worktimes(int wt_comp_id, double wt_startdate, double wt_enddate, String wt_rem, int wt_week, int wt_year,
                     String wt_strsdate, String wt_stredate, String wt_strstime, String wt_stretime,
                     String wt_hours, String wt_salary, int wt_unpbr, int wt_agency_id, String wt_otwage,
                     String wt_holiday) {
        this._wt_comp_id = wt_comp_id;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
        this._wt_week = wt_week;
        this._wt_year = wt_year;
        this._wt_strsdate = wt_strsdate;
        this._wt_stredate = wt_stredate;
        this._wt_strstime = wt_strstime;
        this._wt_stretime = wt_stretime;
        this._wt_hours = wt_hours;
        this._wt_salary = wt_salary;
        this._wt_unpbr = wt_unpbr;
        this._wt_agency_id = wt_agency_id;
        this._wt_otwage = wt_otwage;
        this._wt_holiday = wt_holiday;
    }

    public void setwt_comp_id(int wt_comp_id) {
        this._wt_comp_id = wt_comp_id;
    }

    public int getwt_comp_id() {
        return this._wt_comp_id;
    }

    public void setwt_startdate(double wt_startdate) {
        this._wt_startdate = wt_startdate;
    }

    public double getwt_startdate() {
        return this._wt_startdate;
    }

    public void setwt_enddate(double wt_enddate) { this._wt_enddate = wt_enddate;}

    public double getwt_enddate() {
        return this._wt_enddate;
    }

    public void setwt_rem(String wt_rem) {
        this._wt_rem = wt_rem;
    }

    public String getwt_rem() {
        return this._wt_rem;
    }

    public void setwt_week(int wt_week) { this._wt_week = wt_week; }

    public int getwt_week() { return this._wt_week; }

    public void setwt_year(int wt_year) { this._wt_year = wt_year; }

    public int getwt_year() { return this._wt_year; }

    public void setwt_strsdate(String _wt_strsdate) {
        this._wt_strsdate = _wt_strsdate;
    }

    public String getwt_strsdate() {
        return this._wt_strsdate;
    }

    public void setwt_stredate(String _wt_stredate) {
        this._wt_stredate = _wt_stredate;
    }

    public String getwt_stredate() {
        return this._wt_stredate;
    }

    public void setwt_strstime(String _wt_strstime) {
        this._wt_strstime = _wt_strstime;
    }

    public String getwt_strstime() {
        return this._wt_strstime;
    }

    public void setwt_stretime(String _wt_stretime) {
        this._wt_stretime = _wt_stretime;
    }

    public String getwt_stretime() {
        return this._wt_stretime;
    }

    public void setwt_hours(String wt_hours) { this._wt_hours = wt_hours; }

    public String getwt_hours() { return this._wt_hours; }

    public void setwt_salary(String wt_salary) { this._wt_salary = wt_salary; }

    public String getwt_salary() { return this._wt_salary; }

    public void setwt_unpbr(int wt_unpbr) {
        this._wt_unpbr = wt_unpbr;
    }

    public int getwt_unpbr() {
        return this._wt_unpbr;
    }

    public void setwt_agency_id(int wt_agency_id) {
        this._wt_agency_id = wt_agency_id;
    }

    public int getwt_agency_id() {
        return this._wt_agency_id;
    }

    public void setwt_otwage(String wt_otwage) { this._wt_otwage = wt_otwage; }

    public String getwt_otwage() { return this._wt_otwage; }

    public void setwt_holiday(String wt_holiday) { this._wt_holiday = wt_holiday; }

    public String getwt_holiday() { return this._wt_holiday; }
}
