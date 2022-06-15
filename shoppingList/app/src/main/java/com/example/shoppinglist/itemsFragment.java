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

import com.example.shoppinglist.databinding.FragmentItemsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class itemsFragment extends Fragment implements itemRecyclerViewAdapter.IItemsRecycler{


    FragmentItemsBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    itemRecyclerViewAdapter adapter;
    HashMap<String, Boolean> items = new HashMap<>();
    private FirebaseAuth mAuth;

    public itemsFragment() {
        // Required empty public constructor
    }

    public static itemsFragment newInstance(String param1, String param2) {
        itemsFragment fragment = new itemsFragment();
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
        binding = FragmentItemsBinding.inflate(inflater, container, false);
        getActivity().setTitle("Items");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("shoppingLists").document(listener.getForumID())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        ShoppingList shoppingList = value.toObject(ShoppingList.class);


                        items = shoppingList.getItems();


                        binding.textViewDetailAuthName.setText(shoppingList.getCreatorName());
                        binding.textViewDetailTitle.setText(shoppingList.getTitle());
                    }

                });

        binding.buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                //item newItem = new item(binding.editTextNewItemName.getText().toString(), false);

                db.collection("shoppingLists").document(listener.getForumID())
                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                ShoppingList shoppingList = value.toObject(ShoppingList.class);

                                HashMap<String, Boolean> itemsTemp = shoppingList.getItems();
                                itemsTemp.put(binding.editTextNewItemName.getText().toString(), false);

                                db.collection("shoppingLists").document(listener.getForumID())
                                        .update("items", itemsTemp);
                                adapter.notifyDataSetChanged();
                            }
                        });

            }
        });

        recyclerView = binding.recyclerViewItems;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new itemRecyclerViewAdapter(items, this);
        recyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IItemsFragment){
            listener = (IItemsFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IItemsFragment");
        }
    }

    IItemsFragment listener;

    @Override
    public void updateItems() {
        listener.updateItems();
    }

    public interface IItemsFragment {
        String getForumID();
        void updateItems();
    }
}