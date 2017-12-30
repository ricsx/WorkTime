package uk.co.computerxpert.worktime.data.model;

/**
 * Created by ricsx on 29/12/17.
 */



public class Wage {

    public static final String TAG = Wage.class.getSimpleName();
    public static final String TABLE = "Wage";

    public static final String KEY_wage_id = "wage_id";
    public static final String KEY_wage_comp_id = "wage_comp_id";
    public static final String KEY_wage_startdate = "wage_startdate";
    public static final String KEY_wage_enddate = "wage_enddate";
    public static final String KEY_wage_val = "wage_val";

    private int _wage_id;
    private int _wage_comp_id;
    private long _wage_startdate;
    private long _wage_enddate;
    private float _wage_val;


    public Wage() {
    }

    public Wage(int wage_id, int wage_comp_id, long wage_startdate, long wage_enddate, float wage_val) {
        this._wage_id = wage_id;
        this._wage_comp_id = wage_comp_id;
        this._wage_startdate = wage_startdate;
        this._wage_enddate = wage_enddate;
        this._wage_val = wage_val;
    }

    public Wage(int wage_comp_id, long wage_startdate, long wage_enddate, float wage_val) {
        this._wage_comp_id = wage_comp_id;
        this._wage_startdate = wage_startdate;
        this._wage_enddate = wage_enddate;
        this._wage_val = wage_val;
    }

    public void setwage_id(int wage_id) {
        this._wage_id = wage_id;
    }

    public int getwage_id() {
        return this._wage_id;
    }

    public void setwage_comp_id(int wage_comp_id) {
        this._wage_comp_id = wage_comp_id;
    }

    public int getwage_comp_id() {
        return this._wage_comp_id;
    }

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

    public void setwage_val(float wage_val) { this._wage_val = wage_val; }

    public float getwage_val() { return this._wage_val; }

}
