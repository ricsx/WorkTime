package uk.co.computerxpert.worktime;

/**
 * Created by ricsx on 12/12/17.
 */


public class Wtime {


    private int _wt_id;
    private int _wt_comp_id;
    private String _wt_compnm;
    private String _wt_startdate;
    private String _wt_enddate;
    private String _wt_rem;

    public Wtime() {

    }

    public Wtime(int wt_id, int wt_comp_id, String wt_compnm, String wt_startdate, String wt_enddate, String wt_rem) {
        this._wt_id = wt_id;
        this._wt_comp_id = wt_comp_id;
        this._wt_compnm = wt_compnm;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
    }

    public Wtime(int wt_comp_id, String wt_compnm, String wt_startdate, String wt_enddate, String wt_rem) {
        this._wt_comp_id = wt_comp_id;
        this._wt_compnm = wt_compnm;
        this._wt_startdate = wt_startdate;
        this._wt_enddate = wt_enddate;
        this._wt_rem = wt_rem;
    }

    public void setWt_id(int wt_id) {
        this._wt_id = wt_id;
    }

    public int getWt_id() {
        return this._wt_id;
    }

    public void setWt_comp_id(int wt_comp_id) {
        this._wt_comp_id = wt_comp_id;
    }

    public int getwt_comp_id() {
        return this._wt_comp_id;
    }

    public void setWt_compnm(String wt_compnm) {
        this._wt_compnm = wt_compnm;
    }

    public String getwt_compnm() {
        return this._wt_compnm;
    }

    public void setWt_startdate(String wt_startdate) {
        this._wt_startdate = wt_startdate;
    }

    public String getWt_startdate() {
        return this._wt_startdate;
    }

    public void setWt_enddate(String wt_enddate) {
        this._wt_enddate = wt_enddate;
    }

    public String getWt_enddate() {
        return this._wt_enddate;
    }

    public void setWt_rem(String wt_rem) {
        this._wt_rem = wt_rem;
    }

    public String getWt_rem() {
        return this._wt_rem;
    }

}
