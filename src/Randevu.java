
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

public class Randevu extends Islemler {
    private Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;

    private String tarih;

    public Randevu(int id, int h_id, int p_id, int d_id,String tarih) {
        super(id, h_id, p_id, d_id);
        this.tarih = tarih;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public Randevu(){
        String url="jdbc:mysql://"+Islemler.host+":"+Islemler.port+"/"+Islemler.veritabani;
        
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
    
    @Override
    void ekle() {
        String sorgu="insert into randevular (h_id,p_id,d_id,tarih) values (?,?,?,?)";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,getH_id());
            preparedStatement.setInt(2,getP_id());
            preparedStatement.setInt(3,getD_id());
            preparedStatement.setString(4, getTarih());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void sil() {
        String sorgu="Delete from randevular where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void guncelle() {
        String sorgu="Update randevular set h_id=?,p_id=?,d_id=?,tarih=? where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,getH_id());
            preparedStatement.setInt(2,getP_id());
            preparedStatement.setInt(3,getD_id());
            preparedStatement.setString(4, getTarih());
            preparedStatement.setInt(5, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void listele(DefaultTableModel tablo) {

        String sorgu="SELECT randevular.id, CONCAT(hastalar.ad,' ',hastalar.soyad),poliklinikler.ad,CONCAT(doktorlar.ad,' ',doktorlar.soyad),randevular.tarih FROM randevular INNER JOIN hastalar ON randevular.h_id = hastalar.id INNER JOIN poliklinikler ON randevular.p_id=poliklinikler.id INNER JOIN doktorlar on randevular.d_id=doktorlar.id";
     
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            
            while(rs.next()){
            Vector v=new Vector();
            v.add(rs.getInt(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            
            tablo.addRow(v);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void doktorAra(DefaultTableModel tablo,int doktorID ) {
        String sorgu="SELECT randevular.id, CONCAT(hastalar.ad,' ',hastalar.soyad),poliklinikler.ad,CONCAT(doktorlar.ad,' ',doktorlar.soyad),randevular.tarih FROM randevular INNER JOIN hastalar ON randevular.h_id = hastalar.id INNER JOIN poliklinikler ON randevular.p_id=poliklinikler.id INNER JOIN doktorlar on randevular.d_id=doktorlar.id where doktorlar.id="+doktorID+"";
     
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            
            while(rs.next()){
            Vector v=new Vector();
            v.add(rs.getInt(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            
            tablo.addRow(v);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void hastaAra(DefaultTableModel tablo, int hastaID) {
        String sorgu="SELECT randevular.id, CONCAT(hastalar.ad,' ',hastalar.soyad),poliklinikler.ad,CONCAT(doktorlar.ad,' ',doktorlar.soyad),randevular.tarih FROM randevular INNER JOIN hastalar ON randevular.h_id = hastalar.id INNER JOIN poliklinikler ON randevular.p_id=poliklinikler.id INNER JOIN doktorlar on randevular.d_id=doktorlar.id where hastalar.id="+hastaID+"";
     
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            
            while(rs.next()){
            Vector v=new Vector();
            v.add(rs.getInt(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            
            tablo.addRow(v);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void poliklinikAra(DefaultTableModel tablo, int poliklinikID, int hastaID) {
        String sorgu="SELECT randevular.id, CONCAT(hastalar.ad,' ',hastalar.soyad),poliklinikler.ad,CONCAT(doktorlar.ad,' ',doktorlar.soyad),randevular.tarih FROM randevular INNER JOIN hastalar ON randevular.h_id = hastalar.id INNER JOIN poliklinikler ON randevular.p_id=poliklinikler.id INNER JOIN doktorlar on randevular.d_id=doktorlar.id where poliklinikler.id="+poliklinikID+" and hastalar.id="+hastaID+"";
     
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            
            while(rs.next()){
            Vector v=new Vector();
            v.add(rs.getInt(1));
            v.add(rs.getString(2));
            v.add(rs.getString(3));
            v.add(rs.getString(4));
            v.add(rs.getString(5));
            
            tablo.addRow(v);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
