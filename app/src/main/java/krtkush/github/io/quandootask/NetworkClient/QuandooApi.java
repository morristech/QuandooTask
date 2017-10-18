package krtkush.github.io.quandootask.NetworkClient;

import java.io.File;
import java.util.ArrayList;

import krtkush.github.io.quandootask.Constants;
import krtkush.github.io.quandootask.QuandooTaskApplication;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by kartikeykushwaha on 16/10/17.
 */

public class QuandooApi {

    private static QuandooApiInterface customerListApi;

    public static QuandooApiInterface getQuandooApi() {

        final String BASE_URL = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/";

        if (customerListApi == null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // Add response caching
                    .addInterceptor(new OfflineResponseCacheInterceptor())
                    // Set the cache location and size (1 MB)
                    .cache(new Cache(new File(QuandooTaskApplication.getApplicationInstance()
                            .getCacheDir(), "QuandooCache"),
                            1 * 1024 * 1024))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            customerListApi = retrofit.create(QuandooApiInterface.class);
        }

        return customerListApi;
    }

    public interface QuandooApiInterface {

        @GET("customer-list.json")
        Call<ArrayList<CustomerListDM>> getCustomerList(
                @Header(Constants.offlineCachingFlagHeader) boolean offlineCacheFlag
        );

        @GET("table-map.json")
        Call<ArrayList<Boolean>> getTableList();
    }
}
