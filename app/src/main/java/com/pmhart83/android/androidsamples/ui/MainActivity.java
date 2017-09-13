package com.pmhart83.android.androidsamples.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.pmhart83.android.androidsamples.R;
import com.pmhart83.android.androidsamples.controllers.NavigationController;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setContent(new HomeFragment());
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //todo: use dependancy injection

        NavigationController navController = new NavigationController();

        //perform action based on item chosen in hamburger menu

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setContent(new HomeFragment());
        }
        else if(id == R.id.nav_draw)
        {
            DrawingListFragment drawFragment = new DrawingListFragment();
            setContent(drawFragment);
        }
        else if(id == R.id.nav_web)
        {
            WebBrowserFragment webFragment = new WebBrowserFragment();
            setContent(webFragment);
        }
        else if(id == R.id.nav_email)
        {
            String toEmail = getString(R.string.email_contact);
            navController.ShowEmail(this, toEmail);
        }
        else if(id == R.id.nav_linkedin)
        {
            String linkedInUserId = getString(R.string.linkedin_userid);
            navController.ShowLinkedIn(this, linkedInUserId);
        }
        else
        {
            toastFeatureComingSoon();
        }

        //close hamburger menu

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void toastFeatureComingSoon()
    {
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                EmptyFragment f = new EmptyFragment();
                setContent(f);

                Toast.makeText(getApplicationContext(), "Feature Coming Soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setContent(Fragment f)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, f);
        ft.commit();
    }
}