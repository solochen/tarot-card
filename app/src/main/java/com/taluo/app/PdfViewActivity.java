package com.taluo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;
import com.taluo.app.listener.OnItemClickListener;
import com.taluo.app.widget.TarotCardLayout;

import java.util.List;

/**
 * Created by chenshaolong on 2019/2/27.
 */

public class PdfViewActivity extends Activity {

    public static final String TAG = PdfViewActivity.class.getSimpleName();

    public static void start(Context context) {
        context.startActivity(new Intent(context, PdfViewActivity.class));
    }

    Context mContext;
    int pageNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        mContext = this;

        final PDFView pdfView = findViewById(R.id.pdf_view);

        pdfView.fromAsset("sample.pdf")
                .defaultPage(pageNumber)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        pageNumber = page;
                        Log.e(TAG, "page:" + page + "-pageCount:" + pageCount);
                    }
                })
                .enableAnnotationRendering(true)
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int i) {
                        Log.e(TAG, "loadComplete");
                        PdfDocument.Meta meta = pdfView.getDocumentMeta();
                        Log.e(TAG, "title = " + meta.getTitle());
                        Log.e(TAG, "author = " + meta.getAuthor());
                        Log.e(TAG, "subject = " + meta.getSubject());
                        Log.e(TAG, "keywords = " + meta.getKeywords());
                        Log.e(TAG, "creator = " + meta.getCreator());
                        Log.e(TAG, "producer = " + meta.getProducer());
                        Log.e(TAG, "creationDate = " + meta.getCreationDate());
                        Log.e(TAG, "modDate = " + meta.getModDate());

                        printBookmarksTree(pdfView.getTableOfContents(), "-");
                    }
                })
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(new OnPageErrorListener() {
                    @Override
                    public void onPageError(int i, Throwable throwable) {
                        Log.e(TAG, "onPageError");
                    }
                })
                .pageFitPolicy(FitPolicy.BOTH)
                .load();

    }


    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }


}

