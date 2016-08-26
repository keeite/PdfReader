package tk.keitedev.pdfreader.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.grantland.widget.AutofitHelper;
import tk.keitedev.pdfreader.R;
import tk.keitedev.pdfreader.activities.PDFViewer;
import tk.keitedev.pdfreader.entities.Book;
import tk.keitedev.pdfreader.entities.History;
import tk.keitedev.pdfreader.others.DataBase;

/**
 * Created by daniel on 20/08/2016.
 */
public class LibraryAdapter extends BaseAdapter{
    private List<Book> books;
    private Context context;
    private GridView grid;
    public LibraryAdapter(Context context, List<Book> books,GridView grid){
        this.books = books;
        this.context = context;
        this.grid = grid;
        grid.setAdapter(this);
    }
    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return books.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        Holder holder;
        final Book book = books.get(position);
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.library_grid,parent,false);
            holder = new Holder(view);
            view.setTag(holder);
        }else {
            holder = (Holder) view.getTag();
        }

        holder.title.setText(book.getTitle());
        holder.path = book.getPath();

        History h = DataBase.getInstance(null).getHistory(book.getId());

        holder.pages.setText(h.getLastPage() + "/" + h.getTotalPages());

        holder.open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PDFViewer.class);
                i.putExtra("path",book.getPath());
                if(DataBase.getInstance(null).isExistHistory(book.getId())){
                    i.putExtra("lastPage",DataBase.getInstance(null).getHistory(book.getId()).getLastPage());
                }

                context.startActivity(i);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                books.remove(position);
                notifyDataSetChanged();
                grid.setAdapter(LibraryAdapter.this);

                DataBase.getInstance(null).delete(book.getId());
                Toast.makeText(context,R.string.book_deleted,Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }



    public static class Holder{
        public static TextView title,pages;
        public static String path;
        public static ImageButton open,remove;

        public Holder(View view){
            title = (TextView) view.findViewById(R.id.textTitle);
            open = (ImageButton) view.findViewById(R.id.buttonOpen);
            remove = (ImageButton) view.findViewById(R.id.buttonRemove);
            pages = (TextView) view.findViewById(R.id.textPages);
            
        }

    }
}
