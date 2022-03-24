package edu.aku.hassannaqvi.tpvicsround2listing.ui;

import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.listings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import edu.aku.hassannaqvi.tpvicsround2listing.MainActivity;
import edu.aku.hassannaqvi.tpvicsround2listing.R;
import edu.aku.hassannaqvi.tpvicsround2listing.contracts.TableContracts;
import edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp;
import edu.aku.hassannaqvi.tpvicsround2listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvicsround2listing.databinding.ActivityEndingBinding;


public class EndingActivity extends AppCompatActivity {

    ActivityEndingBinding bi;
    int sectionMainCheck;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending);
        bi.setListings(listings);
        setSupportActionBar(bi.toolbar);
        setSupportActionBar(bi.toolbar);
        //setTitle(R.string.section1_mainheading);

        db = MainApp.appInfo.dbHelper;
        boolean check = getIntent().getBooleanExtra("complete", false);
        //sectionMainCheck = getIntent().getIntExtra("status", 0);


        bi.istatusa.setEnabled(check && !listings.getiStatus().equals("9")); // listings is complete and  patient not on hold
        bi.istatusb.setEnabled(!check);
        bi.istatusc.setEnabled(check && listings.getiStatus().equals("9")); // listings is complete and  patient not on hold


    }

    private void saveDraft() {
        listings.setiStatus(bi.istatusa.isChecked() ? "1"
                : bi.istatusb.isChecked() ? "2"
                : bi.istatusc.isChecked() ? "9"
                : "-1");
        // listings.setEndTime(new SimpleDateFormat("dd-MM-yy HH:mm", Locale.ENGLISH).format(new Date().getTime()));
    }


    public void BtnEnd(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (UpdateDB()) {

            cleanupProcess();
            finish();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            Toast.makeText(this, "Data has been updated.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Error in updating Database.", Toast.LENGTH_SHORT).show();
        }
    }


    private void cleanupProcess() {
        listings = null;
    }


    private boolean UpdateDB() {
        int updcount = db.updateFormColumn(TableContracts.ListingsTable.COLUMN_ISTATUS, listings.getiStatus());
        return updcount > 0;
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
    }

}
