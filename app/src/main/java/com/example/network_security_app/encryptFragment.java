package com.example.network_security_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link encryptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class encryptFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TextView en_txt_view_plain_text,en_txt_view_cipher_text,en_txt_view_cipher_output,en_txt_view_e,en_txt_view_n,en_txt_key_details;
    public EditText en_ed_txt,en_ed_txt_e,en_ed_txt_n;
    public Button en_btn;
    public ImageView en_img_copy,en_img_refresh,en_img_share;


    public encryptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment encryptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static encryptFragment newInstance(String param1, String param2) {
        encryptFragment fragment = new encryptFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(requireContext()));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_encrypt_fragment, container, false);

        en_btn=v.findViewById(R.id.en_btn);
        en_ed_txt=v.findViewById(R.id.en_ed_txt);
        en_txt_view_plain_text=v.findViewById(R.id.en_txt_view_plain_text);
        en_txt_view_cipher_text=v.findViewById(R.id.en_txt_view_cipher_txt);
        en_txt_view_cipher_output=v.findViewById(R.id.en_txt_view_cipher_output);
        en_img_copy=v.findViewById(R.id.en_img_copy);
        en_img_copy=v.findViewById(R.id.en_img_copy);
        en_img_refresh=v.findViewById(R.id.en_img_refresh);
        en_img_share=v.findViewById(R.id.en_img_share);
        en_txt_view_e=v.findViewById(R.id.en_txt_view_e);
        en_txt_view_n=v.findViewById(R.id.en_txt_view_n);
        en_ed_txt_e=v.findViewById(R.id.en_ed_txt_e);
        en_ed_txt_n=v.findViewById(R.id.en_ed_txt_n);

        en_img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                en_txt_view_cipher_output.setText(null);
                en_ed_txt_e.setText(null);
                en_ed_txt_n.setText(null);
                en_txt_view_cipher_output.setBackgroundColor(getResources().getColor(R.color.c3_normal));
                en_ed_txt.setText(null);
            }
        });

        en_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=en_ed_txt.getText().toString();
                String e_string=en_ed_txt_e.getText().toString();
                String n_string=en_ed_txt_n.getText().toString();
                int e=Integer.parseInt(e_string);
                int n=Integer.parseInt(n_string);
                //Instance creation
                Python py=Python.getInstance();
                //Objected created
                PyObject pyobj = py.getModule("encrypt");
                PyObject cipher=pyobj.callAttr("process_message_encrypt",message,e,n);
                String cipher_text =cipher.toString();


//                PyObject decode=pyobj.callAttr("process_message_decrypt",message);

//                String decoded_message = decode.toString();

                en_txt_view_cipher_output.setText(cipher_text);
                en_txt_view_cipher_output.setBackgroundColor(Color.WHITE);

//                dn_txt_view_out2.setText(decoded_message);
//                dn_txt_view_out2.setBackgroundColor(Color.WHITE);

                en_img_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager=(ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData=ClipData.newPlainText("Copy",en_txt_view_cipher_output.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getActivity(), "Message Copied", Toast.LENGTH_SHORT).show();

                    }
                });

                en_img_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String share=en_txt_view_cipher_output.getText().toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT,share);
                        startActivity(Intent.createChooser(shareIntent, "Share Using"));

                    }
                });



            }
        });

        return v;

    }
}