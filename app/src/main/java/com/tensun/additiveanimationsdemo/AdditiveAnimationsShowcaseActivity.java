package com.tensun.additiveanimationsdemo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.tensun.additiveanimationsdemo.fragments.MoveAlongPathDemoFragment;
import com.tensun.additiveanimationsdemo.fragments.RepeatingChainedAnimationsDemoFragment;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;

public class AdditiveAnimationsShowcaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static boolean ADDITIVE_ANIMATIONS_ENABLED = true;                                   // 是否邊移動邊實現效果, 預設為true 即是
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additive_animations_showcase);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(                                   // 將drawer 與toolbar連結在一起
                this,
                drawer,                                                                             // navigation drawer
                toolbar,                                                                            // action bar app icon
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        toggle.syncState();                                                                         // 在toolbar上 顯示navigation button

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(                                         // 將fragment_container 加載最初的畫面, 由TapToMoveDemoFragment 產生的
                R.id.fragment_container,                                                               // 畫面的容器
                new MoveAlongPathDemoFragment()                                                     // 產生畫面
        ).commit();

        Switch additiveEnabledSwitch = (Switch) findViewById(R.id.additive_animations_enabled_switch);
        additiveEnabledSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ADDITIVE_ANIMATIONS_ENABLED = ((Switch)v).isChecked();                          // 將additiveEnabledSwitch 目前的開關狀態, 即時賦予ADDITIVE_ANIMATIONS_ENABLED
            }
        });

        AdditiveAnimator.setDefaultDuration(1000);                                                     // 設置所有動畫的 預設持續時間：
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

    /** 關於NavigationView 的整個版面內容, 都已經在xml設置好了 */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {                                        // 由implements NavigationView.OnNavigationItemSelectedListener 產生
        int id = item.getItemId();

        if (id == R.id.nav_path) {
            getSupportFragmentManager().beginTransaction().
                    replace(
                            R.id.fragment_container,
                            new MoveAlongPathDemoFragment()
                    ).commit();
        } else if(id == R.id.nav_chaining_repeated) {
            getSupportFragmentManager().beginTransaction().
                    replace(
                            R.id.fragment_container,
                            new RepeatingChainedAnimationsDemoFragment()
                    ).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}