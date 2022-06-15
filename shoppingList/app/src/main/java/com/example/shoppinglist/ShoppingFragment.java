package com.example.shoppinglist;

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

import com.example.shoppinglist.databinding.FragmentRegisterBinding;
import com.example.shoppinglist.databinding.FragmentShoppingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShoppingFragment extends Fragment implements shoppingRecyclerViewAdapter.IshopListRecycler{
    FragmentShoppingBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    shoppingRecyclerViewAdapter adapter;
    ArrayList<ShoppingList> shoppingLists = new ArrayList<>();
    FirebaseAuth mAuth;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    public static ShoppingFragment newInstance(String param1, String param2) {
        ShoppingFragment fragment = new ShoppingFragment();
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
        binding = FragmentShoppingBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        getActivity().setTitle(mAuth.getCurrentUser().getDisplayName() + "'s Shopping Lists");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("shoppingLists")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        shoppingLists.clear();

                        for (QueryDocumentSnapshot document: value){
                            ShoppingList shoppingList = document.toObject(ShoppingList.class);
                            if (shoppingList.userId.equals(mAuth.getCurrentUser().getUid()) || shoppingList.guests.contains(mAuth.getCurrentUser().getUid())){
                                shoppingLists.add(shoppingList);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openCreateList();
            }
        });

        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                listener.logOut();
            }
        });

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new shoppingRecyclerViewAdapter(shoppingLists, this);
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IShoppingFragment){
            listener = (IShoppingFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IShoppingFragment");
        }
    }

    IShoppingFragment listener;

    @Override
    public void updateShoppingList() {
        listener.goToLists();
    }

    @Override
    public void openShoppingList(String id) {
        listener.openItems(id);
    }

/*
    @Override
    public void updateForums() {
        listener.goToForums();
    }

    @Override
    public void detailForum(String forumId) {
        listener.openForumDetail(forumId);
    }
*/

    public interface IShoppingFragment {
        void openCreateList();
        void logOut();
        void goToLists();
        void openItems(String id);
        //void openForumDetail(String shoppingListId);
    }
}