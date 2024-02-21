package com.example.network_security_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class generatekey extends AppCompatActivity {
    TextView public_key_txt_view,private_key_txt_view,public_key_txt,private_key_txt;
    ImageView public_key_img_copy,public_key_img_share,private_key_img_copy,private_key_img_share;
    Button generate_key_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatekey);
        public_key_txt_view=findViewById(R.id.public_key_txt_view);
        private_key_txt_view=findViewById(R.id.private_key_txt_view);
        public_key_txt=findViewById(R.id.public_key_txt);
        private_key_txt=findViewById(R.id.private_key_txt);
        public_key_img_copy=findViewById(R.id.public_key_img_copy);
        public_key_img_share=findViewById(R.id.public_key_img_share);
        private_key_img_copy=findViewById(R.id.private_key_img_copy);
        private_key_img_share=findViewById(R.id.private_key_img_share);
        generate_key_btn=findViewById(R.id.key_btn);
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        generate_key_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Access the Python module
                Python py = Python.getInstance();
                PyObject pyObject = py.getModule("key_generation");

                PyObject primefiller = pyObject.callAttr("primefiller");
                PyObject setkeys = pyObject.callAttr("setkeys");

                // Access the Python variables from your module
                PyObject public_key = pyObject.get("public_key");
                PyObject private_key = pyObject.get("private_key");
                PyObject n = pyObject.get("n");

                // Now, update the TextView with the values
                String publickey = "Public Key: " + public_key +  " N: " + n;
                String privatekey= "Private Key: " + private_key + " N: " + n;

                public_key_txt.setText(publickey);
                private_key_txt.setText(privatekey);

                public_key_img_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData=ClipData.newPlainText("Copy",public_key_txt.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(generatekey.this, "Message Copied", Toast.LENGTH_SHORT).show();

                    }
                });
                public_key_img_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String share=public_key_txt.getText().toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT,share);
                        startActivity(Intent.createChooser(shareIntent, "Share Using"));

                    }
                });
                private_key_img_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData=ClipData.newPlainText("Copy",private_key_txt.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(generatekey.this, "Message Copied", Toast.LENGTH_SHORT).show();

                    }
                });
                private_key_img_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String share=private_key_txt.getText().toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT,share);
                        startActivity(Intent.createChooser(shareIntent, "Share Using"));

                    }
                });

            }
        });

    }
}