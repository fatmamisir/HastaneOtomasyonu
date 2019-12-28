
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Insan { //Super Class
    
    public static final String kullaniciAd="root";
    public static final String sifre="";
    public static final String veritabani="hastane";
    public static final String host="localhost";
    public static final int port=3306;
    
    private Connection con=null;
    private Statement statement=null;
    private PreparedStatement preparedStatement=null;
    
    private int id;
    private String kullaniciAdi;
    private String Sifre;
    private String tcNo;
    private String ad;
    private String soyad;
    private String tel;
    private String adres;
    
    public Insan(){
        String url="jdbc:mysql://"+host+":"+port+"/"+veritabani;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı.");
        }
        
        try {
            con=DriverManager.getConnection(url,kullaniciAd,sifre);
            System.out.println("Bağlantı başarılı...");
        } catch (SQLException ex) {
            System.out.println("Bağlantı başarısız...");
        }
    }

    public Insan(int id, String kullaniciAdi, String Sifre, String tcNo, String ad, String soyad, String tel, String adres) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        this.Sifre = Sifre;
        this.tcNo = tcNo;
        this.ad = ad;
        this.soyad = soyad;
        this.tel = tel;
        this.adres = adres;
    }
    
    public void ekle(){
        String sorgu="insert into [tablo adi] (kad,sifre,tcno,ad,soyad,tel,adres) values (?, ?, ?, ?, ?, ?, ?)";
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
            Logger.getLogger(Insan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sil(){
        String sorgu="delete from [tablo adi] where id=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setInt(1, getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Insan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guncelle(){
        String sorgu="Update [tablo adi] set kad=?, sifre=?, tcno=?,ad=?,soyad=?,tel=?,adres=? where id=?";
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
            Logger.getLogger(Insan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList listele(){
        String sorgu="select * from [tablo adi]";
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
            Logger.getLogger(Insan.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ArrayList ara() {
        String sorgu="select * from [tablo adi] where tcno = ? ";
        ArrayList<Insan> liste=new ArrayList<Insan>();
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
                String soyad=rs.getString("soyadad");
                String tel=rs.getString("tel");
                String adres=rs.getString("adres");
                
                
                liste.add(new Insan(id,kAd,sifre,tc,ad,soyad,tel,adres));
            }
            return liste;
        } catch (SQLException ex) {
            Logger.getLogger(Insan.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean girisYap(String kullaniciAdi,String parola){
        String sorgu="select *from [tablo adi] where kad=? and sifre=?";
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, parola);
            
            ResultSet rs=preparedStatement.executeQuery();
            
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(Insan.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String Sifre) {
        this.Sifre = Sifre;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
