package edu.uncc.sis.itcs4180.fragmentsdemo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ContactsFragment extends Fragment {
    
    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        Button btnCreate = view.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.goToCreate();

                // WRONG, NEVER DO THIS
                // ((MainActivity)getActivity()).names.add("");
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof IContactsFragment) {
            listener = (IContactsFragment) context;
        } else {
            throw new RuntimeException("Hey, you forgot to implement IContactsFragment");
        }
    }

    IContactsFragment listener;
    interface IContactsFragment {
        void deleteContact(String contactId);
        void viewContactDetails(String contactId);
        void goToCreate();
    }
}