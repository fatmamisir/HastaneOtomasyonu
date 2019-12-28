
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

public class Poliklinikler implements IVeritabani {
    private Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    
    private int id;
    private String ad;
    
    public Poliklinikler(){
        String url="jdbc:mysql://"+IVeritabani.host+":"+IVeritabani.port+"/"+IVeritabani.veritabani;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı.");
        }
        
        try {
            con=DriverManager.getConnection(url,IVeritabani.kullaniciAd,IVeritabani.sifre);
            System.out.println("Bağlantı başarılı...");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız...");
        }
    }

    public Poliklinikler(int id, String ad) {
        this.id = id;
        this.ad = ad;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    @Override
    public void ekle() {
        String sorgu="insert into poliklinikler (ad) values (?)";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,getAd());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sil() {
        String sorgu="Delete from poliklinikler where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    @Override
    public void guncelle() {
        String sorgu="Update poliklinikler set ad=? where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, getAd());
            preparedStatement.setInt(2, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ArrayList<Poliklinikler> listele() {
        String sorgu="select * from poliklinikler";
        ArrayList<Poliklinikler> liste=new ArrayList<Poliklinikler>();
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            while(rs.next()){
                int id=rs.getInt("id");
                String ad=rs.getString("ad");
                liste.add(new Poliklinikler(id,ad));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    

    @Override
    public ArrayList<Poliklinikler> ara() {
        String sorgu="select * from poliklinikler where id = ? ";
        ArrayList<Poliklinikler> liste=new ArrayList<Poliklinikler>();
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,getId());
            
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String ad=rs.getString("ad");
                liste.add(new Poliklinikler(id,ad));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void comboBoxAd(JComboBox combo){
        String sorgu="select * from poliklinikler";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                String ad=rs.getString("ad");
                combo.addItem(ad);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void comboBoxId(JComboBox combo){
        String sorgu="select * from poliklinikler where ad=? ";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,combo.getSelectedItem().toString());
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void polikliinikAra(String ad){
        String sorgu="select * from poliklinikler where ad=?";
     
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,ad);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                setId(id);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(Poliklinikler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
