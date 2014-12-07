package su.ias.teledoc;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;

public class AppSingleton extends Application {




	private static AppSingleton singleton;
    private DBAdapter dbAdapter;
    public SharedPreferences prefs;


    public static AppSingleton getInstance() {
		return singleton;
	}


	@Override
	public final void onCreate() {
		super.onCreate();
		singleton = this;

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

	}



    public DBAdapter getDBAdapter() {
        initDB();
        return dbAdapter;
    }



    /**
     * Инициализирует БД
     */
    private void initDB() {
        if (dbAdapter == null) {
            try {
                dbAdapter = new DBAdapter(this).open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Получение статуса подключения
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }












}
