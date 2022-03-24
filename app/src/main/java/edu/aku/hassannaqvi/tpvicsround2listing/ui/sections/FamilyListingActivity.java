package edu.aku.hassannaqvi.tpvicsround2listing.ui.sections;

import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.listings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.hassannaqvi.tpvicsround2listing.R;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts;
import edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp;
import edu.aku.hassannaqvi.tpvicsround2listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvicsround2listing.databinding.ActivityFamilyListingBinding;

public class FamilyListingActivity extends AppCompatActivity {
    private static final String TAG = "FamilyListingActivity";
    ActivityFamilyListingBinding bi;
    String st = "";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_family_listing);
        bi.setCallback(this);
        bi.setListings(listings);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        //       setupSkips();
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;

        MainApp.hhid++;
        MainApp.mwraCount = 0;

        MainApp.appendingChar = "";

/*        if (!listings.getHh02e().isEmpty()){
            if (listings.getHh02e().equals("1"))
                appendingChar = "A";
            else if ((listings.getHh02e().equals("2")))
                appendingChar = "B";
        }*/

        bi.hhid.setText("TPV-" + MainApp.listings.getHh01() + "\n" + MainApp.appendingChar + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));
        Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();


    }

/*    private void setupSkips() {

        bi.hh14.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (bi.hh1401.isChecked()) {
                    bi.addFamily.setText("Add MWRA");
                } else {
                    bi.addFamily.setText("Continue to Next");
                    Clear.clearAllFields(bi.fldGrpCVhh15);
                }
            }
        });


    }*/

    private boolean updateDB() {
        long updcount = 0;
        try {
            updcount = db.updateFormColumn(TableContracts.ListingsTable.COLUMN_LC, listings.lCtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, R.string.upd_db_form + e.getMessage());
            Toast.makeText(this, R.string.upd_db_form + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount > 0) return true;
        else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean insertRecord() {
        long rowId = 0;

        try {
            rowId = db.addForm(listings);

            if (rowId > 0) {
                long updCount = 0;

                listings.setId(String.valueOf(rowId));
                listings.setUid(listings.getDeviceId() + listings.getId());

                updCount = db.updateFormColumn(TableContracts.ListingsTable.COLUMN_UID, listings.getUid());

                if (updCount > 0) {
                    return updateDB();
                }

            } else {
                Toast.makeText(this, "Updating Database… ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(CR):" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;

    }


    public void btnContinue(View view) {
        if (!formValidation()) return;
        //saveDraft();
        if (MainApp.hhid == 1 ? updateDB() : insertRecord()) {
            finish();
            if (MainApp.hhid < Integer.parseInt(MainApp.listings.getHh10())) {
                //   Toast.makeText(this, "Staring Family", Toast.LENGTH_SHORT).show();
                listings.setHh12("");
                listings.setHh14("");
                startActivity(new Intent(this, FamilyListingActivity.class));

            } else {
                //     Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SectionBActivity.class));

            }
        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
    }

    private void saveDraft() {
        // listings = new Listings();
//        listings.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
//        //  listings.setUserName(MainApp.user.getUserName());
//        // listings.setDeviceId(MainApp.appInfo.getDeviceID());
//        // listings.setDeviceTag(MainApp.appInfo.getTagName());
//        // listings.setAppver(MainApp.appInfo.getAppVersion());
//        // listings.setStartTime(st);
//        // listings.setEndTime(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
//
//        listings.setHh11(bi.hh11.getText().toString());
//
//        //listings.setHh12(bi.hh12.getText().toString());
//
//        listings.setHh13(bi.hh13.getText().toString());
//        listings.setHh13a(bi.hh13a.getText().toString());
//
//        listings.setHh14(bi.hh1401.isChecked() ? "1"
//                : bi.hh1402.isChecked() ? "2"
//                : "-1");
//
//        listings.setHh15(bi.hh15.getText().toString());
//
///*        listings.setHh16(bi.hh16.getText().toString());
//
//        listings.setHh17(bi.hh17.getText().toString());
//
//        listings.setHh18(bi.hh18.getText().toString());
//
//        listings.setHh19(bi.hh19.getText().toString());*/
//        listings.setHh21(String.valueOf(MainApp.hhid));
//
//        try {
//            listings.setlC(listings.lCtoString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "JSONException(listings): " + e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

    }


    public void btnEnd(View view) {
        bi.hh11.setText("Deleted");
        //bi.hh12.setText("Deleted");
        bi.hh13.setText("Deleted");
        bi.hh14.clearCheck();
        bi.hh15.setText("00");

        //saveDraft();
        if (updateDB()) {
            finish();

            //     Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SectionBActivity.class));

        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
    }


    private boolean formValidation() {
        if (!Validator.emptyCheckingContainer(this, bi.GrpName)) {
            return false;
        }

        if (!bi.hh15.getText().toString().equals("") && !bi.hh13a.getText().toString().equals("")) {
            if (Integer.parseInt(bi.hh15.getText().toString()) > Integer.parseInt(bi.hh13a.getText().toString())) {
                Validator.emptyCustomTextBox(this, bi.hh15, "Number of children younger than 5 year old cannot be more than Total Members");
                return false;
            }
        }

        if (!bi.hh13.getText().toString().equals("") && !bi.hh15.getText().toString().equals("")) {
            if (Integer.parseInt(bi.hh13.getText().toString()) >= Integer.parseInt(bi.hh15.getText().toString())) {
                Validator.emptyCustomTextBox(this, bi.hh13, "Number of 12 to 23 months old children cannot be more or equal to Number of children younger than 5 year old");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
      /*  finish();
        startActivity(new Intent(this, MainActivity.class));*/
    }
}
