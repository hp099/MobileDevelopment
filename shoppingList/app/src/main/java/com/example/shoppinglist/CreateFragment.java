package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoppinglist.databinding.FragmentCreateBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import android.R.drawable;

public class CreateFragment extends Fragment {
    FragmentCreateBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView usersRecyclerView;
    ArrayList<String> usersInvited = new ArrayList<>();
    FirestoreRecyclerAdapter adapter;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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
        binding = FragmentCreateBinding.inflate(inflater, container, false);
        getActivity().setTitle("Create Post");
        mAuth = FirebaseAuth.getInstance();

        usersRecyclerView = binding.recyclerViewInvite;
        Query query = db.getInstance()
                .collection("users")
                .whereNotEqualTo("id", mAuth.getCurrentUser().getUid());

        FirestoreRecyclerOptions<user> options = new FirestoreRecyclerOptions.Builder<user>()
                .setQuery(query, user.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<user, usersViewHolder>(options) {

            @NonNull
            @Override
            public usersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
                return new usersViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull usersViewHolder holder, int position, @NonNull user model) {
                holder.userName.setText(model.name);
                holder.inviteCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (usersInvited.contains(model.id)){
                            usersInvited.remove(model.id);
                            holder.inviteCheck.setImageResource(drawable.checkbox_off_background);
                        }else{
                            usersInvited.add(model.id);
                            holder.inviteCheck.setImageResource(drawable.checkbox_on_background);
                        }
                    }
                });
            }
        };
        usersRecyclerView.setHasFixedSize(true);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersRecyclerView.setAdapter(adapter);

        binding.buttonSubmitCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAllField()){

                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    ShoppingList shpLst = new ShoppingList(binding.editTextTitle.getText().toString(), mAuth.getCurrentUser().getDisplayName().toString(),
                            mAuth.getUid(), usersInvited);

                    db.collection("shoppingLists")
                            .add(shpLst)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(), "Forum Created Successfully", Toast.LENGTH_LONG).show();
                                    String tempId = documentReference.getId();

                                    db.collection("shoppingLists").document(documentReference.getId())
                                            .update("id", tempId).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getContext(), "List ID added", Toast.LENGTH_LONG).show();
                                            listener.goToLists();
                                        }
                                    });
                                }
                            });
                }
            }
        });

        binding.buttonCancelCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.goToLists();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof ICreateFragment){
            listener = (ICreateFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement ICreateFragment");
        }
    }

    ICreateFragment listener;
    public interface ICreateFragment{
        void goToLists();
    }

    private boolean checkAllField() {
        boolean good = true;
        if(binding.editTextTitle.getText().toString().isEmpty()){
            binding.editTextTitle.setError("!");
            Toast.makeText(getContext(),"Enter Title!",Toast.LENGTH_LONG).show();
            good = false;
        }
        return good;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
