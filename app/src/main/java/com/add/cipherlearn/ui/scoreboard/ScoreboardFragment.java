package com.add.cipherlearn.ui.scoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.add.cipherlearn.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardFragment extends Fragment {

    private List<User> users = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    TextView uscore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uscore = view.findViewById(R.id.yourscore);

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String iscore = snapshot.child(user.getUid()).child("score").getValue().toString();

                uscore.setText("your score is " + iscore + " points");

                for(DataSnapshot ds : snapshot.getChildren()){

                    String nickname = ds.child("name").getValue().toString();
                    String score = ds.child("score").getValue().toString();

                    users.add(new User(score, nickname));

                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ScoreboardAdapter(users);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showData(DataSnapshot dataSnapshot) {

        users.clear();


        String iscore = dataSnapshot.child(user.getUid()).child("score").getValue().toString();

        uscore.setText("your score is " + iscore + " points");

        for(DataSnapshot ds : dataSnapshot.getChildren()){

            String nickname = ds.child("name").getValue().toString();
            String score = ds.child("score").getValue().toString();

            users.add(new User(score, nickname));

        }

        mAdapter = new ScoreboardAdapter(users);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

class User {

    String score;
    String nickname;

    public User(String score, String nickname) {
        this.score = score;
        this.nickname = nickname;
    }
}