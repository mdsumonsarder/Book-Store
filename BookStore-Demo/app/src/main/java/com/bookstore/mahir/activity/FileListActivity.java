package com.bookstore.mahir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bookstore.mahir.R;
import com.bookstore.mahir.adapter.FileListAdapter;
import com.bookstore.mahir.model.FileInfo;
import com.bookstore.mahir.model.FileModel;
import com.bookstore.mahir.utils.CommonFileUtils;
import com.bookstore.mahir.utils.Helper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListActivity extends AppCompatActivity implements FileListAdapter.Callback {

  private RecyclerView fileList;
  private ArrayList<FileModel> arrayList;
  private FileListAdapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private List<FileInfo> filesFromStorage = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_file_list);

    Log.d("storage file:",Environment.getExternalStorageDirectory() + "/pdf");
    filesFromStorage =
        CommonFileUtils.fileList();

    if (filesFromStorage != null) {
      Log.d("storage file:",Environment.getExternalStorageDirectory() + "/pdf");

      arrayList = convertToFileModelList(filesFromStorage);
    } else {
      arrayList = new ArrayList<>();
    }

    fileList = findViewById(R.id.fileList);
    adapter = new FileListAdapter();
    layoutManager = new LinearLayoutManager(this);
    fileList.setLayoutManager(layoutManager);
    fileList.setAdapter(adapter);
    Helper.initProgressDialog(this);
    adapter.setCallback(this);
    adapter.addAllData(arrayList);
  }

  private ArrayList<FileModel> convertToFileModelList(List<FileInfo> filesFromStorage) {

    ArrayList<FileModel> fileModels = new ArrayList<>();

    for (int i = 0; i < filesFromStorage.size(); i++) {
      String name = filesFromStorage.get(i).name();
      String path = filesFromStorage.get(i).path();
      fileModels.add(new FileModel(name, path));
    }

    return fileModels;
  }

  @Override public void itemClicked(String pdf) {
    startActivity(new Intent(FileListActivity.this, ViewPdfActivity.class).putExtra("pdf_path",pdf));
  }
}
