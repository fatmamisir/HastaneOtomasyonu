
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Recete extends Islemler {
    private Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    
    private String ilac;

    public Recete(int id, int p_id, int d_id, int h_id,String ilac) {
        super(id, h_id, p_id, d_id);
        this.ilac = ilac;
    }

    public String getIlac() {
        return ilac;
    }

    public void setIlac(String ilac) {
        this.ilac = ilac;
    }
    
    public Recete(){
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
        String sorgu="insert into receteler (p_id,d_id,h_id,ilac) values (?,?,?,?)";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,getP_id());
            preparedStatement.setInt(2,getD_id());
            preparedStatement.setInt(3,getH_id());
            preparedStatement.setString(4, getIlac());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void sil() {
        String sorgu="Delete from receteler where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void guncelle() {
        String sorgu="Update receteler set p_id=?,d_id=?,h_id=?,ilac=? where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,getP_id());
            preparedStatement.setInt(2,getD_id());
            preparedStatement.setInt(3,getH_id());
            preparedStatement.setString(4, getIlac());
            preparedStatement.setInt(5, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Randevu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void listele(DefaultTableModel tablo) {
        String sorgu="SELECT receteler.id, poliklinikler.ad, CONCAT(doktorlar.ad,' ',doktorlar.soyad), CONCAT(hastalar.ad,' ',hastalar.soyad),receteler.ilac FROM receteler INNER JOIN poliklinikler ON receteler.p_id=poliklinikler.id INNER JOIN doktorlar ON receteler.d_id=doktorlar.id INNER JOIN hastalar ON receteler.h_id=hastalar.id";
     
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
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void doktorAra(DefaultTableModel tablo,int doktorID) {
        String sorgu="SELECT receteler.id, poliklinikler.ad, CONCAT(doktorlar.ad,' ',doktorlar.soyad), CONCAT(hastalar.ad,' ',hastalar.soyad),receteler.ilac FROM receteler INNER JOIN poliklinikler ON receteler.p_id=poliklinikler.id INNER JOIN doktorlar ON receteler.d_id=doktorlar.id INNER JOIN hastalar ON receteler.h_id=hastalar.id where doktorlar.id="+doktorID+"";
     
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
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    void hastaAra(DefaultTableModel tablo, int hastaID) {

        String sorgu="SELECT receteler.id, poliklinikler.ad, CONCAT(doktorlar.ad,' ',doktorlar.soyad), CONCAT(hastalar.ad,' ',hastalar.soyad),receteler.ilac FROM receteler INNER JOIN poliklinikler ON receteler.p_id=poliklinikler.id INNER JOIN doktorlar ON receteler.d_id=doktorlar.id INNER JOIN hastalar ON receteler.h_id=hastalar.id where hastalar.id="+hastaID+"";
     
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
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    void poliklinikAra(DefaultTableModel tablo,int poliklinikID ,int hastaID) {

        String sorgu="SELECT receteler.id, poliklinikler.ad, CONCAT(doktorlar.ad,' ',doktorlar.soyad), CONCAT(hastalar.ad,' ',hastalar.soyad),receteler.ilac FROM receteler INNER JOIN poliklinikler ON receteler.p_id=poliklinikler.id INNER JOIN doktorlar ON receteler.d_id=doktorlar.id INNER JOIN hastalar ON receteler.h_id=hastalar.id where poliklinikler.id="+poliklinikID+" and hastalar.id="+hastaID+"";
     
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
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void hastaDoktorAra(DefaultTableModel tablo,int doktorID ,int hastaID) {

        String sorgu="SELECT receteler.id, poliklinikler.ad, CONCAT(doktorlar.ad,' ',doktorlar.soyad), CONCAT(hastalar.ad,' ',hastalar.soyad),receteler.ilac FROM receteler INNER JOIN poliklinikler ON receteler.p_id=poliklinikler.id INNER JOIN doktorlar ON receteler.d_id=doktorlar.id INNER JOIN hastalar ON receteler.h_id=hastalar.id where doktorlar.id="+doktorID+" and hastalar.id="+hastaID+"";
     
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
            Logger.getLogger(Recete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
