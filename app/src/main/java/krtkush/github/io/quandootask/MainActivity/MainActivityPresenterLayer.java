package krtkush.github.io.quandootask.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import krtkush.github.io.quandootask.DaoSession;
import krtkush.github.io.quandootask.NetworkClient.CustomerListDM;
import krtkush.github.io.quandootask.NetworkClient.QuandooApi;
import krtkush.github.io.quandootask.QuandooTaskApplication;
import krtkush.github.io.quandootask.Table;
import krtkush.github.io.quandootask.TableDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static krtkush.github.io.quandootask.Constants.IS_TABLE_LIST_SAVED;
import static krtkush.github.io.quandootask.Constants.SHARED_PREFERENCES_LABEL;

/**
 * Created by kartikeykushwaha on 16/10/17.
 */

class MainActivityPresenterLayer implements MainActivityPresenterInteractor {

    private WeakReference<MainActivity> mainActivity;
    private QuandooApi.QuandooApiInterface quandooApiInterface;
    private Call<ArrayList<CustomerListDM>> customerListApiCall;
    private Call<ArrayList<Boolean>> tableListApiCall;
    private ArrayList<CustomerListDM> customerList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private TableDao tableDao;

    MainActivityPresenterLayer(MainActivity mainActivity) {

        this.mainActivity = new WeakReference<>(mainActivity);

        // Initialize the shared prefs
        sharedPreferences = this.mainActivity.get()
                .getSharedPreferences(SHARED_PREFERENCES_LABEL, Context.MODE_PRIVATE);

        // Initialize the API interface
        quandooApiInterface = QuandooTaskApplication.getApplicationInstance()
                .getQuandooApiInterface();
    }

    @Override
    public void getCustomerList() {

        if (mainActivity != null) {
            customerListApiCall = quandooApiInterface.getCustomerList(true);
            customerListApiCall.enqueue(new Callback<ArrayList<CustomerListDM>>() {
                @Override
                public void onResponse(Call<ArrayList<CustomerListDM>> call,
                                       Response<ArrayList<CustomerListDM>> response) {

                    if (response.isSuccessful()) {

                        // Show the list.
                        customerList.clear();
                        customerList.addAll(response.body());
                        mainActivity.get().populateCustomerList(customerList);
                    } else {
                        // API worked but the something wrong with the request.
                        mainActivity.get().showToast(response.message());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CustomerListDM>> call, Throwable t) {
                    // API request or response parsing completely failed.
                    mainActivity.get().showToast("Something went wrong");
                }
            });
        }
    }

    @Override
    public void getTableList() {

        // Make the API call only if the table list has not been saved into DB
        if (!sharedPreferences.getBoolean(IS_TABLE_LIST_SAVED, false)) {

            if (mainActivity != null) {
                tableListApiCall = quandooApiInterface.getTableList();
                tableListApiCall.enqueue(new Callback<ArrayList<Boolean>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Boolean>> call,
                                           Response<ArrayList<Boolean>> response) {

                        if (response.isSuccessful()) {

                            // Store the table list into the DB.

                            DaoSession daoSession = QuandooTaskApplication
                                    .getApplicationInstance().getDaoSession();
                            tableDao = daoSession.getTableDao();

                            for (Boolean tableReservationStatus : response.body()) {
                                Table table = new Table();
                                table.setReservationStatus(tableReservationStatus);
                                tableDao.insert(table);
                            }

                            // Mark the flag to signify that the table list has been stored in the DB.
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(IS_TABLE_LIST_SAVED, true);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Boolean>> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public void cancelCustomerListApiCall() {

        if (customerListApiCall != null)
            customerListApiCall.cancel();
    }

    @Override
    public void cancelTableListApiCall() {

        if (tableListApiCall != null)
            tableListApiCall.cancel();
    }

    @Override
    public void searchString(String query) {

        if (mainActivity != null) {

            ArrayList<CustomerListDM> queryResultList = new ArrayList<>();

            for (int i = 0; i < customerList.size(); i++)
                if (customerList.get(i).getCustomerFirstName().toLowerCase()
                        .contains(query.toLowerCase()))
                    queryResultList.add(customerList.get(i));

            mainActivity.get().populateCustomerList(queryResultList);
        }
    }

    @Override
    public void activityDestroyed() {

        quandooApiInterface = null;
        customerListApiCall = null;
        tableListApiCall = null;
        customerList = null;
        mainActivity = null;
    }
}
