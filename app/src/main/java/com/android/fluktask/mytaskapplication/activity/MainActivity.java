package com.android.fluktask.mytaskapplication.activity;

/**
 * Created by Admin on 6/1/2017.
 */


import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.fluktask.mytaskapplication.AppTool;
import com.android.fluktask.mytaskapplication.OnGPlusLoginListener;
import com.android.fluktask.mytaskapplication.R;
import com.android.fluktask.mytaskapplication.adapter.MainFragmentAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;


@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_search)
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnGPlusLoginListener {

    private static final String TAG = MainActivity.class.getName();

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;

    @ViewById(R.id.tab_viewpager)
    ViewPager viewPager;

    @ViewById(R.id.tabLayout)
    TabLayout tabLayout;

    @OptionsMenuItem(R.id.action_search)
    MenuItem searchMenu;

    String[] tabNames;

    GoogleApiClient googleApiClient = null;

    GoogleSignInAccount googleSignInAccount = null;


    @AfterViews
    public void onInitView() {

        setActionBarSupport();

        showTab();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }


    private void setActionBarSupport() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setTitle("");

    }


    private void showTab() {

        tabNames = getResources().getStringArray(R.array.mainTabTitle);

        tabLayout.setTabTextColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorAccent));
        tabLayout.addTab(tabLayout.newTab().setText(tabNames[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabNames[1]));

        MainFragmentAdapter adapter = new MainFragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), MainActivity.this);
        adapter.setgPlusLoginListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.getTabAt(0).select();
        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

                // have search menu in tab 2 alone

                if(tab.getPosition() == 0){
                    searchMenu.setVisible(false);
                }else {
                    searchMenu.setVisible(true);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            signOut();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void signOut() {
        if(null != googleApiClient){

            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            googleSignInAccount = null;
                            finish();
                            System.exit(0);
                        }
                    });
        }else {
            AppTool.showToast(getString(R.string.gPlusLogout));
        }

    }

    // call back after successful signIn

    @Override
    public void onLoginUpdate(GoogleApiClient googleApiClient) {
      this.googleApiClient =  googleApiClient;
    }

    @Override
    public void onLoginData(GoogleSignInAccount googleSignInAccount) {
        this.googleSignInAccount = googleSignInAccount;
        if(null != googleSignInAccount){

            View navHeader = navigationView.getHeaderView(0);
            ImageView profileImage = (ImageView) navHeader.findViewById(R.id.profileImage);
            TextView profileUserName = (TextView) navHeader.findViewById(R.id.profileUserName);
            TextView profileUserId = (TextView) navHeader.findViewById(R.id.profileUserId);

            profileUserName.setText(googleSignInAccount.getDisplayName());
            profileUserId.setText(googleSignInAccount.getEmail());

            Picasso.with(MainActivity.this).load(googleSignInAccount.getPhotoUrl())
                    .placeholder(android.R.drawable.btn_star)
                    .error(android.R.drawable.btn_star)
                    .into(profileImage);


            return;
        }
    }

}
