package com.ashik.adminmodule;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private EditText pr_name, pr_address, pr_city;
    private TextView closeButton;
    private Button create_project, addProject;
    private RecyclerView mRecyclerView;
    private DatabaseReference projectRef;
    private DatabaseReference projectInfoRef;
    private projectListAdapter mAdapter;
    private ArrayList<Projects> projectList;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    private String id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        addProject = view.findViewById(R.id.btn_add_project);

        initFirebaseDatabase();
        id = projectRef.push().getKey();

        projectList = new ArrayList<>();
        mAdapter = new projectListAdapter(getActivity(), projectList);

        mRecyclerView = view.findViewById(R.id.list_projects);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                projectList.clear();
                // Fetching data from server
                loadRecyclerViewData();
            }
        });


        mRecyclerView.setAdapter(mAdapter);

        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }


    private void initFirebaseDatabase() {
        projectRef = FirebaseDatabase.getInstance().getReference().child("Projects");
        projectInfoRef = projectRef.child("ProjectInfo");

        Log.d("dataStore", projectRef.toString());

        projectInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    id = projectInfoRef.push().getKey();
                    long count = snapshot.getChildrenCount();
                    id = id + count;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadRecyclerViewData() {

        mSwipeRefreshLayout.setRefreshing(true);

        projectInfoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                projectList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Projects projects = dataSnapshot.getValue(Projects.class);
                    projectList.add(projects);

                }
                sortProjects();
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("projectData", "data failed");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void showDialog() {

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.create_project_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);


        pr_name =  dialogView.findViewById(R.id.p_name);
        pr_address =  dialogView.findViewById(R.id.p_address);
        pr_city =  dialogView.findViewById(R.id.p_city);
        create_project = dialogView.findViewById(R.id.btn_create);
        closeButton = dialogView.findViewById(R.id.btn_close);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        create_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertToFirebase(alertDialog);
            }
        });



    }

    private boolean validateInput(String projectName, String address, String cityName) {

        if (projectName.isEmpty()) {
            pr_name.setError("project name is required");
            pr_name.requestFocus();
            return false;
        }

        if (address.isEmpty()) {
            pr_address.setError("Address is required");
            pr_name.requestFocus();
            return false;
        }

        if (cityName.isEmpty()) {
            pr_city.setError("City name is required");
            pr_city.requestFocus();
            return false;
        }

        return true;
    }


    private void insertToFirebase(AlertDialog alertDialog) {

        String projectName = pr_name.getText().toString();
        String address = pr_address.getText().toString();
        String cityName = pr_city.getText().toString();


        if (validateInput(projectName, address, cityName)) {
            Log.d("idCheck", "id is: " + id);

            Projects project = new Projects(id, projectName, address, cityName);
            projectInfoRef.child(id).setValue(project).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Project added Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Failed to load data", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alertDialog.dismiss();
        }

    }

    @Override
    public void onRefresh() {
        projectList.clear();
        loadRecyclerViewData();
    }

    private void sortProjects() {
        Collections.sort(projectList, new Comparator<Projects>() {
            @Override
            public int compare(Projects o1, Projects o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        Collections.reverse(projectList);
    }


}