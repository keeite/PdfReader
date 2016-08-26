package tk.keitedev.pdfreader.others;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import tk.keitedev.pdfreader.entities.Book;
import tk.keitedev.pdfreader.entities.History;

/**
 * Created by daniel on 20/08/2016.
 */
public class DataBase {

    private static DataBase db = null;
    private SQLiteDatabase sqlite;

    private DataBase(Context context){
               sqlite = new CreateOpenDB(context,"Library",null,3).getWritableDatabase();
    }

    public static DataBase getInstance(Context context){
        if(db == null) db = new DataBase(context);
        return db;
    }

    public List<Book> getBooks(){
        List<Book> books = new ArrayList<>();
        Cursor c = sqlite.rawQuery("SELECT * FROM books ORDER BY title", null);

        if(c.moveToFirst()) {
            do {
                books.add(new Book(c.getInt(0),c.getString(1),c.getString(2)));
            } while (c.moveToNext());
        }
        return books;
    }

    public void insertBook(Book book){
        sqlite.execSQL("INSERT INTO books(title,path) VALUES ('"+ book.getTitle() +"','"+ book.getPath() +"')");
    }

    public Boolean isExist(String title){
        return title.equalsIgnoreCase(getBookByTitle(title).getTitle());
    }

    public Book getBookByTitle(String title){
        Cursor c = sqlite.rawQuery("SELECT * FROM books WHERE title = ?", new String[]{title});
        Book book = new Book();
        if (c.moveToFirst()){
            book.setId(c.getInt(0));
            book.setTitle(c.getString(1));
            book.setPath(c.getString(2));
        }
        return book;
    }

    /**
     *
     * Return -1 if there a error else return the next ID
     * @return int
     */
    public int getNextID(){
        int id = 1;
        Cursor c = sqlite.rawQuery("SELECT MAX(id) FROM books",null);
        if (c != null){
            c.moveToFirst();
            id = c.getInt(0) + 1;
        }
        return id;
    }

    public void delete(int idBook){
        sqlite.execSQL("DELETE FROM books WHERE id = ?",new Object[]{idBook});
        sqlite.execSQL("DELETE FROM history WHERE idBook = ?",new Object[]{idBook});
    }

    public History getHistory(int idBook){
        Cursor c = sqlite.rawQuery("SELECT * FROM history WHERE idBook = " + idBook,null);

        History history = new History();
        if (c.moveToFirst()){
            history.setId(c.getInt(0));
            history.setLastPage(c.getInt(1));
            history.setTotalPages(c.getInt(2));

        }
        return history;
    }

    public Boolean isExistHistory(int idBook){
        return idBook == getHistory(idBook).getId();
    }

    public void insertHistory(History history){

        sqlite.execSQL("INSERT INTO history(idBook,lastPage,totalPages) VALUES ("+ history.getId() +","+ history.getLastPage() +","+ history.getTotalPages() +")");
    }

    public void updateHistory(History history){

        sqlite.execSQL("UPDATE history SET lastPage = ? WHERE idBook = ?",new Object[]{history.getLastPage(),history.getId()});
    }

    public void insertOrUpdateHistory(History history){

        if (isExistHistory(history.getId())) {
            updateHistory(history);
        }else {
            insertHistory(history);
        }

    }

    public void clearAll(){
        clearAllBooks();
        clearALLHistory();
    }
    private void clearAllBooks() {
        sqlite.execSQL("DELETE FROM books");
    }

    private void clearALLHistory(){
        sqlite.execSQL("DELETE FROM history");
    }

}
