package com.example.furkantrkn.furkan_pati;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //this inflates out tab layout file.
        View x =  inflater.inflate(R.layout.tab_fragment_layout,null);
        // set up stuff.
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        // create a new adapter for our pageViewer. This adapters returns child fragments as per the positon of the page Viewer.
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        // this is a workaround
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                //provide the viewPager to TabLayout.
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        //to preload the adjacent tabs. This makes transition smooth.
        viewPager.setOffscreenPageLimit(5);

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        //return the fragment with respect to page position.
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 :
                    return new AnasayfaFragment();
               case 1 :
                    return new SEVFragment();
                case 2 :
                    return new SARILFragment();
                case 3 :
                    return new IZLEFragment();
                case 4 :
                    return new OGRENFragment();
                case 5 :
                    return new COZFragment();

            }

            return null;
        }

        @Override
        public int getCount() {

            return 6;

        }

        //This method returns the title of the tab according to the position.
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "ANA SAYFA";
               case 1 :
                    return "SEV";
                case 2:
                    return "SARIL";
                case 3:
                    return "İZLE";
                case 4:
                    return "ÖĞREN";
                case 5:
                    return "ÇÖZ";
            }
            return null;
        }
    }
}