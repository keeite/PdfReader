package tk.keitedev.pdfreader.entities;

/**
 * Created by daniel on 20/08/2016.
 */
public class Book {
    private int id;
    private String title,path;
    public Book(int id,String title,String path){
        this.id = id;
        this.title = title;
        this.path = path;
    }
    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
