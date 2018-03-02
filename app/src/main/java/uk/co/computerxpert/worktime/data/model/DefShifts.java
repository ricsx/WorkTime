package uk.co.computerxpert.worktime.data.model;

public class DefShifts {

    public static final String TAG = DefShifts.class.getSimpleName();
    public static final String TABLE = "DefShifts";

    public static final String KEY_DS_ID = "defsh_id";
    public static final String KEY_DS_Name = "defsh_name";
    public static final String KEY_DS_comp_id = "defsh_comp_id";
    public static final String KEY_DS_Starttime = "defsh_starttime";
    public static final String KEY_DS_Endtime = "defsh_endtime";
    public static final String KEY_DS_Unpbr = "defsh_unpbr";
    public static final String KEY_DS_Agency_id = "defsh_agency_id";


    private int _defsh_id;
    private String _defsh_name;
    private int _defsh_comp_id;
    private String _defsh_starttime;
    private String _defsh_endtime;
    private int _defsh_unpbr;
    private int _defsh_agency_id;


    public DefShifts() {

    }

    public DefShifts(int defsh_id, String defsh_name, int defsh_comp_id, String defsh_starttime,
                     String defsh_endtime, int defsh_unpbr, int defsh_agency_id ) {
        this._defsh_id = defsh_id;
        this._defsh_name = defsh_name;
        this._defsh_comp_id = defsh_comp_id;
        this._defsh_starttime = defsh_starttime;
        this._defsh_endtime = defsh_endtime;
        this._defsh_unpbr = defsh_unpbr;
        this._defsh_agency_id = defsh_agency_id;
    }

    public DefShifts(String defsh_name, int defsh_comp_id, String defsh_starttime,
                     String defsh_endtime, int defsh_unpbr, int defsh_agency_id ) {
        this._defsh_name = defsh_name;
        this._defsh_comp_id = defsh_comp_id;
        this._defsh_starttime = defsh_starttime;
        this._defsh_endtime = defsh_endtime;
        this._defsh_unpbr = defsh_unpbr;
        this._defsh_agency_id = defsh_agency_id;
    }

    public void set_defsh_id(int defsh_id) {
        this._defsh_id = defsh_id;
    }

    public int get_defsh_id() {
        return this._defsh_id;
    }

    public void set_defsh_name(String defsh_name) { this._defsh_name = defsh_name; }

    public String get_defsh_name() { return this._defsh_name; }

    public void set_defsh_comp_id(int defsh_comp_id) { this._defsh_comp_id = defsh_comp_id; }

    public int get_defsh_comp_id() { return this._defsh_comp_id; }

    public void set_defsh_starttime(String defsh_startdade) { this._defsh_starttime = defsh_startdade; }

    public String get_defsh_starttime() { return this._defsh_starttime; }

    public void set_defsh_endtime(String defsh_endtime) { this._defsh_endtime = defsh_endtime; }

    public String get_defsh_endtime() { return this._defsh_endtime; }

    public void set_defsh_unpbr(int defsh_unpbr) { this._defsh_unpbr = defsh_unpbr; }

    public int get_defsh_unpbr() { return this._defsh_unpbr; }

    public void set_defsh_agency_id(int defsh_agency_id) { this._defsh_agency_id = defsh_agency_id; }

    public int get_defsh_agency_id() { return this._defsh_agency_id; }
}
