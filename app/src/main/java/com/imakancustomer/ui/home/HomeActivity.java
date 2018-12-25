package com.imakancustomer.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imakancustomer.R;
import com.imakancustomer.ui.address.AddressFragment;
import com.imakancustomer.ui.category_list.CategoryListFragment;
import com.imakancustomer.ui.dashboard.DashboardFragment;
import com.imakancustomer.ui.help.HelpFragment;
import com.imakancustomer.ui.login.LoginActivity;
import com.imakancustomer.ui.notification.NotificationFragment;
import com.imakancustomer.ui.order.OrderFragment;
import com.imakancustomer.ui.payment.PaymentFragment;
import com.imakancustomer.ui.profile.ProfileFragment;
import com.imakancustomer.ui.provider.ProviderListFragment;
import com.imakancustomer.utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private SharedPref mSharedPref;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private int mStackHeight;
    private ActionBarDrawerToggle toggle;
    public static LinearLayout ll_appbar_location;
    public static TextView tv_location_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ll_appbar_location = findViewById(R.id.ll_appbar_location);
        tv_location_name = findViewById(R.id.tv_location_name);

        mSharedPref = new SharedPref(this);
        intializeBackStack();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        TextView tv_header_username = header.findViewById(R.id.tv_header_name);
        tv_header_username.setText("Hi " + mSharedPref.getFullName());

        /*Picasso.with(DrawerActivity.this).load(Cons.BASE_URL).placeholder(R.drawable.ic_profile_new)
                .error(R.drawable.ic_profile_new).into(iv_user);
                */
        addfragment(new CategoryListFragment());
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_home:
                replaceFragment(new CategoryListFragment());
                break;
            case R.id.nav_dashboard:
                replaceFragment(new DashboardFragment());
                break;
            case R.id.nav_profile:
                replaceFragment(new ProfileFragment());
                break;
            case R.id.nav_notifications:
                replaceFragment(new NotificationFragment());
                break;
            case R.id.nav_orders:
                replaceFragment(new OrderFragment());
                break;
            case R.id.nav_addresses:
                replaceFragment(new AddressFragment());
                break;
/*            case R.id.nav_payments:
                replaceFragment(new PaymentFragment());
                break;*/
            case R.id.nav_help:
                replaceFragment(new HelpFragment());
                break;
            case R.id.nav_logout:
                mSharedPref.setLogin(false);
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finish();
                break;
            default:
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method to do replacing of fragment
     *
     * @param fragment contain the frgment name
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Method to add fragment
     *
     * @param fragment hold the fragment to add
     */
    public void addfragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment, fragment.getTag());
        fragmentTransaction.commit();
    }


    private void intializeBackStack() {

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            mStackHeight = getSupportFragmentManager().getBackStackEntryCount();
            Log.e("StackVal", ": " + mStackHeight);

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.container_body);

            if (f instanceof CategoryListFragment || f instanceof ProviderListFragment) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.syncState();
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawer.openDrawer(GravityCompat.START);
                    }
                });

            } else {
                if (mStackHeight > 1) {
                    // if we have something on the stack (doesn't include the current shown fragment)
                    getSupportActionBar().setHomeButtonEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    toolbar.setNavigationOnClickListener(v -> onBackPressed());

                } else {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    toggle.syncState();
                    toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(GravityCompat.START));
                }

            }

        });
    }

    public static void hideShowLocationOption(boolean status, String msg) {
        if (status) {
            ll_appbar_location.setVisibility(View.VISIBLE);
            tv_location_name.setText(msg);
        } else {
            ll_appbar_location.setVisibility(View.GONE);
        }
    }
}
