package com.bookstore.mahir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bookstore.mahir.R;
import com.bookstore.mahir.model.FileModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raaju
 */

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.MyViewHolder> {

  private Callback mCallback;
  private List<FileModel> fileList = new ArrayList<FileModel>();

  public FileListAdapter() {
  }

  public void addAllData(List<FileModel> fileList) {
    this.fileList.clear();
    this.fileList.addAll(fileList);
    notifyDataSetChanged();
  }

  public void addData(FileModel file) {
    this.fileList.add(file);
    notifyDataSetChanged();
  }

  public void setCallback(Callback callback) {
    mCallback = callback;
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, int position) {
    holder.onUpdate(position);
  }

  @NonNull @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new MyViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_list_item, parent, false));
  }

  @Override
  public int getItemCount() {
    return fileList.size();
  }

  public class MyViewHolder extends RecyclerView.ViewHolder {

    public MyViewHolder(View itemView) {
      super(itemView);
    }

    protected void clear() {

    }

    public void onUpdate(int position) {
      final FileModel fileModel = fileList.get(position);

      AppCompatTextView tvName = itemView.findViewById(R.id.name);
      AppCompatTextView tvPath = itemView.findViewById(R.id.path);
      AppCompatButton readBtn = itemView.findViewById(R.id.button);

      tvName.setText(fileModel.getName());
      tvPath.setText(fileModel.getPath());

      readBtn.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mCallback.itemClicked(fileModel.getName());
        }
      });
    }
  }

  public interface Callback {

    void itemClicked(String pdf);
  }
}