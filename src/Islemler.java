
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public abstract class Islemler {
    public static final String kullaniciAd="root";
    public static final String sifre="";
    public static final String veritabani="hastane";
    public static final String host="localhost";
    public static final int port=3306;
    
    private Connection con=null;
    
    private int id;
    private int h_id;
    private int p_id;
    private int d_id;

    public Islemler(int id, int h_id, int p_id, int d_id) {
        this.id = id;
        this.h_id = h_id;
        this.p_id = p_id;
        this.d_id = d_id;
    }
    
    public Islemler(){
        String url="jdbc:mysql://"+host+":"+port+"/"+veritabani;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı.");
        }
        
        try {
            con=DriverManager.getConnection(url,Islemler.kullaniciAd,Islemler.sifre);
            System.out.println("Bağlantı başarılı...");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız...");
        }
    }
    
    abstract void ekle();
    abstract void sil();
    abstract void guncelle();
    abstract void listele(DefaultTableModel tablo);
    abstract void doktorAra(DefaultTableModel tablo,int doktorID);
    abstract void hastaAra(DefaultTableModel tablo,int hastaID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getH_id() {
        return h_id;
    }

    public void setH_id(int h_id) {
        this.h_id = h_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }
}
