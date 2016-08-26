package tk.keitedev.pdfreader.entities;

/**
 * Created by daniel on 20/08/2016.
 */
public class History {
    private int id,lastPage,totalPages;

    public History(int id,int lastPage,int totalPages){
        this.id = id;
        this.lastPage = lastPage;
        this.totalPages = totalPages;

    }
    public History(){}

    public int getId() {
        return id;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
