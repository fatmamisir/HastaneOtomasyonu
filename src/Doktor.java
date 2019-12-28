
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

public class Doktor extends Insan { //SubClass
    private Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;

    public Doktor() {
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

    public Doktor(int id, String kullaniciAdi, String Sifre, String tcNo, String ad, String soyad, String tel, String adres) {
        super(id, kullaniciAdi, Sifre, tcNo, ad, soyad, tel, adres);
    }

    @Override
    public boolean girisYap(String kullaniciAdi, String parola) {
        String sorgu="select *from doktorlar where kad=? and sifre=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, parola);
            
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                setId(rs.getInt("id"));
                
                setKullaniciAdi(rs.getString("kad"));
                setSifre(rs.getString("sifre"));
                
                setTcNo(rs.getString("tcno"));
                setAd(rs.getString("ad"));
                setSoyad(rs.getString("soyad"));
                setTel(rs.getString("tel"));
                setAdres(rs.getString("adres"));
            }
            
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @Override
    public void ekle() {
        String sorgu="insert into doktorlar (kad,sifre,tcno,ad,soyad,tel,adres) values (?, ?, ?, ?, ?, ?, ?)";
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
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sil() {
        String sorgu="delete from doktorlar where tcno=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, getTcNo());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void guncelle() {
        String sorgu="Update doktorlar set kad=?, sifre=?, tcno=?,ad=?,soyad=?,tel=?,adres=? where id=?";
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
            Logger.getLogger(Hasta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<Doktor> listele() {
        String sorgu="select * from doktorlar";
        ArrayList<Doktor> liste=new ArrayList<Doktor>();
        try {
            statement=con.createStatement();
            ResultSet rs=statement.executeQuery(sorgu);
            while(rs.next()){
                int id=rs.getInt("id");
                String kAd=rs.getString("kad");
                String sifre=rs.getString("sifre");
                String tc=rs.getString("tcno");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                String tel=rs.getString("tel");
                String adres=rs.getString("adres");

                liste.add(new Doktor(id,kAd,sifre,tc,ad,soyad,tel,adres));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public ArrayList<Doktor> ara() {
        String sorgu="select * from doktorlar where tcno = ? ";
        ArrayList<Doktor> liste=new ArrayList<Doktor>();
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,getTcNo());
            
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String kAd=rs.getString("kad");
                String sifre=rs.getString("sifre");
                String tc=rs.getString("tcno");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                String tel=rs.getString("tel");
                String adres=rs.getString("adres");
                
                
                liste.add(new Doktor(id,kAd,sifre,tc,ad,soyad,tel,adres));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void comboBoxAd(JComboBox combo){
        String sorgu="select * from doktorlar";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                combo.addItem(ad+" "+soyad);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void comboBoxId(JComboBox combo){
        String sorgu="select * from doktorlar where ad=? and soyad=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,combo.getSelectedItem().toString());
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void AdaGoreAra(String ad,String soyad){
        String sorgu="select * from doktorlar where ad=? and soyad=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1,ad);
            preparedStatement.setString(2,soyad);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Doktor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     
}
