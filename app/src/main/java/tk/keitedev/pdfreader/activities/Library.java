package tk.keitedev.pdfreader.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import java.util.List;

import tk.keitedev.pdfreader.R;
import tk.keitedev.pdfreader.adapters.LibraryAdapter;
import tk.keitedev.pdfreader.entities.Book;
import tk.keitedev.pdfreader.others.DataBase;

public class Library extends AppCompatActivity {
    GridView grid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().show();
        setTitle(R.string.activity_library);

    }

    @Override
    protected void onResume() {
        super.onResume();
        grid = (GridView) findViewById(R.id.gridLibrary);
        List<Book> books = DataBase.getInstance(this).getBooks();
        new LibraryAdapter(this,books,grid);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()){
            case R.id.action_open:
                i = new Intent(getApplicationContext(),ListFileActivity.class);
                break;
            case R.id.action_library:
                i = new Intent(this,Library.class);
                break;
        }
        startActivity(i);


        return super.onOptionsItemSelected(item);
    }


}
