package tk.keitedev.pdfreader.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

import tk.keitedev.pdfreader.R;
import tk.keitedev.pdfreader.entities.Book;
import tk.keitedev.pdfreader.others.DataBase;
import tk.keitedev.pdfreader.others.FilterDirectory;

public class ListFileActivity extends ListActivity {

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_files);

        // Use the current directory as title
        path = getIntent().hasExtra("path") ? getIntent().getStringExtra("path") : "/mnt";
        File dir = new File(path);
        setTitle(dir.canRead() ? path : path + " (inacesible)");

        if(dir.list() != null) setListAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,android.R.id.text1,dir.list(new FilterDirectory())));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String filename = (String) getListAdapter().getItem(position);
        filename =  path.endsWith(File.separator) ? path + filename : path + File.separator + filename;

        if(new File(filename).list() == null) {
            Toast.makeText(this, "Ese directorio esta vacio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, ListFileActivity.class);
            intent.putExtra("path", filename);
            startActivity(intent);
            return;
        }else if(!filename.contains(".pdf")) {
            Toast.makeText(this, "no es un PDF", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, Library.class);
        startActivity(intent);
        insertIntoBD(filename);
    }

    private void insertIntoBD(String path) {
        String[] array = path.split(File.separator);
        DataBase db = DataBase.getInstance(null);
        String title = array[array.length - 1];
        if(db.isExist(title)){
            Toast.makeText(this,R.string.book_already_added,Toast.LENGTH_LONG).show();
            return;
        }
        Book book = new Book();

        book.setId(db.getNextID());
        book.setTitle(title);
        book.setPath(path);
        db.insertBook(book);
    }
}
