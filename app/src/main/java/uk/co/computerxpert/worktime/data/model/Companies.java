package uk.co.computerxpert.worktime.data.model;

/**
 * Created by ricsx on 29/12/17.
 */


public class Companies {

    public static final String TAG = Companies.class.getSimpleName();
    public static final String TABLE = "Companies";

    public static final String KEY_comp_id = "comp_id";
    public static final String KEY_comp_name = "comp_name";

    private int _comp_id;
    private String _comp_name;


    public Companies() {

    }

    public Companies(int comp_id, String comp_name) {
        this._comp_id = comp_id;
        this._comp_name = comp_name;
    }

    public Companies(String comp_name) {
        this._comp_name = comp_name;
    }

    public void set_comp_id(int comp_id) {
        this._comp_id = comp_id;
    }

    public int getcomp_id() {
        return this._comp_id;
    }

    public void setcomp_name(String comp_name) { this._comp_name = comp_name; }

    public String getcomp_name() { return this._comp_name; }

}
