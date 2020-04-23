package com.bookstore.mahir.activity;

import android.icu.util.Calendar;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bookstore.mahir.R;
import mehdi.sakout.aboutpage.AboutPage;

public class About extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mehdi.sakout.aboutpage.Element element=new mehdi.sakout.aboutpage.Element();
        element.setTitle("About page");
        View aboutpage=new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.bookstore)
                .setDescription("Book Store")
                .addItem(new mehdi.sakout.aboutpage.Element().setTitle("Version 1.1.1"))
                .addEmail("bookstorebd953@gmail.com")
                .addWebsite("http://galaxymartbd.com")
                .addFacebook("https://www.facebook.com/Book-Store-103257514704334/?modal=admin_todo_tour")
                .addYoutube("https://www.youtube.com/channel/UCncsC6DaW5HUZs_NqomKMvA")
                .addTwitter("https://twitter.com/BookSto38163537")
                .addItem(createCopyright())
                .create();
        setContentView(aboutpage);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private mehdi.sakout.aboutpage.Element createCopyright() {
        mehdi.sakout.aboutpage.Element element=new mehdi.sakout.aboutpage.Element();
        final String copystring=String.format("copyright by @  Md Tanvir Rahman/ Bookstore", Calendar.getInstance().get(Calendar.YEAR));
         element.setTitle(copystring);
        element.setGravity(Gravity.CENTER);
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(About.this, copystring, Toast.LENGTH_SHORT).show();
            }
        });
        return element;
    }
}
