package com.example.dell.booking_cyber.Adapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dell.booking_cyber.LoginActivity;
import com.example.dell.booking_cyber.DTO.AccountDTO;
import com.example.dell.booking_cyber.Model.AccountManager;
import com.example.dell.booking_cyber.R;
import com.example.dell.booking_cyber.SignupActivity;

public class NavigationAdapter extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public Integer layout;
    public Class currentClass;
    public AccountManager accountManager;
    public boolean BottomNavigationOn = true;
    public boolean profileNavigationOn = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set botom navigator
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(!BottomNavigationOn){
            navigation.setVisibility(View.GONE);
        }
        accountManager = new AccountManager(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        // Retrieve the SearchView and plug it into SearchManager
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        String[] member_names = getResources().getStringArray(R.array.Members);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return true;
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(currentClass == null)
            return false;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            if(currentClass != LoginActivity.class){
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                return true;
            }
        }
        if(id == R.id.action_signup){
            if(currentClass != SignupActivity.class){
                Intent intent = new Intent(this,SignupActivity.class);
                startActivity(intent);
                return true;
            }
        }
        if (id == R.id.action_logout) {
            accountManager.logout();
            finish();
            startActivity(getIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        }
        if (id == R.id.nav_transaction) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(accountManager.isLogin()){
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_signup).setVisible(false);
            AccountDTO accountDTO = accountManager.getAccount();
            TextView navUsername = (TextView) findViewById(R.id.header_username);
            navUsername.setText(accountManager.userDTO.getName());

            TextView navEmail = (TextView) findViewById(R.id.textView);
            navEmail.setText(accountDTO.getUsername());
        }else{
            menu.findItem(R.id.action_logout).setVisible(false);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(null);
        }
        if(!profileNavigationOn){
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(null);
        }
        return super.onPrepareOptionsMenu(menu);
    }
}
