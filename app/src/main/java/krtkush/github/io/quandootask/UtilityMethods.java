package krtkush.github.io.quandootask;

import android.net.ConnectivityManager;

/**
 * Created by kartikeykushwaha on 17/10/17.
 */

public class UtilityMethods {

    /**
     * Method to detect network connection on the device
     * @return
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) QuandooTaskApplication.getApplicationInstance()
                        .getSystemService(QuandooTaskApplication.getApplicationInstance()
                                .CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
