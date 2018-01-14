package uk.co.computerxpert.worktime.data.model;

/**
 * Created by ricsx on 29/12/17.
 */


public class Companies {

    public static final String TAG = Companies.class.getSimpleName();
    public static final String TABLE = "Companies";

    public static final String KEY_comp_id = "comp_id";
    public static final String KEY_comp_name = "comp_name";
    public static final String KEY_companyAddress = "comp_addrs";
    public static final String KEY_companyCity = "comp_city";
    public static final String KEY_companyPostcode = "comp_postc";
    public static final String KEY_companyPhone = "comp_phone";
    public static final String KEY_contactpersonName = "comp_cpname";
    public static final String KEY_contactpersonPhone = "comp_cpphone";
    public static final String KEY_contactpersonEmail = "comp_cpemail";
    public static final String KEY_companyAgencyID = "comp_agencyid";



    private int _comp_id;
    private String _comp_name;
    private String _comp_addrs;
    private String _comp_city;
    private String _comp_postc;
    private String _comp_phone;
    private String _comp_cpname;
    private String _comp_cpphone;
    private String _comp_cpemail;
    private int _comp_agencyid;


    public Companies() {

    }


    public Companies(int comp_id, String comp_name){
        this._comp_id = comp_id;
        this._comp_name = comp_name;
    }

    public Companies(int comp_id, String comp_name, String comp_addrs, String comp_city, String comp_postc,
                     String comp_phone, String comp_cpname, String comp_cpphone, String comp_cpemail,
                     int comp_agencyid) {
        this._comp_id = comp_id;
        this._comp_name = comp_name;
        this._comp_addrs = comp_addrs;
        this._comp_city = comp_city;
        this._comp_postc = comp_postc;
        this._comp_phone = comp_phone;
        this._comp_cpname = comp_cpname;
        this._comp_cpphone = comp_cpphone;
        this._comp_cpemail = comp_cpemail;
        this._comp_agencyid = comp_agencyid;
    }

    public Companies(String comp_name, String comp_addrs, String comp_city, String comp_postc,
                     String comp_phone, String comp_cpname, String comp_cpphone, String comp_cpemail,
                     int comp_agencyid) {
        this._comp_name = comp_name;
        this._comp_addrs = comp_addrs;
        this._comp_city = comp_city;
        this._comp_postc = comp_postc;
        this._comp_phone = comp_phone;
        this._comp_cpname = comp_cpname;
        this._comp_cpphone = comp_cpphone;
        this._comp_cpemail = comp_cpemail;
        this._comp_agencyid = comp_agencyid;
    }

    public void setcomp_id(int comp_id) {
        this._comp_id = comp_id;
    }

    public int getcomp_id() {
        return this._comp_id;
    }

    public void setcomp_name(String comp_name) { this._comp_name = comp_name; }

    public String getcomp_name() { return this._comp_name; }

    public void setcompanyAddress(String comp_addrs) { this._comp_addrs = comp_addrs; }

    public String getcompanyAddress() { return this._comp_addrs; }

    public void setcompanyCity(String comp_city) { this._comp_city = comp_city; }

    public String getcompanyCity() { return this._comp_city; }

    public void setcompanyPostcode(String comp_postc) { this._comp_postc = comp_postc; }

    public String getcompanyPostcode() { return this._comp_postc; }

    public void setcompanyPhone(String comp_phone) { this._comp_phone = comp_phone; }

    public String getcompanyPhone() { return this._comp_phone; }

    public void setcontactpersonName(String comp_cpname) { this._comp_cpname = comp_cpname; }

    public String getcontactpersonName() { return this._comp_cpname; }

    public void setcontactpersonPhone(String comp_cpphone) { this._comp_cpphone = comp_cpphone; }

    public String getcontactpersonPhone() { return this._comp_cpphone; }

    public void setcontactpersonEmail(String comp_cpemail) { this._comp_cpemail = comp_cpemail; }

    public String getcontactpersonEmail() { return this._comp_cpemail; }

    public void setcompanyAgencyID(int _comp_agencyid) { this._comp_agencyid = _comp_agencyid; }

    public int getcompanyAgencyID() { return this._comp_agencyid; }


}
