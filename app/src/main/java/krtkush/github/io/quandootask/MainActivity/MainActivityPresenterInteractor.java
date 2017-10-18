package krtkush.github.io.quandootask.MainActivity;

/**
 * Created by kartikeykushwaha on 16/10/17.
 */

public interface MainActivityPresenterInteractor {

    void getCustomerList();

    void cancelCustomerListApiCall();

    void activityDestroyed();

    void searchString(String query);

    void getTableList();

    void cancelTableListApiCall();
}
