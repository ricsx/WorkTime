package uk.co.computerxpert.worktime.data.model;


public class Wage {

    public static final String TAG = Wage.class.getSimpleName();
    public static final String TABLE = "Wage";

    public static final String KEY_wage_id = "wage_id";
    public static final String KEY_wage_comp_id = "wage_comp_id";
    public static final String KEY_wage_startdate = "wage_startdate";
    public static final String KEY_wage_strstdate = "wage_strstdate";
    public static final String KEY_wage_val = "wage_val";

    private int _wage_id;
    private int _wage_comp_id;
    private double _wage_startdate;
    private String _wage_strstdate;
    private String _wage_val;
    private String _comp_name;


    public Wage() {
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

    public void setwage_startdate(double wage_startdate) {
        this._wage_startdate = wage_startdate;
    }

    public double getwage_startdate() {
        return this._wage_startdate;
    }

    public void setwage_strstdate(String wage_strstdate) {
        this._wage_strstdate = wage_strstdate;
    }

    public String getwage_strstdate() { return this._wage_strstdate; }

    public void setwage_val(String wage_val) { this._wage_val = wage_val; }

    public String getwage_val() { return this._wage_val; }

    // To the relation-querys:

    public void setcomp_name(String comp_name) { this._comp_name = comp_name; }

    public String getcomp_name() { return _comp_name; }
}
