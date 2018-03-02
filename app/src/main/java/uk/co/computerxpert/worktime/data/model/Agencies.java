package uk.co.computerxpert.worktime.data.model;

public class Agencies {
    public static final String TAG = Agencies.class.getSimpleName();
    public static final String TABLE = "Agencies";

    public static final String KEY_agency_id = "agency_id";
    public static final String KEY_agency_name = "agency_name";
    public static final String KEY_agencyAddress = "agency_addrs";
    public static final String KEY_agencyCity = "agency_city";
    public static final String KEY_agencyPostcode = "agency_postc";
    public static final String KEY_agencyPhone = "agency_phone";
    public static final String KEY_contactpersonName = "agency_cpname";
    public static final String KEY_contactpersonPhone = "agency_cpphone";
    public static final String KEY_contactpersonEmail = "agency_cpemail";


    private int _agency_id;
    private String _agency_name;
    private String _agency_addrs;
    private String _agency_city;
    private String _agency_postc;
    private String _agency_phone;
    private String _agency_cpname;
    private String _agency_cpphone;
    private String _agency_cpemail;

    public Agencies() {

    }


    public Agencies(int agency_id, String agency_name){
        this._agency_id = agency_id;
        this._agency_name = agency_name;
    }

    public Agencies(int agency_id, String agency_name, String agency_addrs, String agency_city, String agency_postc,
                     String agency_phone, String agency_cpname, String agency_cpphone, String agency_cpemail
                     ) {
        this._agency_id = agency_id;
        this._agency_name = agency_name;
        this._agency_addrs = agency_addrs;
        this._agency_city = agency_city;
        this._agency_postc = agency_postc;
        this._agency_phone = agency_phone;
        this._agency_cpname = agency_cpname;
        this._agency_cpphone = agency_cpphone;
        this._agency_cpemail = agency_cpemail;
    }

    public Agencies(String agency_name, String agency_addrs, String agency_city, String agency_postc,
                     String agency_phone, String agency_cpname, String agency_cpphone, String agency_cpemail
                    ) {
        this._agency_name = agency_name;
        this._agency_addrs = agency_addrs;
        this._agency_city = agency_city;
        this._agency_postc = agency_postc;
        this._agency_phone = agency_phone;
        this._agency_cpname = agency_cpname;
        this._agency_cpphone = agency_cpphone;
        this._agency_cpemail = agency_cpemail;
    }

    public void setagency_id(int agency_id) {
        this._agency_id = agency_id;
    }

    public int getagency_id() {
        return this._agency_id;
    }

    public void setagency_name(String agency_name) { this._agency_name = agency_name; }

    public String getagency_name() { return this._agency_name; }

    public void setagencyAddress(String agency_addrs) { this._agency_addrs = agency_addrs; }

    public String getagencyAddress() { return this._agency_addrs; }

    public void setagencyCity(String agency_city) { this._agency_city = agency_city; }

    public String getagencyCity() { return this._agency_city; }

    public void setagencyPostcode(String agency_postc) { this._agency_postc = agency_postc; }

    public String getagencyPostcode() { return this._agency_postc; }

    public void setagencyPhone(String agency_phone) { this._agency_phone = agency_phone; }

    public String getagencyPhone() { return this._agency_phone; }

    public void setcontactpersonName(String agency_cpname) { this._agency_cpname = agency_cpname; }

    public String getcontactpersonName() { return this._agency_cpname; }

    public void setcontactpersonPhone(String agency_cpphone) { this._agency_cpphone = agency_cpphone; }

    public String getcontactpersonPhone() { return this._agency_cpphone; }

    public void setcontactpersonEmail(String agency_cpemail) { this._agency_cpemail = agency_cpemail; }

    public String getcontactpersonEmail() { return this._agency_cpemail; }

}
