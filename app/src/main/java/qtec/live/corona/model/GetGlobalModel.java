package qtec.live.corona.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */
public class GetGlobalModel {
    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

}
