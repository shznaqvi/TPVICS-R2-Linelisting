package edu.aku.hassannaqvi.tpvicsround2listing.database;

import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.IBAHC;
import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.PROJECT_NAME;
import static edu.aku.hassannaqvi.tpvicsround2listing.core.UserAuth.checkPassword;
import static edu.aku.hassannaqvi.tpvicsround2listing.database.CreateTable.SQL_CREATE_CLUSTERS;
import static edu.aku.hassannaqvi.tpvicsround2listing.database.CreateTable.SQL_CREATE_ENTRYLOGS;
import static edu.aku.hassannaqvi.tpvicsround2listing.database.CreateTable.SQL_CREATE_FORM;
import static edu.aku.hassannaqvi.tpvicsround2listing.database.CreateTable.SQL_CREATE_MWRA;
import static edu.aku.hassannaqvi.tpvicsround2listing.database.CreateTable.SQL_CREATE_USERS;
import static edu.aku.hassannaqvi.tpvicsround2listing.database.CreateTable.SQL_CREATE_VERSIONAPP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;
import android.widget.Toast;

import net.sqlcipher.SQLException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts.ClusterTable;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts.EntryLogTable;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts.FormTable;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts.MwraTable;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts.UsersTable;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts.VersionTable;
import edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Cluster;
import edu.aku.hassannaqvi.tpvicsround2listing.models.EntryLog;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Form;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Mwra;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Users;
import edu.aku.hassannaqvi.tpvicsround2listing.models.VersionApp;



/*import edu.aku.hassannaqvi.naunehal.models.Immunization;*/

/**
 * @author hassan.naqvi on 11/30/2016.
 * @update ali azaz on 01/07/21
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = PROJECT_NAME + ".db";
    public static final String DATABASE_COPY = PROJECT_NAME + "_copy.db";
    private static final int DATABASE_VERSION = 1;
    private final String TAG = "DatabaseHelper";
    private static final String DATABASE_PASSWORD = IBAHC;
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_CLUSTERS);
        db.execSQL(SQL_CREATE_FORM);
        db.execSQL(SQL_CREATE_MWRA);
        db.execSQL(SQL_CREATE_ENTRYLOGS);
        db.execSQL(SQL_CREATE_VERSIONAPP);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
            case 2:
        }
    }


    //ADDITION in DB
    public Long addForm(Form cr) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormTable.COLUMN_PROJECT_NAME, cr.getProjectName());
        values.put(FormTable.COLUMN_UID, cr.getUid());
        values.put(FormTable.COLUMN_USERNAME, cr.getUserName());
        values.put(FormTable.COLUMN_CLUSTER, cr.getCluster());
        values.put(FormTable.COLUMN_SYSDATE, cr.getSysDate());
        values.put(FormTable.COLUMN_ISTATUS, cr.getiStatus());
        values.put(FormTable.COLUMN_DEVICETAGID, cr.getDeviceTag());
        values.put(FormTable.COLUMN_DEVICEID, cr.getDeviceId());
        values.put(FormTable.COLUMN_APPVERSION, cr.getAppver());
        values.put(FormTable.COLUMN_START_TIME, cr.getStartTime());
        values.put(FormTable.COLUMN_END_TIME, cr.getEndTime());

        // Put all JSON as xxtoString()
        values.put(FormTable.COLUMN_SA, cr.sAtoString());
        values.put(FormTable.COLUMN_SB, cr.sBtoString());
        // values.put(FormTable.COLUMN_LC, cr.lCtoString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormTable.TABLE_NAME,
                FormTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    //ADDITION in DB
    public Long addMwra(Mwra mwra) throws JSONException {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MwraTable.COLUMN_PROJECT_NAME, mwra.getProjectName());
        values.put(MwraTable.COLUMN_UID, mwra.getUid());
        values.put(MwraTable.COLUMN_UUID, mwra.getUuid());
        values.put(MwraTable.COLUMN_USERNAME, mwra.getUserName());
        values.put(MwraTable.COLUMN_SYSDATE, mwra.getSysDate());
        values.put(MwraTable.COLUMN_ISTATUS, mwra.getiStatus());
        values.put(MwraTable.COLUMN_DEVICETAGID, mwra.getDeviceTag());
        values.put(MwraTable.COLUMN_DEVICEID, mwra.getDeviceId());
        values.put(MwraTable.COLUMN_APPVERSION, mwra.getAppver());
        values.put(MwraTable.COLUMN_START_TIME, mwra.getStartTime());
        values.put(MwraTable.COLUMN_END_TIME, mwra.getEndTime());
        // Put all JSON as xxtoString()
        values.put(MwraTable.COLUMN_SMWRA, mwra.sMwratoString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MwraTable.TABLE_NAME,
                MwraTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    public Long addEntryLog(EntryLog entryLog) throws SQLiteException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(EntryLogTable.COLUMN_PROJECT_NAME, entryLog.getProjectName());
        values.put(EntryLogTable.COLUMN_UUID, entryLog.getUuid());
        values.put(EntryLogTable.COLUMN_PSU_CODE, entryLog.getPsuCode());
        values.put(EntryLogTable.COLUMN_HHID, entryLog.getHhid());
        values.put(EntryLogTable.COLUMN_USERNAME, entryLog.getUserName());
        values.put(EntryLogTable.COLUMN_SYSDATE, entryLog.getSysDate());
        values.put(EntryLogTable.COLUMN_ISTATUS, entryLog.getiStatus());
        values.put(EntryLogTable.COLUMN_ISTATUS96x, entryLog.getiStatus96x());
        values.put(EntryLogTable.COLUMN_ENTRY_TYPE, entryLog.getEntryType());
        values.put(EntryLogTable.COLUMN_ENTRY_DATE, entryLog.getEntryDate());
        values.put(EntryLogTable.COLUMN_DEVICEID, entryLog.getDeviceId());
        values.put(EntryLogTable.COLUMN_SYNCED, entryLog.getSynced());
        values.put(EntryLogTable.COLUMN_SYNC_DATE, entryLog.getSyncDate());
        values.put(EntryLogTable.COLUMN_APPVERSION, entryLog.getAppver());

        long newRowId;
        newRowId = db.insertOrThrow(
                EntryLogTable.TABLE_NAME,
                EntryLogTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    //UPDATE in DB
    public int updateFormColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.form.getId())};

        return db.update(FormTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormTable.COLUMN_ISTATUS, MainApp.form.getiStatus());

        // Which row to update, based on the ID
        String selection = FormTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.form.getId())};

        return db.update(FormTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    //UPDATE in DB
    public int updateMwraColumn(String column, String value) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = MwraTable._ID + " =? ";
        String[] selectionArgs = {String.valueOf(MainApp.mwra.getId())};

        return db.update(MwraTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updatesEntryLogColumn(String column, String value, String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = EntryLogTable._ID + " =? ";
        String[] selectionArgs = {id};

        return db.update(EntryLogTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    /*    public int updateEnding() {
            SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

            // New value for one column
            ContentValues values = new ContentValues();
            values.put(MwraTable.COLUMN_ISTATUS, MainApp.mwra.getiStatus());

            // Which row to update, based on the ID
            String selection = MwraTable.COLUMN_ID + " =? ";
            String[] selectionArgs = {String.valueOf(MainApp.mwra.getId())};

            return db.update(MwraTable.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }*/
    /*
     * Functions that dealing with table data
     * */
    public boolean doLogin(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause = UsersTable.COLUMN_USERNAME + "=? ";
        String[] whereArgs = {username};
        String groupBy = null;
        String having = null;
        String orderBy = UsersTable.COLUMN_ID + " ASC";

        Users loggedInUser = new Users();
        c = db.query(
                UsersTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            loggedInUser = new Users().hydrate(c);

        }

        c.close();

        db.close();
        if (loggedInUser.getPassword().equals("")) {
            Toast.makeText(mContext, "Stored password is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (checkPassword(password, loggedInUser.getPassword())) {
            MainApp.user = loggedInUser;
            //  MainApp.selectedDistrict = loggedInUser.getDist_id();
            return c.getCount() > 0;
        } else {
            return false;
        }
    }


    public ArrayList<Form> getFormsByDate(String sysdate) {

        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = {
                FormTable._ID,
                FormTable.COLUMN_UID,
                FormTable.COLUMN_SYSDATE,
                FormTable.COLUMN_USERNAME,
                FormTable.COLUMN_CLUSTER,
                FormTable.COLUMN_ISTATUS,
                FormTable.COLUMN_SYNCED,

        };
        String whereClause = FormTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
        String groupBy = null;
        String having = null;
        String orderBy = FormTable.COLUMN_ID + " ASC";
        ArrayList<Form> allForms = new ArrayList<>();
        try {
            c = db.query(
                    FormTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                Form forms = new Form();
                forms.setId(c.getString(c.getColumnIndexOrThrow(FormTable.COLUMN_ID)));
                forms.setUid(c.getString(c.getColumnIndexOrThrow(FormTable.COLUMN_UID)));
                forms.setSysDate(c.getString(c.getColumnIndexOrThrow(FormTable.COLUMN_SYSDATE)));
                forms.setUserName(c.getString(c.getColumnIndexOrThrow(FormTable.COLUMN_USERNAME)));
                forms.setCluster(c.getString(c.getColumnIndexOrThrow(FormTable.COLUMN_CLUSTER)));
                allForms.add(forms);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allForms;
    }


    // istatus examples: (1) or (1,9) or (1,3,5)
    public Form getFormByAssessNo(String uid, String istatus) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormTable.COLUMN_UID + "=? AND " +
                FormTable.COLUMN_ISTATUS + " in " + istatus;

        String[] whereArgs = {uid};

        String groupBy = null;
        String having = null;

        String orderBy = FormTable.COLUMN_ID + " ASC";

        Form allFC = null;
        try {
            c = db.query(
                    FormTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allFC = new Form().Hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public ArrayList<Cursor> getDatabaseManagerData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    /*public int updateTemp(String assessNo, String temp) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_TSF305, temp);
        values.put(FormsTable.COLUMN_SYSDATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()) + "-Updated");
        values.put(FormsTable.COLUMN_ISTATUS, "1");
        values.put(FormsTable.COLUMN_SYNCED, (byte[]) null);

        String selection = FormsTable.COLUMN_ASSESMENT_NO + " =? AND " + FormsTable.COLUMN_ISTATUS + " =? ";
        // String selection = FormsTable.COLUMN_ASSESMENT_NO + " =? ";
        String[] selectionArgs = {assessNo, "9"};
        // String[] selectionArgs = {assessNo};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }*/


    public int syncVersionApp(JSONObject VersionList) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(VersionTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) VersionList.get(VersionTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionApp Vc = new VersionApp();
            Vc.sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public int syncAppUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                Users user = new Users();
                user.sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersTable.COLUMN_USERNAME, user.getUserName());
                values.put(UsersTable.COLUMN_PASSWORD, user.getPassword());
                values.put(UsersTable.COLUMN_FULLNAME, user.getFullname());
                values.put(UsersTable.COLUMN_DESIGNATION, user.getDesignation());
                values.put(UsersTable.COLUMN_ENABLED, user.getEnabled());
                values.put(UsersTable.COLUMN_ISNEW_USER, user.getNewUser());
                values.put(UsersTable.COLUMN_PWD_EXPIRY, user.getPwdExpiry());
                values.put(UsersTable.COLUMN_DIST_ID, user.getDist_id());
                long rowID = db.insert(UsersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }


    public int syncClusters(JSONArray clusterList) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(ClusterTable.TABLE_NAME, null, null);
        int insertCount = 0;

        for (int i = 0; i < clusterList.length(); i++) {

            JSONObject json = clusterList.getJSONObject(i);

            Cluster cluster = new Cluster();
            cluster.sync(json);
            ContentValues values = new ContentValues();

            values.put(ClusterTable.COLUMN_GEOAREA, cluster.getGeoarea());
            values.put(ClusterTable.COLUMN_DIST_ID, cluster.getDistId());
            values.put(ClusterTable.COLUMN_EB_CODE, cluster.getEbcode());
            long rowID = db.insert(ClusterTable.TABLE_NAME, null, values);
            if (rowID != -1) insertCount++;
        }


        db.close();

        return insertCount;
    }

/*    public int syncRandom(JSONArray list) {
        SQLiteDatabase db = this.getWritableDatabase(DATABASE_PASSWORD);
        db.delete(RandomTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < list.length(); i++) {

                JSONObject json = list.getJSONObject(i);

                RandomHH ran = new RandomHH();
                ran.sync(json);
                ContentValues values = new ContentValues();
                values.put(RandomTable.COLUMN_ID, ran.getID());
                values.put(RandomTable.COLUMN_SNO, ran.getSno());
                values.put(RandomTable.COLUMN_CLUSTER_NO, ran.getClusterNo());
                values.put(RandomTable.COLUMN_HH_NO, ran.getHhno());
                values.put(RandomTable.COLUMN_HEAD_HH, ran.getHeadhh());
                long rowID = db.insert(RandomTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncRandom(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

*/


    //get UnSyncedTables
    public JSONArray getUnsyncedForm() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = FormTable.COLUMN_ID + " ASC";

        JSONArray allForm = new JSONArray();
        try {
            c = db.query(
                    FormTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                /** WorkManager Upload
                 /*Form fc = new Form();
                 allFC.add(fc.Hydrate(c));*/
                Log.d(TAG, "getUnsyncedForm: " + c.getCount());
                Form form = new Form();
                allForm.put(form.Hydrate(c).toJSONObject());
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.d(TAG, "getUnsyncedForm: " + allForm.toString().length());
        Log.d(TAG, "getUnsyncedForm: " + allForm);
        return allForm;
    }

    //get UnSyncedTables
    public JSONArray getUnsyncedMwra() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = MwraTable.COLUMN_SYNCED + " is null ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = MwraTable.COLUMN_ID + " ASC";

        JSONArray allMwra = new JSONArray();
        try {
            c = db.query(
                    MwraTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                /** WorkManager Upload
                 /*Mwra fc = new Mwra();
                 allFC.add(fc.Hydrate(c));*/
                Log.d(TAG, "getUnsyncedMwraCR: " + c.getCount());
                Mwra mwra = new Mwra();
                allMwra.put(mwra.Hydrate(c).toJSONObject());
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        Log.d(TAG, "getUnsyncedMwraCR: " + allMwra.toString().length());
        Log.d(TAG, "getUnsyncedMwraCR: " + allMwra);
        return allMwra;
    }

    //ENTRYLOG
    public JSONArray getUnsyncedEntryLog() throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;
        String whereClause;
        whereClause = EntryLogTable.COLUMN_SYNCED + " = '' ";

        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = EntryLogTable.COLUMN_ID + " ASC";

        JSONArray all = new JSONArray();
        c = db.query(
                EntryLogTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {
            Log.d(TAG, "getUnsyncedEntryLog: " + c.getCount());
            EntryLog entryLog = new EntryLog();
            all.put(entryLog.Hydrate(c).toJSONObject());
        }
        Log.d(TAG, "getUnsyncedEntryLog: " + all.toString().length());
        Log.d(TAG, "getUnsyncedEntryLog: " + all);
        return all;
    }


    //update SyncedTables
    public void updateSyncedForm(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(FormTable.COLUMN_SYNCED, true);
        values.put(FormTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = FormTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    //update SyncedTables
    public void updateSyncedmwra(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

// New value for one column
        ContentValues values = new ContentValues();
        values.put(MwraTable.COLUMN_SYNCED, true);
        values.put(MwraTable.COLUMN_SYNCED_DATE, new Date().toString());

// Which row to update, based on the title
        String where = MwraTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                MwraTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }


/*    public void updateSyncedSamp(String id) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        ContentValues values = new ContentValues();
        values.put(SamplesTable.COLUMN_SYNCED, true);
        values.put(SamplesTable.COLUMN_SYNCED_DATE, new Date().toString());

        String where = SamplesTable.COLUMN_ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                SamplesTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }*/


    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase(DATABASE_PASSWORD);
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }


    public Cluster getClusters(String ebCode) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause = ClusterTable.COLUMN_EB_CODE + " = ?";

        String[] whereArgs = {ebCode};

        String groupBy = null;
        String having = null;

        String orderBy = ClusterTable.COLUMN_EB_CODE + " ASC";

        Cluster e = new Cluster();
        try {
            c = db.query(
                    ClusterTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy
            );
            while (c.moveToNext()) {
                e = new Cluster().hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return e;

    }

    public List<Cluster> getClusters() {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause = null;

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy = ClusterTable.COLUMN_EB_CODE + " ASC";

        List<Cluster> e = new ArrayList<>();
        try {
            c = db.query(
                    ClusterTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy
            );
            while (c.moveToNext()) {
                e.add(new Cluster().hydrate(c));

            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return e;

    }

    public List<Form> getListingsByCluster(String cluster) throws JSONException {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);
        Cursor c = null;
        String[] columns = null;

        String whereClause;
        whereClause = FormTable.COLUMN_CLUSTER + " =? ";

        String[] whereArgs = {cluster};

        String groupBy = null;
        String having = null;

        String orderBy = FormTable.COLUMN_ID + " ASC";

        List<Form> allListing = new ArrayList<>();
        c = db.query(
                FormTable.TABLE_NAME,  // The table to query
                columns,                   // The columns to return
                whereClause,               // The columns for the WHERE clause
                whereArgs,                 // The values for the WHERE clause
                groupBy,                   // don't group the rows
                having,                    // don't filter by row groups
                orderBy                    // The sort order
        );
        while (c.moveToNext()) {

            allListing.add(new Form().Hydrate(c));
        }
        c.close();
        db.close();


        return allListing;
    }

    public int updatePassword(String hashedPassword) {
        SQLiteDatabase db = this.getReadableDatabase(DATABASE_PASSWORD);

        ContentValues values = new ContentValues();
        values.put(UsersTable.COLUMN_PASSWORD, hashedPassword);
        values.put(UsersTable.COLUMN_ISNEW_USER, "");

        String selection = UsersTable.COLUMN_USERNAME + " =? ";
        String[] selectionArgs = {MainApp.user.getUserName()};

        return db.update(UsersTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}