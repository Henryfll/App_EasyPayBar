package com.example.henryf.pryeasypaybar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Fabian on 19/06/2017.
 */

public class Comunicador extends FragmentPagerAdapter {
    public Comunicador(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new FragmentoCategorias();
            case 1:
                return new FragmentoInicio();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
