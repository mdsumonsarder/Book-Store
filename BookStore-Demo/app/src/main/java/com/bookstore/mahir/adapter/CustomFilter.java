package com.bookstore.mahir.adapter;

import android.widget.Filter;

import com.bookstore.mahir.model.BookItem;
import java.util.ArrayList;


public class CustomFilter extends Filter{

  SearchAdapter adapter;
  ArrayList<BookItem> filterList;


  public CustomFilter(ArrayList<BookItem> filterList,SearchAdapter adapter)
  {
    this.adapter=adapter;
    this.filterList=filterList;

  }

  //FILTERING OCURS
  @Override
  protected FilterResults performFiltering(CharSequence constraint) {
    FilterResults results=new FilterResults();

    //CHECK CONSTRAINT VALIDITY
    if(constraint != null && constraint.length() > 0)
    {
      //CHANGE TO UPPER
      constraint=constraint.toString().toUpperCase();
      //STORE OUR FILTERED PLAYERS
      ArrayList<BookItem> filteredBooks=new ArrayList<>();

      for (int i=0;i<filterList.size();i++)
      {
        //CHECK
        if(filterList.get(i).getName().toUpperCase().contains(constraint))
        {
          //ADD PLAYER TO FILTERED PLAYERS
          filteredBooks.add(filterList.get(i));
        }
      }

      results.count=filteredBooks.size();
      results.values=filteredBooks;
    }else
    {
      results.count=filterList.size();
      results.values=filterList;

    }


    return results;
  }

  @Override
  protected void publishResults(CharSequence constraint, FilterResults results) {

    adapter.bookList = (ArrayList<BookItem>) results.values;

    //REFRESH
    adapter.notifyDataSetChanged();
  }
}