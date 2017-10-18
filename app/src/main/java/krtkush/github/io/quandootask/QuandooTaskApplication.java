package krtkush.github.io.quandootask;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import krtkush.github.io.quandootask.NetworkClient.QuandooApi;

/**
 * Created by kartikeykushwaha on 16/10/17.
 */

public class QuandooTaskApplication extends Application {

    // Application instance.
    private static QuandooTaskApplication quandooTaskApplicationInstance;

    // API interface
    private QuandooApi.QuandooApiInterface quandooApiInterface;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        quandooTaskApplicationInstance = this;

        // Initialize the DB
        daoSession = new DaoMaster(new DaoMaster
                .DevOpenHelper(this, "quandoo.db")
                .getWritableDb()).newSession();

        // Initialize the API interfaces.
        quandooApiInterface = QuandooApi.getQuandooApi();

        // Initiate the job scheduler.
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1,
                new ComponentName(getPackageName(),
                        ResetReservationJobScheduler.class.getName()));
        builder.setPeriodic(900000);    // Run every 15 minutes.
        jobScheduler.schedule(builder.build());
    }

    public QuandooApi.QuandooApiInterface getQuandooApiInterface() {
        return quandooApiInterface;
    }

    public static QuandooTaskApplication getApplicationInstance() {
        return quandooTaskApplicationInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
