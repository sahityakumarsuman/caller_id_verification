package com.example.android.truecaller;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.android.truecaller.callerfragment.CallerInfo.CallerHistory;
import com.example.android.truecaller.callerfragment.CallerInfo.CallerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duke on 11/15/2015.
 */
public class CallerDetails extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    private int[] tabIcons = {
            R.drawable.user_info,
            R.drawable.call_log,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caller_details);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int heigt = dm.heightPixels;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (heigt * .7));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setIcon();
    }

    private void setIcon() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new CallerInfo(), "Caller Info");
        adapter.addFragment(new CallerHistory(), "Caller History");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
