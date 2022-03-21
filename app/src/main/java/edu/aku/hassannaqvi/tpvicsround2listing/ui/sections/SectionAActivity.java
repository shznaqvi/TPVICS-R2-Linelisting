package edu.aku.hassannaqvi.tpvicsround2listing.ui.sections;

import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.form;
import static edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp.sharedPref;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.aku.hassannaqvi.tpvicsround2listing.MainActivity;
import edu.aku.hassannaqvi.tpvicsround2listing.R;
import edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp;
import edu.aku.hassannaqvi.tpvicsround2listing.database.DatabaseHelper;
import edu.aku.hassannaqvi.tpvicsround2listing.databinding.ActivitySectionABinding;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Cluster;
import edu.aku.hassannaqvi.tpvicsround2listing.models.Form;

public class SectionAActivity extends AppCompatActivity {
    private static final String TAG = "SectionCRActivity";
    ActivitySectionABinding bi;
    String st = "";
    private DatabaseHelper db;
    private ArrayList<String> ebCode, districtNames, tehsilNames, cityNames, headHH;
    Cluster cluster = new Cluster();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_a);
        bi.setCallback(this);
        st = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime());
        setupSkips();
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;

        populateSpinner();

        bi.hh01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 9) {
                    bi.searchEB.setBackgroundColor(getResources().getColor(R.color.greenLight));
                    bi.searchEB.setEnabled(true);
                } else {
                    bi.searchEB.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                    bi.searchEB.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bi.hh04.setText(null);
                bi.hh05.setText(null);
                bi.hh04.setError(null);
                bi.hh05.setError(null);
                bi.hh06.setText(null);
                bi.ebMsg.setText(null);

            }
        });

        bi.hh02d1.setOnCheckedChangeListener((compoundButton, b) -> {
            if (!cluster.getEbcode().equals("") && b) {
                 bi.openForm.setEnabled(true);
                bi.openForm.setVisibility(View.VISIBLE);

            }else{
                bi.openForm.setEnabled(false);
                bi.openForm.setVisibility(View.GONE);
            }
        });

    }


    private void setupSkips() {
    }

    private void populateSpinner() {
        List<Cluster> clustersList = db.getClusters();
        ebCode = new ArrayList<>();
        districtNames = new ArrayList<>();
        tehsilNames = new ArrayList<>();
        cityNames = new ArrayList<>();
        for (Cluster c : clustersList) {
            ebCode.add(c.getEbcode());
            String[] geoArea = c.getGeoarea().split("\\|");
            districtNames.add(geoArea[1]);
            tehsilNames.add(geoArea[2]);
            cityNames.add(geoArea[3]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, ebCode);

        bi.hh01.setAdapter(adapter);
/*
        bi.hh01.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int position = ebCode.indexOf(bi.hh01.getText().toString());
                bi.hh04.setText(districtNames.get(position));
                bi.hh05.setText(tehsilNames.get(position));

                ArrayList<String> households = new ArrayList<String>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SectionAActivity.this,
                        android.R.layout.simple_dropdown_item_1line, households);

                bi.hh01.setAdapter(adapter);

            }
        });*/


    }

    private boolean insertRecord() {
        MainApp.selectedCluster = bi.hh01.getText().toString();
        MainApp.maxStructure = Integer.parseInt(sharedPref.getString(MainApp.selectedCluster, "0"));


        return true;
       /* long rowId = 0;

        try {
            rowId = db.addCR(form);

            if (rowId > 0) {
                long updCount = 0;

                form.setId(String.valueOf(rowId));
                form.setUid(form.getDeviceId() + form.getId());

                updCount = db.updateCrColumn(TableContracts.FormTable.COLUMN_UID, form.getUid());

                if (updCount > 0) {
                    return true;
                }

            } else {
                Toast.makeText(this, "Updating Database… ERROR!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(CR):" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return false;*/
    }


    public void btnContinue(View view) {
        if (!formValidation()) return;
        saveDraft();
        if (insertRecord()) {
            finish();
            startActivity(new Intent(this, SectionBActivity.class));
        } else Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
    }


    private void saveDraft() {
        form = new Form();
        form.setSysDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));
        form.setUserName(MainApp.user.getUserName());
        form.setDeviceId(MainApp.appInfo.getDeviceID());
        form.setDeviceTag(MainApp.appInfo.getTagName());
        form.setAppver(MainApp.appInfo.getAppVersion());
        form.setStartTime(st);
        form.setEndTime(new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new Date().getTime()));


        form.setHh01(bi.hh01.getText().toString());

/*        form.setHh02(bi.hh02.getText().toString());

        form.setHh03(bi.hh03.getText().toString());*/

        form.setHh04(bi.hh04.getText().toString());

        form.setHh05(bi.hh05.getText().toString());

        form.setHh06(bi.hh06.getText().toString());

        try {
            form.setsA(form.sAtoString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "JSONException(SA): " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void btnEnd(View view) {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

    @Override
    public void onBackPressed() {
        // Toast.makeText(getApplicationContext(), "Back Press Not Allowed", Toast.LENGTH_LONG).show();
        finish();
        //  startActivity(new Intent(this, MainActivity.class));
    }

    public void searchEB(View view) {
        bi.openForm.setEnabled(false);
        Cluster testEb = new Cluster();
        testEb.setEbcode("909090909");
        testEb.setGeoarea("|Test District|Test Tehsil|Test City");

        if (!bi.hh01.getText().toString().equals(testEb.getEbcode())) {
            cluster = db.getClusters(bi.hh01.getText().toString());
            MainApp.selectedCluster = bi.hh01.getText().toString();
            MainApp.maxStructure = Integer.parseInt(sharedPref.getString(MainApp.selectedCluster, "0"));
            bi.ebMsg.setText("Existing structures: " + MainApp.maxStructure);
        } else {
            cluster = testEb;
            MainApp.selectedCluster = null;
            MainApp.maxStructure = 0;
            bi.ebMsg.setText(null);

        }

       /* ebCode = new ArrayList<>();
        districtNames = new ArrayList<>();
        tehsilNames = new ArrayList<>();
        for (Cluster eb : clusters) {
            ebCode.add(eb.getCluster());
            districtNames.add(eb.getDistrictName());
            tehsilNames.add(eb.getTehsilName()); //
        }*/
        if (!cluster.getEbcode().equals("")) {
            bi.hh04.setError(null);
            bi.hh05.setError(null);
            bi.hh06.setError(null);

            String[] geoArea = cluster.getGeoarea().split("\\|");
            bi.hh04.setText(geoArea[1]);
            bi.hh05.setText(geoArea[2]);
            bi.hh06.setText(geoArea[3]);

            if (bi.hh02d1.isChecked())
                bi.openForm.setEnabled(true);
                bi.openForm.setVisibility(View.VISIBLE);

            //     bi.fldGrpHH.setVisibility(View.VISIBLE);

        } else {
            bi.hh04.setError("Not Found!");
            bi.hh05.setError("Not Found!");
            bi.hh06.setText("Not Found!");
            bi.openForm.setEnabled(false);

//            bi.fldGrpHH.setVisibility(View.GONE);
        }
    }
}