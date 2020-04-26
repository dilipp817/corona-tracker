package com.corona.coronatracker.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.corona.coronatracker.R;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager pager;
    private int totalPages = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupViewPager();
    }

    private void setupViewPager() {
        pager = findViewById(R.id.pager);
        TextView tv_prev = findViewById(R.id.tv_prev);
        TextView tv_next = findViewById(R.id.tv_next);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tv_prev.setOnClickListener(v -> prevNextListener(PrevNext.PREVIOUS));
        tv_next.setOnClickListener(v -> prevNextListener(PrevNext.NEXT));
    }

    private void prevNextListener(PrevNext action) {
        int currentItem = pager.getCurrentItem();
        if (action == PrevNext.PREVIOUS) {
            if (currentItem != 0)
                pager.setCurrentItem(currentItem - 1);
        }
        else if (action == PrevNext.NEXT)
            if (currentItem != totalPages -1) pager.setCurrentItem(currentItem + 1);
            else {
                startActivity(new Intent(this, MainActivity.class));
            }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 1:
                    return FragmentViewPager.newInstance("page 2", R.drawable.icon_corona);
                case 2:
                    return FragmentViewPager.newInstance("page 3", R.drawable.icon_corona);
                default:
                    return FragmentViewPager.newInstance("page 1", R.drawable.icon_corona);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    private enum PrevNext {
        PREVIOUS, NEXT;
    }
}
