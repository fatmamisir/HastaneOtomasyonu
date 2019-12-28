
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Yonetici extends Insan { //Sub Class
    
    private Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    
    public Yonetici() {
        String url="jdbc:mysql://"+Insan.host+":"+Insan.port+"/"+Insan.veritabani;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı.");
        }
        
        try {
            con=DriverManager.getConnection(url,Insan.kullaniciAd,Insan.sifre);
            System.out.println("Bağlantı başarılı...");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız...");
        }
    }

    public Yonetici(int id, String kullaniciAdi, String Sifre, String tcNo, String ad, String soyad, String tel, String adres) {
        super(id, kullaniciAdi, Sifre, tcNo, ad, soyad, tel, adres);
    }

    @Override
    public boolean girisYap(String kullaniciAdi, String parola) {
        String sorgu="select *from yonetici where kad=? and sifre=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, parola);
            
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                setAd(ad);
                setSoyad(soyad);
            }
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(Yonetici.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    

    public ArrayList<Yonetici> ara() {
        String sorgu="select * from yonetici where id = ? ";
        ArrayList<Yonetici> liste=new ArrayList<Yonetici>();
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1,getId());
            
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String kAd=rs.getString("kad");
                String sifre=rs.getString("sifre");
                String tc=rs.getString("tcno");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyadad");
                String tel=rs.getString("tel");
                String adres=rs.getString("adres");
                
                
                liste.add(new Yonetici(id,kAd,sifre,tc,ad,soyad,tel,adres));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Yonetici.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Yonetici> listele() {
        String sorgu="select * from yonetici";
        ArrayList<Yonetici> liste=new ArrayList<Yonetici>();
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            while(rs.next()){
                int id=rs.getInt("id");
                String kAd=rs.getString("kad");
                String sifre=rs.getString("sifre");
                String tc=rs.getString("tcno");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyadad");
                String tel=rs.getString("tel");
                String adres=rs.getString("adres");
                
                
                liste.add(new Yonetici(id,kAd,sifre,tc,ad,soyad,tel,adres));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Yonetici.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void guncelle() {
        String sorgu="Update yonetici set kad=?, sifre=?, tcno=?,ad=?,soyad=?,tel=?,adres=? where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, getKullaniciAdi());
            preparedStatement.setString(2, getSifre());
            preparedStatement.setString(3, getTcNo());
            preparedStatement.setString(4, getAd());
            preparedStatement.setString(5, getSoyad());
            preparedStatement.setString(6, getTel());
            preparedStatement.setString(7, getAdres());
            preparedStatement.setInt(8, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Yonetici.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sil() {
        String sorgu="delete from yonetici where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Yonetici.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ekle() {
        String sorgu="insert into yonetici (kad,sifre,tcno,ad,soyad,tel,adres) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, getKullaniciAdi());
            preparedStatement.setString(2, getSifre());
            preparedStatement.setString(3, getTcNo());
            preparedStatement.setString(4, getAd());
            preparedStatement.setString(5, getSoyad());
            preparedStatement.setString(6, getTel());
            preparedStatement.setString(7, getAdres());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Yonetici.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
