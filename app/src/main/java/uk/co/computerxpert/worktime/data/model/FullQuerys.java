package uk.co.computerxpert.worktime.data.model;


public class FullQuerys {

    private int _wt_id;
    private int _wt_comp_id;
    private double _wt_startdate;
    private double _wt_enddate;
    private String _wt_rem;
    private int _wt_week;
    private int _wt_year;

    private int _wage_id;
    private int _wage_comp_id;
    private double _wage_startdate;
    private String _wage_strstdate;
    private String _wage_val;
    private String _wt_strsdate;
    private String _wt_stredate;
    private String _wt_strstime;
    private String _wt_stretime;
    private String _wt_hours;
    private String _wt_salary;
    private String _wt_otwage;

    private int _comp_id;
    private String _comp_name;


    public FullQuerys(){

    }


    public FullQuerys(int comp_id, String comp_name,
                      int wage_id, int wage_comp_id, double  wage_startdate, String wage_strstdate, String wage_val,
                      int wt_id, int wt_comp_id, double  wt_startdate, double  wt_enddate, String wt_rem, int wt_week, int wt_year,
                      String wt_strsdate, String wt_stredate, String wt_strstime, String wt_stretime,
                      String wt_hours, String wt_salary, String wt_otwage){
        this._comp_id = comp_id;
        this._comp_name = comp_name;
        this._wage_id = wage_id;
        this._wage_comp_id = wage_comp_id;
        this._wage_startdate = wage_startdate;
        this._wage_strstdate = wage_strstdate;
        this._wage_val = wage_val;
        this._wt_id = wt_id;
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
        this._wt_otwage = wt_otwage;
    }


    public FullQuerys(String comp_name,
                      int wage_id, int wage_comp_id, double  wage_startdate, String wage_strstdate, String wage_val,
                      int wt_id, int wt_comp_id, double  wt_startdate, double  wt_enddate, String wt_rem, int wt_week, int wt_year,
                      String wt_strsdate, String wt_stredate, String wt_strstime, String wt_stretime,
                      String wt_hours, String wt_salary, String wt_otwage){
        this._comp_name = comp_name;
        this._wage_id = wage_id;
        this._wage_comp_id = wage_comp_id;
        this._wage_startdate = wage_startdate;
        this._wage_strstdate = wage_strstdate;
        this._wage_val = wage_val;
        this._wt_id = wt_id;
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
        this._wt_otwage = wt_otwage;
    }


    public FullQuerys(int comp_id, String comp_name) {
        this._comp_id = comp_id;
        this._comp_name = comp_name;

    }


    public void setcomp_id(int comp_id) {
        this._comp_id = comp_id;
    }

    public int getcomp_id() {
        return this._comp_id;
    }

    public void setcomp_name(String comp_name) { this._comp_name = comp_name; }

    public String getcomp_name() { return this._comp_name; }

    public void setwage_id(int wage_id) { this._wage_id = wage_id; }

    public int getwage_id() { return this._wage_id; }

    public void setwage_comp_id(int wage_comp_id) { this._wage_comp_id = wage_comp_id; }

    public int getwage_comp_id() { return this._wage_comp_id; }

    public void setwage_startdate(double  wage_startdate) { this._wage_startdate = wage_startdate; }

    public double  getwage_startdate() { return this._wage_startdate; }

    public void set_wage_strstdate(String wage_stsstdate) { this._wage_strstdate = wage_stsstdate; }

    public String  get_wage_strstdate() { return this._wage_strstdate; }

    public void setwage_val(String wage_val) { this._wage_val = wage_val; }

    public String getwage_val() { return this._wage_val; }

    public void setwt_id(int wt_id) { this._wt_id = wt_id; }

    public int getwt_id() { return this._wt_id; }

    public void setwt_comp_id(int wt_comp_id) { this._wt_comp_id = wt_comp_id; }

    public int getwt_comp_id() { return this._wt_comp_id; }

    public void setwt_startdate(double  wt_startdate) { this._wt_startdate = wt_startdate; }

    public double  getwt_startdate() { return this._wt_startdate; }

    public void setwt_enddate(double  wt_enddate) {this._wt_enddate = wt_enddate; }

    public double  getwt_enddate() { return this._wt_enddate; }

    public void setwt_rem(String wt_rem) {
        this._wt_rem = wt_rem;
    }

    public String getwt_rem() { return this._wt_rem; }

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

    public void setwt_otwage(String wt_otwage) { this._wt_otwage = wt_otwage; }

    public String getwt_otwage() { return this._wt_otwage; }
}

