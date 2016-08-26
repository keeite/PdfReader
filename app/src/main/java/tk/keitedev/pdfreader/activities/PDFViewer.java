package tk.keitedev.pdfreader.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.PDFView.Configurator;
import com.joanzapata.pdfview.listener.OnPageChangeListener;

import java.io.File;

import tk.keitedev.pdfreader.R;
import tk.keitedev.pdfreader.entities.Book;
import tk.keitedev.pdfreader.entities.History;
import tk.keitedev.pdfreader.others.DataBase;

public class PDFViewer extends AppCompatActivity implements OnPageChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);




    }

    @Override
    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("path")) {
            PDFView pdf = (PDFView) findViewById(R.id.pdfreader);
            Configurator configurator = pdf.fromFile(new File(getIntent().getStringExtra("path")));
            configurator.defaultPage(getIntent().getIntExtra("lastPage",0));
            configurator.onPageChange(this);
            configurator.load();


        }
    }



    @Override
    public void onPageChanged(int page, int pageCount) {
        Toast.makeText(this,page + "/" + pageCount,Toast.LENGTH_SHORT).show();

        String[] path = getIntent().getStringExtra("path").split(File.separator);
        String title = path[path.length -1];
        DataBase db = DataBase.getInstance(null);
        if(db.isExist(title)){
            Book book = db.getBookByTitle(title);
            History history = new History(book.getId(),page,pageCount);
            db.insertOrUpdateHistory(history);
        }
    }
}
