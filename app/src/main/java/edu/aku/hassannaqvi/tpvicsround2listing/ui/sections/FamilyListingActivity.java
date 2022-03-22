package edu.aku.hassannaqvi.tpvicsround2listing.ui.sections;

import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.form;

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
import edu.aku.hassannaqvi.tpvicsround2listing.models.Form;

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
        bi.setForm(form);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        //       setupSkips();
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;

        MainApp.hhid++;
        MainApp.mwraCount = 0;

        String appendingChar = "";

        if (!form.getHh02e().isEmpty()){
            if (form.getHh02e().equals("1"))
                appendingChar = "A";
            else if ((form.getHh02e().equals("2")))
                appendingChar = "B";
        }

        bi.hhid.setText("TPV-"+MainApp.form.getHh01() + "\n" + appendingChar + "-" + String.format("%04d", MainApp.maxStructure) + "-" + String.format("%03d", MainApp.hhid));
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
            updcount = db.updateFormColumn(TableContracts.FormTable.COLUMN_LC, form.lCtoString());
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
            rowId = db.addForm(form);

            if (rowId > 0) {
                long updCount = 0;

                form.setId(String.valueOf(rowId));
                form.setUid(form.getDeviceId() + form.getId());

                updCount = db.updateFormColumn(TableContracts.FormTable.COLUMN_UID, form.getUid());

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
            if (MainApp.hhid < Integer.parseInt(MainApp.form.getHh10())) {
                //   Toast.makeText(this, "Staring Family", Toast.LENGTH_SHORT).show();
                form.setHh12("");
                form.setHh14("");
                startActivity(new Intent(this, FamilyListingActivity.class));

            } else {
                //     Toast.makeText(this, "Staring Household", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, SectionBActivity.class));

            }
        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
    }

    private void saveDraft() {
        // form = new Form();
//        form.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
//        //  form.setUserName(MainApp.user.getUserName());
//        // form.setDeviceId(MainApp.appInfo.getDeviceID());
//        // form.setDeviceTag(MainApp.appInfo.getTagName());
//        // form.setAppver(MainApp.appInfo.getAppVersion());
//        // form.setStartTime(st);
//        // form.setEndTime(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
//
//        form.setHh11(bi.hh11.getText().toString());
//
//        //form.setHh12(bi.hh12.getText().toString());
//
//        form.setHh13(bi.hh13.getText().toString());
//        form.setHh13a(bi.hh13a.getText().toString());
//
//        form.setHh14(bi.hh1401.isChecked() ? "1"
//                : bi.hh1402.isChecked() ? "2"
//                : "-1");
//
//        form.setHh15(bi.hh15.getText().toString());
//
///*        form.setHh16(bi.hh16.getText().toString());
//
//        form.setHh17(bi.hh17.getText().toString());
//
//        form.setHh18(bi.hh18.getText().toString());
//
//        form.setHh19(bi.hh19.getText().toString());*/
//        form.setHh21(String.valueOf(MainApp.hhid));
//
//        try {
//            form.setlC(form.lCtoString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "JSONException(form): " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
