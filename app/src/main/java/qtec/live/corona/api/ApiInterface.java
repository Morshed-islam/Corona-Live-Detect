package qtec.live.corona.api;

import java.util.List;

import qtec.live.corona.model.GetCountryModel;
import qtec.live.corona.model.GetGlobalModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
/**
 * Created By Morshed
 * Software Engineer -> Qtec Solution
 * Date 18/03/2020
 */

public interface ApiInterface {

    @GET(HttpParam.API_GET_GLOBAL_COUNT)
    Call<GetGlobalModel> getGlobalDetails();

    @GET(HttpParam.API_GET_ALL_COUNTRY_COUNT)
    Call<List<GetCountryModel>> getCountryDetails();
//    @Query("item_type") String item_type,
}
