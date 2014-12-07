package su.ias.teledoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Адаптер для взаимодействия с БД Для данного приложения в этом классе
 * создаются две таблицы: таблица с информацией о пользователях и таблица
 * отсканированных купонов
 * 
 * @author n.senchurin
 */
public class DBAdapter {


	private static final String DATABASE_NAME = "teledoc.db";
	private static final int DATABASE_VERSION = 1;


    public static final String TABLE_UNSENDED = "unsended";
    public static final String FLD_ID = "_id";
    public static final String FLD_STR_TO_POST = "str_to_post";
    public static final String FLD_URL = "url";


    // Переменная для хранения объекта БД
	private SQLiteDatabase db;

	// Контекст приложения для
	private final Context context;

	// Экземпляр вспомогательного класса для открытия и обновления БД
	private CDBHelper dbHelper;

	public DBAdapter(Context _context) {

		context = _context;
		dbHelper = new CDBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}




	/**
	 * Либо создаем новую бд, либо берем существующую
	 * 
	 * @return
	 * @throws android.database.SQLException
	 */
	public DBAdapter open() throws SQLException {

		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException e) {
			db = dbHelper.getReadableDatabase();
		}
		return this;
	}



	public void close() {
		db.close();
	}



    public void addRecord( String strToPost, String url) {
        ContentValues unsendedCV = new ContentValues();
        unsendedCV.put(FLD_STR_TO_POST, strToPost);
        unsendedCV.put(FLD_URL, url);
        db.insert(TABLE_UNSENDED, null, unsendedCV);
    }



    public void removeRec(String id ){
        String selection = FLD_ID + " = ? " ;
        String[] selectionArgs = new String[] { id};

        db.delete(TABLE_UNSENDED, selection, selectionArgs);
    }



    private static class CDBHelper extends SQLiteOpenHelper {

		public CDBHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}


		@Override
		public void onCreate(SQLiteDatabase db) {

            String regionsQuery = "CREATE TABLE " + TABLE_UNSENDED + "(" //
                    + FLD_ID + " INTEGER NOT NULL PRIMARY KEY ," //
                    + FLD_STR_TO_POST + " TEXT, "
                    + FLD_URL + " TEXT);";

            db.execSQL(regionsQuery);

		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		}

	}

}