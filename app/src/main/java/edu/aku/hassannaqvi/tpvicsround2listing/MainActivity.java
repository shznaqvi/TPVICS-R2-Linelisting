package edu.aku.hassannaqvi.tpvicsround2listing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.aku.hassannaqvi.tpvicsround2listing.core.MainApp;
import edu.aku.hassannaqvi.tpvicsround2listing.database.AndroidManager;
import edu.aku.hassannaqvi.tpvicsround2listing.databinding.ActivityMainBinding;
import edu.aku.hassannaqvi.tpvicsround2listing.ui.ChangePasswordActivity;
import edu.aku.hassannaqvi.tpvicsround2listing.ui.SyncActivity;
import edu.aku.hassannaqvi.tpvicsround2listing.ui.lists.ListingsReporter;
import edu.aku.hassannaqvi.tpvicsround2listing.ui.sections.SectionAActivity;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bi;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        setSupportActionBar(bi.toolbar);
        bi.adminView.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);
        bi.username.setText("Welcome, " + MainApp.user.getFullname() + "!");


    }

    public void sectionPress(View view) {
        switch (view.getId()) {

            case R.id.openChildForm:
                //MainApp.cr = new Listings();
                finish();
                startActivity(new Intent(this, SectionAActivity.class));
                break;
            case R.id.dbm:
                startActivity(new Intent(this, AndroidManager.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.action_database:
                intent = new Intent(MainActivity.this, AndroidManager.class);
                startActivity(intent);
                break;
            case R.id.action_data_sync:
                intent = new Intent(MainActivity.this, SyncActivity.class);
                startActivity(intent);
                break;
            case R.id.action_view_listing:
                intent = new Intent(MainActivity.this, ListingsReporter.class);
                startActivity(intent);
                break;
            case R.id.changePassword:
                intent = new Intent(MainActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem action_database = menu.findItem(R.id.action_database);

        action_database.setVisible(MainApp.admin);
        return true;

    }
}