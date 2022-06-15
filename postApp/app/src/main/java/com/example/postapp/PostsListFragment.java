package com.example.postapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.postapp.databinding.FragmentPostsListBinding;

import java.util.ArrayList;


public class PostsListFragment extends Fragment implements PostsRecyclerViewAdapter.IPostRecycler, PagesRecyclerViewAdapter.IPageRecycler {
    FragmentPostsListBinding binding;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    PostsRecyclerViewAdapter adapter;
    ArrayList<Posts> posts = new ArrayList<>();
    Person currentUser;

    RecyclerView recyclerView2;
    LinearLayoutManager layoutManager2;
    PagesRecyclerViewAdapter adapter2;
    ArrayList<Integer> pages = new ArrayList<>();
    int selectedPage = 1;

    public PostsListFragment() {
        // Required empty public constructor
    }

    public static PostsListFragment newInstance(String param1, String param2) {
        PostsListFragment fragment = new PostsListFragment();
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
        binding = FragmentPostsListBinding.inflate(inflater, container, false);
        getActivity().setTitle("Posts");

        listener.getPosts(selectedPage);

        binding.buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.openCreatePost();
            }
        });

        binding.buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.logOut();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof IPostsListFragment){
            listener = (IPostsListFragment) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement IPostsListFragment");
        }
    }

    IPostsListFragment listener;

    @Override
    public void delete(Posts pst) {
        listener.deletePost(pst);
    }

    @Override
    public Person getUser() {
        return currentUser;
    }

    public void updateList(PostList postList, Person currentUser) {
        posts = postList.getPosts();
        this.currentUser = currentUser;

        binding.textViewHello.setText("Hello " + currentUser.user_fullname);

        recyclerView = binding.recyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostsRecyclerViewAdapter(posts, this);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < (postList.totalCount / postList.pageSize); i++) {
            pages.add(i+1);
        }

        recyclerView2 = binding.recyclerViewPages;
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager2);
        adapter2 = new PagesRecyclerViewAdapter(pages, this);
        recyclerView2.setAdapter(adapter2);

        binding.textViewPages.setText("Showing page " + (postList.page) + " out of " + String.valueOf(postList.totalCount/postList.pageSize));
    }

    @Override
    public void selectPage(int page) {
        this.selectedPage = page;
        listener.getPosts(page);
    }

    public interface IPostsListFragment{
        void openCreatePost();
        void logOut();
        void getPosts(int i);
        void deletePost(Posts pst);
    }
}