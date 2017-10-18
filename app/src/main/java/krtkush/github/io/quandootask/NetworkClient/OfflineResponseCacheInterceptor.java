package krtkush.github.io.quandootask.NetworkClient;

import java.io.IOException;

import krtkush.github.io.quandootask.Constants;
import krtkush.github.io.quandootask.UtilityMethods;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor to cache data and maintain it for four weeks.
 *
 * If the device is offline, stale (at most four weeks old) response is fetched from the cache.
 */
public class OfflineResponseCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if(Boolean.valueOf(request.header(Constants.offlineCachingFlagHeader))) {
            if(!UtilityMethods.isNetworkAvailable()) {
                request = request.newBuilder()
                        .removeHeader(Constants.offlineCachingFlagHeader)
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 2419200)
                        .build();
            }
        }

        return chain.proceed(request);
    }
}