package com.ywl5320.navigationbar;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ywl5320.navigationbar.bar.NavitationLayout;
import com.ywl5320.navigationbar.bar.NavitationScrollLayout;
import com.ywl5320.navigationbar.bar.TabNavitationLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavitationScrollLayout navitationScrollLayout;
    private ViewPager viewPager;
    private String[] titles = new String[]{"标题一", "标题二", "标题三", "标题四", "标题五", "标题六", "标题七","标题八"};
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragments;

    private NavitationLayout navitationLayout;
    private ViewPager viewPager1;
    private String[] titles1 = new String[]{"标题一", "标题二", "标题三", "标题四"};
    private ViewPagerAdapter viewPagerAdapter1;
    private List<Fragment> fragments1;

    private TabNavitationLayout tabNavitationLayout;
    private String[] titles2 = new String[]{"标题一", "标题二", "标题三"};
    private ViewPagerAdapter viewPagerAdapter2;
    private List<Fragment> fragments2;
    private ViewPager viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager1 = (ViewPager) findViewById(R.id.viewpager1);
        viewPager2 = (ViewPager) findViewById(R.id.viewpager2);

        navitationScrollLayout = (NavitationScrollLayout) findViewById(R.id.bar);
        navitationLayout = (NavitationLayout) findViewById(R.id.bar1);
        tabNavitationLayout = (TabNavitationLayout) findViewById(R.id.bar2);

        fragments1 =  new ArrayList<>();
        fragments1.add(new ChildFragment());
        fragments1.add(new ChildFragment());
        fragments1.add(new ChildFragment());
        fragments1.add(new ChildFragment());
        viewPagerAdapter1 = new ViewPagerAdapter(getSupportFragmentManager(), fragments1);
        viewPager1.setAdapter(viewPagerAdapter1);

        fragments2 =  new ArrayList<>();
        fragments2.add(new ChildFragment());
        fragments2.add(new ChildFragment());
        fragments2.add(new ChildFragment());
        viewPagerAdapter2 = new ViewPagerAdapter(getSupportFragmentManager(), fragments2);
        viewPager2.setAdapter(viewPagerAdapter2);

        fragments =  new ArrayList<>();
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        fragments.add(new ChildFragment());
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);

        navitationLayout.setViewPager(this, titles1, viewPager1, R.color.color_333333, R.color.color_2581ff, 16, 16, 0, 0, true);
        navitationLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationLayout.setNavLine(this, 3, R.color.colorPrimary, 0);

        navitationScrollLayout.setViewPager(this, titles, viewPager, R.color.color_333333, R.color.color_2581ff, 16, 16, 12, true, R.color.color_333333, 0f, 15f, 15f, 100);
        navitationScrollLayout.setBgLine(this, 1, R.color.colorAccent);
        navitationScrollLayout.setNavLine(this, 3, R.color.colorPrimary);

        tabNavitationLayout.setViewPager(this, titles2, viewPager2, R.drawable.drawable_left, R.drawable.drawable_mid, R.drawable.drawable_right, R.color.color_ffffff, R.color.color_282d31, 16, 0, 1f, true);

        navitationScrollLayout.setOnTitleClickListener(new NavitationScrollLayout.OnTitleClickListener() {
            @Override
            public void onTitleClick(View v) {
                Toast.makeText(MainActivity.this, ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        navitationScrollLayout.setOnNaPageChangeListener(new NavitationScrollLayout.OnNaPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
