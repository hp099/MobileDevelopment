package com.example.forumapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.forumapp.databinding.FragmentDetailsBinding;
import com.example.forumapp.databinding.FragmentForumsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DetailsFragment extends Fragment implements commentRecyclerViewAdapter.ICommentRecycler{


    FragmentDetailsBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    commentRecyclerViewAdapter adapter;
    ArrayList<Comments> comments = new ArrayList<>();
    private FirebaseAuth mAuth;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        getActivity().setTitle("Forum");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").document(listener.getForumID())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        binding.textViewDetailAuthName.setText(value.getString("creatorName"));
                        binding.textViewDetailTitle.setText(value.getString("title"));
                        binding.textViewDetailDescription.setText(value.getString("description"));
                    }
                });

        binding.buttonPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                String currentDateTime = DateFormat.getDateTimeInstance().format(calendar.getTime());
                mAuth = FirebaseAuth.getInstance();
                HashMap<String, Object> comment = new HashMap<>();
                comment.put("userId", mAuth.getCurrentUser().getUid());
                comment.put("dateTime", currentDateTime);
                comment.put("comment", binding.editTextComment.getText().toString());

                db.collection("comments").add(comment)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                db.collection("forums").document(listener.getForumID())
                                        .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                Forum forum = value.toObject(Forum.class);

                                                if (forum != null){
                                                    List<String> tempList = forum.comments;
                                                    if (!tempList.contains(documentReference.getId())){
                                                        tempList.add(documentReference.getId());

                                                        db.collection("forums").document(listener.getForumID())
                                                                .update("comments", tempList).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                // success!
                                                                Toast.makeText(view.getContext(), "comment added", Toast.LENGTH_LONG).show();
                                                                binding.editTextComment.setText("");
                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });
                            }
                        });
            }
        });

        db.collection("forums").document(listener.getForumID())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Forum forum = value.toObject(Forum.class);

                        if (forum != null){
                            db.collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    comments.clear();
                                    for (QueryDocumentSnapshot document: value){
                                        if (forum.comments.contains(document.getId())){
                                            Comments comment = document.toObject(Comments.class);
                                            comment.setId(document.getId());
                                            comments.add(comment);
                                            binding.textViewNoOfComments.setText(comments.size() + " Comments");
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });



        recyclerView = binding.recyclerViewComments;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new commentRecyclerViewAdapter(comments, this);
        recyclerView.setAdapter(adapter);


        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IDetailsFragment){
            listener = (IDetailsFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IDetailsFragment");
        }
    }

    IDetailsFragment listener;

    @Override
    public void updateComments() {
        listener.updateComments();
    }

    public interface IDetailsFragment {
        String getForumID();
        void updateComments();
    }
}