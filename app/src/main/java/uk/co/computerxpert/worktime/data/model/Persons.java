package uk.co.computerxpert.worktime.data.model;


public class Persons {
    public static final String TAG = Persons.class.getSimpleName();
    public static final String TABLE = "Persons";

    public static final String KEY_persons_id = "persons_id";
    public static final String KEY_persons_fname = "persons_fname";
    public static final String KEY_persons_lname = "persons_lname";
    public static final String KEY_personsAddress = "persons_addrs";
    public static final String KEY_personsCity = "persons_city";
    public static final String KEY_personsPostcode = "persons_postc";
    public static final String KEY_personsPhone = "persons_phone";

    private int _persons_id;
    private String _persons_fname;
    private String _persons_lname;
    private String _persons_addrs;
    private String _persons_city;
    private String _persons_postc;
    private String _persons_phone;


    public Persons() {

    }


    public Persons(int persons_id, String persons_fname, String persons_lname){
        this._persons_id = persons_id;
        this._persons_fname = persons_fname;
        this._persons_lname = persons_lname;
    }

    public Persons(int persons_id, String persons_fname, String persons_lname, String persons_addrs, String persons_city, String persons_postc,
                    String persons_phone
    ) {
        this._persons_id = persons_id;
        this._persons_fname = persons_fname;
        this._persons_lname = persons_lname;
        this._persons_addrs = persons_addrs;
        this._persons_city = persons_city;
        this._persons_postc = persons_postc;
        this._persons_phone = persons_phone;
    }

    public Persons(String persons_fname, String persons_lname, String persons_addrs, String persons_city, String persons_postc,
                   String persons_phone
    ) {
        this._persons_fname = persons_fname;
        this._persons_lname = persons_lname;
        this._persons_addrs = persons_addrs;
        this._persons_city = persons_city;
        this._persons_postc = persons_postc;
        this._persons_phone = persons_phone;
    }

    public void setpersons_id(int persons_id) { this._persons_id = persons_id; }

    public int getpersons_id() { return this._persons_id; }

    public void setpersons_fname(String persons_fname) { this._persons_fname = persons_fname; }

    public String getpersons_fname() { return this._persons_fname; }

    public void setpersons_lname(String persons_lname) { this._persons_lname = persons_lname; }

    public String getpersons_lname() { return this._persons_lname; }

    public void setpersonsAddress(String persons_addrs) { this._persons_addrs = persons_addrs; }

    public String getpersonsAddress() { return this._persons_addrs; }

    public void setpersonsCity(String persons_city) { this._persons_city = persons_city; }

    public String getpersonsCity() { return this._persons_city; }

    public void setpersonsPostcode(String persons_postc) { this._persons_postc = persons_postc; }

    public String getpersonsPostcode() { return this._persons_postc; }

    public void setpersonsPhone(String persons_phone) { this._persons_phone = persons_phone; }

    public String getpersonsPhone() { return this._persons_phone; }

}
