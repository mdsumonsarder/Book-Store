package com.bookstore.mahir.activity;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.bookstore.mahir.R;
import com.bookstore.mahir.utils.CommonFileUtils;
import com.github.barteksc.pdfviewer.PDFView;
import java.io.File;

public class ViewPdfActivity extends AppCompatActivity {

  private PDFView pdfView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pdf);

    pdfView = findViewById(R.id.pdfView);
    String path = getIntent().getStringExtra("pdf_path");

    if (!path.isEmpty()) {

      File file = CommonFileUtils.GetFile(path);

      Log.d("get_file", "init...");

      if (file != null) {

        Log.d("get_file", file.getAbsolutePath());

        pdfView.fromFile(file)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .enableAnnotationRendering(
                false) // render annotations (such as comments, colors or forms)
            .password(null)
            .scrollHandle(null)
            .enableAntialiasing(true)
            .spacing(0)
            .load();
      }
    }
  }
}
