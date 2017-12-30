package uk.co.computerxpert.worktime.data.model;

/**
 * Created by ricsx on 29/12/17.
 */



public class Worktime {

    public static final String TAG = Worktime.class.getSimpleName();
    public static final String TABLE = "Worktime";

    public static final String KEY_wt_id = "wt_id";
    public static final String KEY_wt_comp_id = "wt_comp_id";
    public static final String KEY_wt_compnm = "wt_compnm";
    public static final String KEY_wt_startdate = "wt_startdate";
    public static final String KEY_wt_enddate = "wt_enddate";
    public static final String KEY_wt_rem = "wt_rem";
    public static final String KEY_wt_week = "wt_week";

    private int _wt_id;
    private int _wt_comp_id;
    private String _wt_compnm;
    private long _wt_startdate;
    private long _wt_enddate;
    private String _wt_rem;
    private int _wt_week;


    public Worktime() {

    }

    public Worktime(int wt_id, int wt_comp_id, String wt_compnm, long wt_startdate, long wt_enddate, String wt_rem, int wt_week) {
        this._wt_id = wt_id;
        this._wt_comp_id = wt_comp_id;
        this._wt_compnm = wt_compnm;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
        this._wt_week = wt_week;
    }

    public Worktime(int wt_comp_id, String wt_compnm, long wt_startdate, long wt_enddate, String wt_rem, int wt_week) {
        this._wt_comp_id = wt_comp_id;
        this._wt_compnm = wt_compnm;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
        this._wt_week = wt_week;
    }

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

    public void setwt_compnm(String wt_compnm) {
        this._wt_compnm = wt_compnm;
    }

    public String getwt_compnm() {
        return this._wt_compnm;
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

}
