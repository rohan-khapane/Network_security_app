package com.example.network_security_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new encrypt_decryptFragment();
        } else if(position==1){
            return new encryptFragment();
        } else {
            return new decryptFragment();
        }

    }

    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Encrypt & Decrypt";
        }else if (position==1){
            return "Encrypt";
        }else{
            return "Decrypt";
        }

    }
}
