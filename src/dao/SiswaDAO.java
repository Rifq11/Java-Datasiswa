/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import koneksi.Koneksi;
import model.Siswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Serge
 */
public class SiswaDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<Siswa> listSiswa;
    Siswa siswa;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    public SiswaDAO (){
        con = Koneksi.getConnection();
    }

    public ArrayList<Siswa> getListSiswa() {
        try {
            listSiswa = new ArrayList<>();
            ps = con.prepareStatement("SELECT * FROM siswa", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                siswa = new Siswa();
                siswa.setNis(rs.getString("nis"));
                siswa.setNama(rs.getString("nama"));
                siswa.setJenkel(rs.getString("jenkel"));
                siswa.setTmpLahir(rs.getString("tmplahir"));
                siswa.setTglLahir(rs.getDate("tgllahir"));
                siswa.setAlamat(rs.getString("alamat"));
                siswa.setAyahKandung(rs.getString("ayah"));
                siswa.setIbuKandung(rs.getString("ibu"));
                siswa.setTelp(rs.getString("telp"));
                siswa.setUser(rs.getString("user"));
                siswa.setWaktu(rs.getDate("waktu"));
                siswa.setFoto(rs.getString("foto"));

                listSiswa.add(siswa);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listSiswa;
    }
    
    public DefaultTableModel getModelAllSiswa() {
        Object[][] dataTable = new Object[getListSiswa().size()][11];
        String jenkel;
        
        for (int i = 0; i < getListSiswa().size(); i++) {
            dataTable[i][0] = getListSiswa().get(i).getNis();
            dataTable[i][1] = getListSiswa().get(i).getNama();
            jenkel = "L".equals(getListSiswa().get(i).getJenkel()) ? "Laki-Laki" : "Perempuan";
            dataTable[i][2] = jenkel;
            dataTable[i][3] = getListSiswa().get(i).getTmpLahir();
            dataTable[i][4] = getListSiswa().get(i).getTglLahir();
            dataTable[i][5] = getListSiswa().get(i).getAlamat();
            dataTable[i][6] = getListSiswa().get(i).getTelp();
            dataTable[i][7] = getListSiswa().get(i).getAyahKandung();
            dataTable[i][8] = getListSiswa().get(i).getIbuKandung();
            dataTable[i][9] = getListSiswa().get(i).getWaktu();
            dataTable[i][10] = getListSiswa().get(i).getUser();
        }
        String[] colNames = {"NIS", "Nama", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat", "Telepon", "Ayah", "Ibu", "Waktu", "User"};
        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);
        
        return model;
    }
    
    public Siswa getSiswaByNis(String nis) {
        Siswa siswa = new Siswa();
        try {
            String query = "SELECT * FROM siswa WHERE nis = ?";
            ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                siswa.setNis(rs.getString("nis"));
                siswa.setNama(rs.getString("nama"));
                siswa.setJenkel(rs.getString("jenkel"));
                siswa.setTmpLahir(rs.getString("tmplahir"));
                siswa.setTglLahir(rs.getDate("tgllahir"));
                siswa.setAlamat(rs.getString("alamat"));
                siswa.setAyahKandung(rs.getString("ayah"));
                siswa.setIbuKandung(rs.getString("ibu"));
                siswa.setTelp(rs.getString("telp"));
                siswa.setUser(rs.getString("user"));
                siswa.setWaktu(rs.getDate("waktu"));
                siswa.setFoto(rs.getString("foto"));
            }
            else {
                throw new SQLException();
            }
        }
        catch (SQLException e) {
            System.out.println("Siswa tidak ditemukan\n" + e);
            return siswa = null;
        }
        return siswa;
    }
    
    public DefaultTableModel getModelSiswaBySiswa(Siswa sis) {
        Object[][] dataTable = new Object[1][11];
        String jenkel;
        
        dataTable[0][0] = sis.getNis();
        dataTable[0][1] = sis.getNama();
        jenkel = "L".equals(sis.getJenkel()) ? "Laki-Laki" : "Perempuan";
        dataTable[0][2] = jenkel;
        dataTable[0][3] = sis.getTmpLahir();
        dataTable[0][4] = sis.getTglLahir();
        dataTable[0][5] = sis.getAlamat();
        dataTable[0][6] = sis.getTelp();
        dataTable[0][7] = sis.getAyahKandung();
        dataTable[0][8] = sis.getIbuKandung();
        dataTable[0][9] = sis.getWaktu();
        dataTable[0][10] = sis.getUser();
        
        String[] colNames = {"NIS", "Nama", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat", "Telepon", "Ayah", "Ibu", "Waktu", "User"};
        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);
        
        return model;
    }
    
    public void saveSiswa(Siswa siswa) {
        String query = "SELECT * FROM siswa WHERE nis = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, siswa.getNis());
            rs = ps.executeQuery();
            if (rs.next()) {
                query = "UPDATE siswa SET nama = ?, jenkel = ?, tmpLahir = ?, tglLahir = ?, alamat = ?, telp = ?, ayah = ?," +
                "ibu = ?, user = ?, foto = ? WHERE nis = ?";
                System.out.println(siswa.getNis());
            } else {
                query = "INSERT INTO siswa (nama, jenkel, tmpLahir, tglLahir, alamat, telp, ayah, ibu, user, foto, nis) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }
            ps = con.prepareStatement(query);
            ps.setString(1, siswa.getNama());
            ps.setString(2, siswa.getJenkel());
            ps.setString(3, siswa.getTmpLahir());
            String tgl = sdf.format(siswa.getTglLahir());
            ps.setDate(4, Date.valueOf(tgl));
            ps.setString(5, siswa.getAlamat());
            ps.setString(6, siswa.getTelp());
            ps.setString(7, siswa.getAyahKandung());
            ps.setString(8, siswa.getIbuKandung());
            ps.setString(9, siswa.getUser());
            ps.setString(10, siswa.getFoto());
            ps.setString(11, siswa.getNis());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteSiswa(String nis) {
        String query = "DELETE FROM siswa WHERE nis = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nis);
            ps.executeUpdate();
            System.out.println("Data siswa sudah terhapus");
        }
        catch (SQLException e) {
            System.out.println("Data siswa tidak ditemukan");
        }
    }
}
