package com.fahmi.ekuningan.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fahmi.ekuningan.fragment.FragmentDesc;
import com.fahmi.ekuningan.fragment.FragmentMaps;
import com.fahmi.ekuningan.fragment.FragmentPhoto;

public class ViewPagerAdapter extends FragmentPagerAdapter{

    public ViewPagerAdapter(@NonNull FragmentManager fm){
        super(fm);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        if (position == 0)
            return new FragmentDesc();
        else if (position == 1)
            return new FragmentPhoto();
        else if (position == 2)
            return new FragmentMaps();
        return null;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Deskripsi";
        else if (position == 1)
            title = "Foto";
        else if (position == 2)
            title = "Maps";
        return title;
    }

}
