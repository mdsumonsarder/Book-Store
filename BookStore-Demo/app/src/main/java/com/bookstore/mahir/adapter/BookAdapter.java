package com.bookstore.mahir.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bookstore.mahir.model.BookItem;
import com.bookstore.mahir.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raaju
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

  private Callback mCallback;
  private List<BookItem> bookList = new ArrayList<BookItem>();

  public BookAdapter() {
  }

  public void addAllData(List<BookItem> bookList) {
    this.bookList.clear();
    this.bookList.addAll(bookList);
    notifyDataSetChanged();
  }

  public void addData(BookItem book) {
    this.bookList.add(book);
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
        LayoutInflater.from(parent.getContext()).inflate(R.layout.custom, parent, false));
  }

  @Override
  public int getItemCount() {
    return bookList.size();
  }



  public class MyViewHolder extends RecyclerView.ViewHolder {

    View item;

    public MyViewHolder(View itemView) {
      super(itemView);
      this.item = itemView;
    }

    protected void clear() {

    }

    public void onUpdate(int position) {
      final BookItem bookItem = bookList.get(position);

      TextView tvdisplayname= item.findViewById(R.id.tvNameDisplay);
      TextView tvdsplayad=item.findViewById(R.id.tvAddressDisplay);
      TextView tvname=(TextView) item.findViewById(R.id.tvName);
      TextView tvaddress=(TextView) item.findViewById(R.id.tvAddress);
      Button btnphone=item.findViewById(R.id.btnCall);
      Button btnemail=item.findViewById(R.id.btnMail);
      Button btnDownload =item.findViewById(R.id.btnDownload);
      ImageView ivbook=item.findViewById(R.id.ivbook);

      tvdisplayname.setText(bookItem.getName());
      tvdsplayad.setText(bookItem.getAddress());
      ivbook.setImageResource(R.drawable.book_image);

      btnphone.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mCallback.callClicked();
        }
      });

      btnemail.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mCallback.mailClicked();
        }
      });

      btnDownload.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          mCallback.downloadClicked(bookItem.getUrl());
        }
      });


    }
  }

  public interface Callback {

    void downloadClicked(String pdfUrl);

    void callClicked();

    void mailClicked();
  }
}