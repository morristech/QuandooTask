package krtkush.github.io.quandootask.TableActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import krtkush.github.io.quandootask.R;
import krtkush.github.io.quandootask.Table;

public class TableSelectorActivity extends AppCompatActivity {

    private TableSelectorActivityPresenterInteractor presenterInteractor;
    private List<Table> tableList = new ArrayList<>();
    private TableListAdapter tableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_selector);

        presenterInteractor = new TableActivityPresenterLayer(this);

        initViews();
        presenterInteractor.getExtrasFromIntent(getIntent());
        presenterInteractor.getTableList();
    }

    private void initViews() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tableRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Prepare the adapter
        tableListAdapter = new TableListAdapter(tableList, this);
        recyclerView.setAdapter(tableListAdapter);
    }

    protected void populateTableList(List<Table> tableList) {

        this.tableList.clear();
        this.tableList.addAll(tableList);
        tableListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {

        presenterInteractor = null;
        super.onDestroy();
    }
}
