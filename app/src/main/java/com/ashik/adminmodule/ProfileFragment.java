package com.ashik.adminmodule;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String name, phone, email;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = view.findViewById(R.id.user_progressBar);
        listView = view.findViewById(R.id.user_profile_list_view);
        mAuth = FirebaseAuth.getInstance();

        progressBar.setVisibility(View.VISIBLE);

        try {
            SharedPreferences sp = requireActivity().getSharedPreferences("userInfo",MODE_PRIVATE);
            name = sp.getString("name", name);
            phone = sp.getString("phone", phone);
            email = sp.getString("email", email);

        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        displayList(name, phone, email);
        if (name != null && phone != null && email != null){
            progressBar.setVisibility(View.GONE);

        }else{
            progressBar.setVisibility(View.VISIBLE);
        }


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 5000);

        return view;
    }


    public void displayList(String name, String phone, String email) {

        int[] imageIDs = {
                R.drawable.profile_icon, R.drawable.mobile_icon, R.drawable.email_icon,R.drawable.worker_icon,
                R.drawable.share_icon, R.drawable.start_icon, R.drawable.logout_icon
        };

        String[] itemNames = {
                "Name", "Mobile", "Email", "Worker","Refer a Friend", "Rate Builders Hub", "LOG OUT"
        };

        String[] data = {
                name, phone, email, " ", " ", " "," "
        };

        ListAdapter listAdapter = new ListAdapter(getActivity(), imageIDs, itemNames, data);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedItemPosition = position + 1;

                switch (selectedItemPosition){
                    case 1:
                        Toast.makeText(getActivity(), "Name", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(getActivity(), "Phone", Toast.LENGTH_SHORT).show();
                        break;

                    case 3:
                        Toast.makeText(getActivity(), "Email", Toast.LENGTH_SHORT).show();
                        break;

                    case 4:
                        Intent intent=new Intent(getActivity(),WorkerMod.class);
                        startActivity(intent);
                        break;

                    case 5:
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        String body = "Install the app now!";
                        String sub = "Share with your friends";
                        myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                        myIntent.putExtra(Intent.EXTRA_TEXT,body);
                        startActivity(Intent.createChooser(myIntent, "Share Using"));
                        break;

                    case 6:
                        new AlertDialog.Builder(getActivity())
                                .setIcon(R.drawable.ic_dialog_alert)
                                .setTitle("Rate this app")
                                .setMessage(R.string.rate_dialog_message)
                                .setIcon(R.drawable.ic_dialog_alert)
                                .setPositiveButton("Rate It Now", (dialog, which) -> {

                                })
                                .setNeutralButton("Remind Me Later", (dialog, which) ->{

                                })
                                .setNegativeButton("No, Thanks", (dialog, which) -> {

                                })
                                .show();
                        break;

                    case 7:

                        new AlertDialog.Builder(getActivity())
                                .setIcon(R.drawable.ic_dialog_alert)
                                .setTitle("LOGOUT")
                                .setMessage("Do you really want to log out?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        mAuth.signOut();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(getActivity(), loginActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                        break;

                }
            }
        });


    }

}