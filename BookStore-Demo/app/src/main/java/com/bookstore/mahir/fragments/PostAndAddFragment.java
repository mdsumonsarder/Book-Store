package com.bookstore.mahir.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bookstore.mahir.R;
import com.bookstore.mahir.fragments.HomeFragment;
import com.bookstore.mahir.model.BookItem;
import com.bookstore.mahir.utils.FileUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostAndAddFragment extends Fragment implements View.OnClickListener {

  public PostAndAddFragment() {
    // Required empty public constructor
  }

  //DatabaseReference databaseReference;
  private EditText etname, etphone, etaddress;
  private Button btnsubmit, btncancel;
  private TextView tvdatainfirebase, tvPDF;
  private FirebaseStorage storage;
  private StorageReference storageRef;
  private DatabaseReference databaseReference;
  private String pdfPath = null;
  private final static int REQUEST_PDF_OPEN = 12342;
  private LinearLayout UploadProgressBar;
  private StorageReference folderRef, pdfRef;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_post_and_add, container, false);
    storage = FirebaseStorage.getInstance();
    storageRef = storage.getReference();
    etname = v.findViewById(R.id.etName);
    etaddress = v.findViewById(R.id.etAddress);
    etphone = v.findViewById(R.id.etPhone);
    btnsubmit = v.findViewById(R.id.btnSubmit);
    btncancel = v.findViewById(R.id.btnCancel);
    UploadProgressBar = v.findViewById(R.id.UploadProgressBar);
    btnsubmit.setOnClickListener(this);
    btncancel.setOnClickListener(this);
    tvdatainfirebase = v.findViewById(R.id.tvdatainfirebase);
    tvPDF = v.findViewById(R.id.tvPDF);
    tvPDF.setOnClickListener(this);
    databaseReference = FirebaseDatabase.getInstance().getReference().child("book_information");

    return v;
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {

      case R.id.tvPDF:
        selectPdf();
        break;

      case R.id.btnSubmit:
        String address = etaddress.getText().toString();
        String name = etname.getText().toString();
        String phone = etphone.getText().toString();
        String file = tvdatainfirebase.getText().toString();
        if (name.isEmpty()) {
          etname.setError("must be fill up");
          if (file.isEmpty()) {
            tvdatainfirebase.setError("Pick a pdf File");
          }
        } else {
          String KEY = databaseReference.push().getKey();
          String Url = tvdatainfirebase.getText().toString();
          BookItem bookItem = new BookItem(name, address, phone, KEY, "", Url);
          databaseReference.push().setValue(bookItem);
        }
        break;
      case R.id.btnCancel:
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new HomeFragment();
        fragmentTransaction.replace(R.id.loadfagment, fragment);
        fragmentTransaction.commit();
        break;
    }
  }

  public void selectPdf() {

      Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
      intent.setType("application/pdf");
      // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
      startActivityForResult(Intent.createChooser(intent, "Select Pdf"), REQUEST_PDF_OPEN);

  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      if (requestCode == REQUEST_PDF_OPEN) {
        Uri uri = data.getData();
        // Toast.makeText(getActivity(), uri+"", Toast.LENGTH_SHORT).show();
        if (uri != null) {
          String path = FileUtils.getPath(getActivity(), uri);
          uploadUriToFireStorage(path);
        }
      }
    }
  }

  private void uploadUriToFireStorage(String path) {

    Uri uriFile = Uri.fromFile(new File(path));
    folderRef = storageRef.child("pdf/");
    pdfRef = folderRef.child(uriFile.getLastPathSegment() + "");
    tvdatainfirebase.setText(uriFile.getLastPathSegment());
    UploadTask uploadTask = pdfRef.putFile(uriFile);
    disableUI();
    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
      @Override
      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        enableUI();

        pdfRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
          @Override public void onSuccess(Uri uri) {
            String download = uri.toString();
          }
        });
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception exception) {
        // Handle unsuccessful uploads
        enableUI();
        Toast.makeText(getActivity(), "upload failed!", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void disableUI() {

    tvdatainfirebase.setClickable(false);
    tvPDF.setClickable(false);

    etphone.setEnabled(false);
    etname.setEnabled(false);
    etaddress.setEnabled(false);
    etaddress.setEnabled(false);
    tvdatainfirebase.setEnabled(false);
    tvPDF.setEnabled(false);

    UploadProgressBar.setVisibility(View.VISIBLE);
  }

  private void enableUI() {
    UploadProgressBar.setVisibility(View.GONE);

    tvdatainfirebase.setClickable(true);
    tvPDF.setClickable(true);

    etphone.setEnabled(true);
    etname.setEnabled(true);
    etaddress.setEnabled(true);
    tvdatainfirebase.setEnabled(true);
    tvPDF.setEnabled(true);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    // If there's an upload in progress, save the reference so you can query it later
    if (pdfRef != null) {
      outState.putString("reference", pdfRef.toString());
    }
  }

  @Override public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);

    // If there was an upload in progress, get its reference and create a new StorageReference
    String stringRef;
    if (savedInstanceState != null) {
      stringRef = savedInstanceState.getString("reference");
      if (stringRef == null) {
        return;
      }

      pdfRef = FirebaseStorage.getInstance().getReferenceFromUrl(stringRef);

      // Find all UploadTasks under this StorageReference (in this example, there should be one)
      List<UploadTask> tasks = pdfRef.getActiveUploadTasks();
      if (tasks.size() > 0) {
        // Get the task monitoring the upload
        UploadTask task = tasks.get(0);

        // Add new listeners to the task using an Activity scope
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onSuccess(UploadTask.TaskSnapshot state) {
            Toast.makeText(getActivity(), "Upload Completed!", Toast.LENGTH_SHORT).show();
          }
        });
      }
    }
  }
}