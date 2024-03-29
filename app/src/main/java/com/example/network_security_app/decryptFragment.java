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

import com.chaquo.python.PyException;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link decryptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class decryptFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public TextView dn_txt_view_plain_text,dn_txt_view_cipher_text,dn_txt_view_cipher_output,dn_txt_key_details,dn_txt_view_d,dn_txt_view_n;
    public EditText dn_ed_txt,dn_ed_txt_d,dn_ed_txt_n;
    public Button dn_btn;
    public ImageView dn_img_copy,dn_img_refresh,dn_img_share;


    public decryptFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment decryptFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static decryptFragment newInstance(String param1, String param2) {
        decryptFragment fragment = new decryptFragment();
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

        View v= inflater.inflate(R.layout.fragment_decrypt, container, false);
        dn_btn=v.findViewById(R.id.dn_btn);
        dn_ed_txt=v.findViewById(R.id.dn_ed_txt);
        dn_txt_view_plain_text=v.findViewById(R.id.dn_txt_view_plain_text);
        dn_txt_view_cipher_text=v.findViewById(R.id.dn_txt_view_cipher_txt);
        dn_txt_view_cipher_output=v.findViewById(R.id.dn_txt_view_cipher_output);
        dn_img_copy=v.findViewById(R.id.dn_img_copy);
        dn_img_refresh=v.findViewById(R.id.dn_img_refresh);
        dn_img_share=v.findViewById(R.id.dn_img_share);
        dn_ed_txt_d=v.findViewById(R.id.dn_ed_txt_d);
        dn_ed_txt_n=v.findViewById(R.id.dn_ed_txt_n);
        dn_txt_view_n=v.findViewById(R.id.dn_txt_view_n);
        dn_txt_view_d=v.findViewById(R.id.dn_txt_view_d);

        dn_img_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dn_txt_view_cipher_output.setText(null);
                dn_ed_txt_d.setText(null);
                dn_ed_txt_n.setText(null);
                dn_txt_view_cipher_output.setBackgroundColor(getResources().getColor(R.color.c3_normal));
                dn_ed_txt.setText(null);
            }
        });





        dn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message=dn_ed_txt.getText().toString();
                String d_string=dn_ed_txt_d.getText().toString();
                String n_string=dn_ed_txt_n.getText().toString();
                if (message.isEmpty() || d_string.isEmpty() || n_string.isEmpty()) {
                    // Display an error message or take appropriate action
                    Toast.makeText(getActivity(), "Please enter all values", Toast.LENGTH_SHORT).show();
                    return; // Stop further execution
                }
                try {

                int d=Integer.parseInt(d_string);
                int n=Integer.parseInt(n_string);

                //Instance creation
                Python py=Python.getInstance();
                //Objected created
                PyObject pyobj = py.getModule("encrypt");



                    PyObject decode = pyobj.callAttr("process_message_decrypt", message,d,n);

                    String decoded_message = decode.toString();

                    dn_txt_view_cipher_output.setText(decoded_message);
                    dn_txt_view_cipher_output.setBackgroundColor(Color.WHITE);
//                dn_txt_view_out2.setText(decoded_message);
//                dn_txt_view_out2.setBackgroundColor(Color.WHITE);


                dn_img_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboardManager=(ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData=ClipData.newPlainText("Copy",dn_txt_view_cipher_output.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getActivity(), "Message Copied", Toast.LENGTH_SHORT).show();

                    }
                });


                dn_img_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        String share=dn_txt_view_cipher_output.getText().toString();
                        shareIntent.putExtra(Intent.EXTRA_TEXT,share);
                        startActivity(Intent.createChooser(shareIntent, "Share Using"));

                    }
                });

                }catch (PyException pyException){
                    Toast.makeText(getActivity(), "Invalid Cipher Text", Toast.LENGTH_SHORT).show();
                }
                catch (NumberFormatException e) {
                    // Handle the case where e_string or n_string cannot be parsed as integers
                    Toast.makeText(getActivity(), "Invalid value for d or n", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return v;


        // Inflate the layout for this fragment

    }
}