package com.bookstore.mahir.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bookstore.mahir.R;
import com.bookstore.mahir.adapter.BookAdapter;
import com.bookstore.mahir.model.BookItem;
import com.bookstore.mahir.utils.CommonFileUtils;
import com.bookstore.mahir.utils.FileUtils;
import com.bookstore.mahir.utils.Helper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements BookAdapter.Callback {


    public HomeFragment() {
    }
    private RecyclerView lvdata;
    private ArrayList<BookItem> arrayList = null;
    private BookAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        lvdata=v.findViewById(R.id.lvdata);
        arrayList=new ArrayList<>();
        adapter = new BookAdapter();
        layoutManager = new LinearLayoutManager(getActivity());
        lvdata.setLayoutManager(layoutManager);
        lvdata.setAdapter(adapter);
        adapter.setCallback(this);
        Helper.initProgressDialog(getActivity());

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("book_information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    BookItem bookItem =snapshot.getValue(BookItem.class);
                    arrayList.add(bookItem);
                }
                adapter.addAllData(arrayList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }

    @Override public void downloadClicked(String pdfUrl) {
       // Toast.makeText(getActivity(), "Download", Toast.LENGTH_SHORT).show();
        downloadInLocalFile(pdfUrl);
    }

    @Override public void callClicked() {
        Toast.makeText(getActivity(), "Call", Toast.LENGTH_SHORT).show();
    }

    @Override public void mailClicked() {
        Toast.makeText(getActivity(), "mail", Toast.LENGTH_SHORT).show();
    }

    private void downloadInLocalFile(String path) {

        File dir = new File(Environment.getExternalStorageDirectory() + "/pdf");

        StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("pdf/"+path+"");

        final File file = new File(dir, UUID.randomUUID().toString() + ".png");
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final FileDownloadTask fileDownloadTask = fileRef.getFile(file);

        Helper.mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fileDownloadTask.cancel();
            }
        });
        Helper.mProgressDialog.show();

        fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Helper.dismissProgressDialog();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Helper.dismissProgressDialog();

            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                int progress = (int) ((100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                Helper.setProgress(progress);
            }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        Helper.dismissProgressDialog();
    }
}