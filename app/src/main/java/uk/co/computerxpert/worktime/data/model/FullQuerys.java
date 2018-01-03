package uk.co.computerxpert.worktime.data.model;

/**
 * Created by ricsx on 03/01/18.
 */

public class FullQuerys {

/*    public static final String TAG = Worktimes.class.getSimpleName();
    public static final String TAG = Companies.class.getSimpleName();
    public static final String TAG = Wage.class.getSimpleName();
*/
    public static final String Wo_TABLE = "Worktime";

    public static final String KEY_wt_id = "wt_id";
 //   public static final String KEY_wt_comp_id = "wt_comp_id";
    public static final String KEY_wt_startdate = "wt_startdate";
    public static final String KEY_wt_enddate = "wt_enddate";
    public static final String KEY_wt_rem = "wt_rem";
    public static final String KEY_wt_week = "wt_week";
    public static final String KEY_wt_year = "wt_year";

    private int _wt_id;
    private int _wt_comp_id;
    private long _wt_startdate;
    private long _wt_enddate;
    private String _wt_rem;
    private int _wt_week;
    private int _wt_year;

    public static final String W_TABLE = "Wage";

    public static final String KEY_wage_id = "wage_id";
    public static final String KEY_wage_comp_id = "wage_comp_id";
    public static final String KEY_wage_startdate = "wage_startdate";
    public static final String KEY_wage_enddate = "wage_enddate";
    public static final String KEY_wage_val = "wage_val";

    private int _wage_id;
    private int _wage_comp_id;
    private long _wage_startdate;
    private long _wage_enddate;
    private String _wage_val;

    public static final String C_TABLE = "Companies";

    public static final String KEY_comp_id = "comp_id";
    public static final String KEY_comp_name = "comp_name";

    private int _comp_id;
    private String _comp_name;


    public FullQuerys(){

    }


    public FullQuerys(int comp_id, String comp_name,
                      int wage_id, int wage_comp_id, long wage_startdate, long wage_enddate, String wage_val,
                      int wt_id, int wt_comp_id, long wt_startdate, long wt_enddate, String wt_rem, int wt_week, int wt_year){
        this._comp_id = comp_id;
        this._comp_name = comp_name;
        this._wage_id = wage_id;
        this._wage_comp_id = wage_comp_id;
        this._wage_startdate = wage_startdate;
        this._wage_enddate = wage_enddate;
        this._wage_val = wage_val;
        this._wt_id = wt_id;
        this._wt_comp_id = wt_comp_id;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
        this._wt_week = wt_week;
        this._wt_year = wt_year;
    }


    public FullQuerys(String comp_name,
                      int wage_id, int wage_comp_id, long wage_startdate, long wage_enddate, String wage_val,
                      int wt_id, int wt_comp_id, long wt_startdate, long wt_enddate, String wt_rem, int wt_week, int wt_year){
        this._comp_name = comp_name;
        this._wage_id = wage_id;
        this._wage_comp_id = wage_comp_id;
        this._wage_startdate = wage_startdate;
        this._wage_enddate = wage_enddate;
        this._wage_val = wage_val;
        this._wt_id = wt_id;
        this._wt_comp_id = wt_comp_id;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
        this._wt_week = wt_week;
        this._wt_year = wt_year;
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

    public void setwage_id(int wage_id) {
        this._wage_id = wage_id;
    }

    public int getwage_id() {
        return this._wage_id;
    }

    public void setwage_comp_id(int wage_comp_id) {
        this._wage_comp_id = wage_comp_id;
    }

    public int getwage_comp_id() { return this._wage_comp_id; }

    public void setwage_startdate(long wage_startdate) {
        this._wage_startdate = wage_startdate;
    }

    public long getwage_startdate() {
        return this._wage_startdate;
    }

    public void setwage_enddate(long wage_enddate) {
        this._wage_enddate = wage_enddate;
    }

    public long getwage_enddate() {
        return this._wage_enddate;
    }

    public void setwage_val(String wage_val) { this._wage_val = wage_val; }

    public String getwage_val() { return this._wage_val; }

    public void setwt_id(int wt_id) {
        this._wt_id = wt_id;
    }

    public int getwt_id() {
        return this._wt_id;
    }

    public void setwt_comp_id(int wt_comp_id) {
        this._wt_comp_id = wt_comp_id;
    }

    public int getwt_comp_id() {
        return this._wt_comp_id;
    }

    public void setwt_startdate(long wt_startdate) {
        this._wt_startdate = wt_startdate;
    }

    public long getwt_startdate() {
        return this._wt_startdate;
    }

    public void setwt_enddate(long wt_enddate) {
        this._wt_enddate = wt_enddate;
    }

    public long getwt_enddate() {
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

}

