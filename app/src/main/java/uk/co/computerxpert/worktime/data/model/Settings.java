package uk.co.computerxpert.worktime.data.model;

public class Settings {

    public static final String TAG = Settings.class.getSimpleName();
    public static final String TABLE = "Settings";

    public static final String KEY_settings_id = "settings_id";
    public static final String KEY_settings_name = "settings_name";
    public static final String KEY_settings_val = "settings_val";

    private int _settings_id;
    private String _settings_name;
    private String _settings_val;



    public Settings() {
    }

    public void set_settings_id(int settings_id) {
        this._settings_id = settings_id;
    }

    public int get_settings_id() {
        return this._settings_id;
    }

    public void set_settings_name(String settings_name) { this._settings_name = settings_name; }

    public String get_settings_name() { return this._settings_name; }

    public void set_settings_val(String settings_val) { this._settings_val = settings_val; }

    public String get_settings_val() { return _settings_val; }
}
