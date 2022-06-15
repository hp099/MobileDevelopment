package com.example.forumapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.forumapp.databinding.FragmentForumsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ForumsFragment extends Fragment implements forumRecyclerViewAdapter.IForumRecycler{

    FragmentForumsBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    forumRecyclerViewAdapter adapter;
    ArrayList<Forum> forums = new ArrayList<>();

    public ForumsFragment() {
        // Required empty public constructor
    }

    public static ForumsFragment newInstance(String param1, String param2) {
        ForumsFragment fragment = new ForumsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForumsBinding.inflate(inflater, container, false);
        getActivity().setTitle("Posts");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        /*db.collection("forums").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                            forums.add(new Forum(document.getString("title"), document.getString("creatorName"), document.getString("description"),
                                    document.getString("dateTime"), document.getString("userId")));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });*/

        db.collection("forums")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        forums.clear();
                        for (QueryDocumentSnapshot document: value){
                            Forum forum = document.toObject(Forum.class);
                            forums.add(forum);

                            /*forums.add(new Forum(document.getString("title"), document.getString("creatorName"), document.getString("description"),
                                    document.getString("dateTime"), document.getString("userId"), document.getString("id"), (List<String>) document.get("likes")));*/
                        }
                        adapter.notifyDataSetChanged();


                    }
                });

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new forumRecyclerViewAdapter(forums, this);
        recyclerView.setAdapter(adapter);

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openCreateForum();
            }
        });

        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                listener.logOut();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IForumListFragment){
            listener = (IForumListFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IForumListFragment");
        }
    }

    IForumListFragment listener;

    @Override
    public void updateForums() {
        listener.goToForums();
    }

    @Override
    public void detailForum(String forumId) {
        listener.openForumDetail(forumId);
    }

    public interface IForumListFragment {
        void openCreateForum();
        void logOut();
        void goToForums();
        void openForumDetail(String forumId);
    }
}