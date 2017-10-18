package krtkush.github.io.quandootask.MainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import krtkush.github.io.quandootask.NetworkClient.CustomerListDM;
import krtkush.github.io.quandootask.R;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenterInteractor presenterInteractor;
    private ArrayList<CustomerListDM> customerList = new ArrayList<>();
    private CustomerListAdapter customerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenterInteractor = new MainActivityPresenterLayer(this);
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenterInteractor.getCustomerList();
        presenterInteractor.getTableList();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenterInteractor.cancelCustomerListApiCall();
    }

    @Override
    protected void onDestroy() {

        presenterInteractor.activityDestroyed();
        presenterInteractor = null;
        super.onDestroy();
    }

    /**
     * Initiate all the views of this activity.
     */
    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        EditText searchInputField = (EditText) findViewById(R.id.searchInputField);

        // Prepare the recycler view.
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Prepare the adapter
        customerListAdapter = new CustomerListAdapter(customerList, this);
        recyclerView.setAdapter(customerListAdapter);

        // Add text change listener to search
        searchInputField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenterInteractor.searchString(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Method to populate the customer list.
     * @param customerList
     */
    protected void populateCustomerList(ArrayList<CustomerListDM> customerList) {

        this.customerList.clear();
        this.customerList.addAll(customerList);
        customerListAdapter.notifyDataSetChanged();
    }

    /**
     * A wrapper method to show a toast message.
     * @param message
     */
    protected void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
