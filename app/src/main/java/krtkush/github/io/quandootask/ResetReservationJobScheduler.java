package krtkush.github.io.quandootask;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import static krtkush.github.io.quandootask.Constants.JOB_SCHEDULER_LAST_RUN_TIME;
import static krtkush.github.io.quandootask.Constants.SHARED_PREFERENCES_LABEL;

/**
 * Created by kartikeykushwaha on 18/10/17.
 */

/**
 * Job scheduler to remove all reservations every 15 minutes.
 */
public class ResetReservationJobScheduler extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {

        // Initialize the shared prefs
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_LABEL,
                Context.MODE_PRIVATE);

        // Reset the reservations only if last run was at least 15 minutes ago.
        if (System.currentTimeMillis() >= sharedPreferences.getLong(JOB_SCHEDULER_LAST_RUN_TIME, 0)
                + 900000) {

            DaoSession daoSession = QuandooTaskApplication.getApplicationInstance().getDaoSession();
            TableDao tableDao = daoSession.getTableDao();

            List<Table> tableList = tableDao.queryBuilder()
                    .orderAsc(TableDao.Properties.ReservationStatus).list();

            if (tableList.size() > 0) {
                for (Table table : tableList) {
                    table.setReservationStatus(false);
                    tableDao.update(table);
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong(JOB_SCHEDULER_LAST_RUN_TIME, System.currentTimeMillis());
            editor.apply();
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
