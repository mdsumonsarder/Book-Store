package com.bookstore.mahir.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookstore.mahir.R;
import com.bookstore.mahir.model.BookItem;
import java.util.ArrayList;

/**
 * Created by jack on 9/24/2017.
 */

public class MyCustomAdapter extends ArrayAdapter<BookItem> implements View.OnClickListener {
    private Context mcontext;
    private ArrayList<BookItem> data;
    public MyCustomAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<BookItem> object) {
        super(context, resource, object);
        this.mcontext=context;
        this.data=object;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View mconvertview=convertView;
        if (mconvertview==null){
            LayoutInflater from = LayoutInflater.from(getContext());
            mconvertview = from.inflate(R.layout.custom, null);
        }
        BookItem bookItem = data.get(position);
        TextView tvdisplayname=mconvertview.findViewById(R.id.tvNameDisplay);
        TextView tvdsplayad=mconvertview.findViewById(R.id.tvAddressDisplay);
        TextView tvname=(TextView) mconvertview.findViewById(R.id.tvName);
        TextView tvaddress=(TextView) mconvertview.findViewById(R.id.tvAddress);
        Button btnphone=mconvertview.findViewById(R.id.btnCall);
        Button btnemail=mconvertview.findViewById(R.id.btnMail);
        ImageView ivbook=mconvertview.findViewById(R.id.ivbook);
        tvdisplayname.setText(""+ bookItem.getName());
        tvdsplayad.setText(""+ bookItem.getAddress());
        ivbook.setImageResource(R.drawable.book_image);
        btnphone.setOnClickListener(this);
        btnemail.setOnClickListener(this);
        return mconvertview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCall:

                break;
            case R.id.btnMail:

                break;
        }

    }
}
