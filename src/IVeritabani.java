
import java.util.ArrayList;

public interface IVeritabani {
    public static final String kullaniciAd="root";
    public static final String sifre="";
    public static final String veritabani="hastane";
    public static final String host="localhost";
    public static final int port=3306;
    
    /**
     *
     */
    public void ekle();
    public void sil();
    public void guncelle();
    public ArrayList listele();
    public ArrayList ara();
}
