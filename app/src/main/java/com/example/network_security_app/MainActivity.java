package com.example.network_security_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.os.Bundle;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//        textView=(TextView)findViewById(R.id.textview);

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
//
//        //Instance creation
//        Python py=Python.getInstance();
//        //Objected created
//        PyObject pyobj = py.getModule("encryption");

        //call the attribute
//        PyObject obj=pyobj.callAttr("main");
//        textView.setText(obj.toString());

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tab.setupWithViewPager(viewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.opt_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         int itemId=item.getItemId();
         if(itemId==R.id.generate_key){
             Intent intent=new Intent(MainActivity.this,generatekey.class);
             startActivity(intent);
         }
        return super.onOptionsItemSelected(item);
    }
}