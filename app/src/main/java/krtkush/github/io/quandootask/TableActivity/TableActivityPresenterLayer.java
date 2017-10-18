package krtkush.github.io.quandootask.TableActivity;

import android.content.Intent;

import java.lang.ref.WeakReference;

import krtkush.github.io.quandootask.Constants;
import krtkush.github.io.quandootask.DaoSession;
import krtkush.github.io.quandootask.QuandooTaskApplication;
import krtkush.github.io.quandootask.TableDao;

/**
 * Created by kartikeykushwaha on 17/10/17.
 */

class TableActivityPresenterLayer implements TableSelectorActivityPresenterInteractor {

    private WeakReference<TableSelectorActivity> tableSelectorActivity;
    private Integer customerID;
    private TableDao tableDao;

    TableActivityPresenterLayer(TableSelectorActivity tableSelectorActivity) {

        this.tableSelectorActivity = new WeakReference<>(tableSelectorActivity);
    }

    @Override
    public void getExtrasFromIntent(Intent intent) {

        customerID = intent.getIntExtra(Constants.CUSTOMER_ID, -1);
    }

    @Override
    public void getTableList() {

        // Get table list from the DB.
        DaoSession daoSession = QuandooTaskApplication
                .getApplicationInstance().getDaoSession();
        tableDao = daoSession.getTableDao();

        tableSelectorActivity.get().populateTableList(tableDao.loadAll());
    }
}
