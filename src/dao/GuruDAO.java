/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Guru;
import koneksi.Koneksi;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Serge
 */
public class GuruDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList <Guru> listGuru;
    Guru guru;

    public GuruDAO() {
        con = Koneksi.getConnection();
    }

    public void setGuru() throws SQLException {
        guru.setNik(rs.getString("nik"));
        guru.setNama(rs.getString("nama"));
        guru.setJenkel(rs.getString("jenkel"));
        guru.setTmpLahir(rs.getString("tmplahir"));
        guru.setTglLahir(rs.getDate("tgllahir"));
        guru.setAlamat(rs.getString("alamat"));
        guru.setTelp(rs.getString("telpon"));
        guru.setUser(rs.getString("user"));
        guru.setWaktu(rs.getDate("waktu"));
    }

    // CRUD

    public ArrayList<Guru> getListGuru () {
        try {
            listGuru = new ArrayList<>();
            ps = con.prepareStatement("SELECT * FROM guru", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                guru = new Guru();
                setGuru();

                listGuru.add(guru);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listGuru;
    }

    public void insertGuru (String nik, String nama, String jenkel, String tmpLahir, Date tglLahir, String alamat, String telp, String user) {
        String query = "INSERT INTO guru (nik, nama, jenkel, tmpLahir, tglLahir, alamat, telpon, user) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nik);
            ps.setString(2, nama);
            ps.setString(3, jenkel);
            ps.setString(4, tmpLahir);
            ps.setDate(5, tglLahir);
            ps.setString(6, alamat);
            ps.setString(7, telp);
            ps.setString(8, user);
            ps.executeUpdate();

            System.out.println("Guru berhasil ditambahkan");
        }
        catch(SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void updateGuru (String nikNew, String nama, String jenkel, String tmpLahir, Date tglLahir, String alamat, String telp, String user, String nikOld) {
        String query = "UPDATE guru SET " +
                "nik = ?, nama = ?, jenkel = ?, tmpLahir = ?, tglLahir = ?, alamat = ?, telpon = ?, user = ?" +
                "WHERE nik = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nikNew);
            ps.setString(2, nama);
            ps.setString(3, jenkel);
            ps.setString(4, tmpLahir);
            ps.setDate(5, tglLahir);
            ps.setString(6, alamat);
            ps.setString(7, telp);
            ps.setString(8, user);
            ps.setString(9, nikOld);

            ps.executeUpdate();

            System.out.println("Guru berhasil diupdate dengan NIK = " + nikOld + " menjadi NIk = " + nikNew);
        }
        catch (SQLException e) {
            System.out.println("Guru tidak ditemukan dengan NIK = " + nikOld);
        }
    }

    public void deleteGuru (String nik) {
        String query = "DELETE FROM guru WHERE nik = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nik);

            ps.executeUpdate();
            System.out.println("Data guru sudah terhapus");
        }
        catch (SQLException e) {
            System.out.println("Data guru tidak ditemukan dengan NIK = " + nik);
        }
    }

    public Guru getGuruByNik (String nik) {
        guru = new Guru();
        try {
            ps = con.prepareStatement("SELECT * FROM guru WHERE nik = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, nik);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                setGuru();
            }
            else {
                throw new SQLException("Guru tidak ditemukan");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            return guru = null;
        }
        return guru;
    }
    
    public DefaultTableModel getModelAllGuru() {
        Object[][] dataTabel = new Object[getListGuru().size()][9];
        String jenkel;
        for (int i = 0; i < getListGuru().size(); i++) {
            dataTabel[i][0] = getListGuru().get(i).getNik();
            dataTabel[i][1] = getListGuru().get(i).getNama();
            if ("L".equals(getListGuru().get(i).getJenkel()))
                jenkel = "Laki-Laki"; else jenkel = "Perempuan";
            dataTabel[i][2] = jenkel;
            dataTabel[i][3] = getListGuru().get(i).getTmpLahir();
            dataTabel[i][4] = getListGuru().get(i).getTglLahir();
            dataTabel[i][5] = getListGuru().get(i).getAlamat();
            dataTabel[i][6] = getListGuru().get(i).getTelp();
            dataTabel[i][7] = getListGuru().get(i).getUser();
            dataTabel[i][8] = getListGuru().get(i).getWaktu();
        }
        String[] colNames = {"NIK", "Nama", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat", "Telepon", "User", "Waktu"};
        DefaultTableModel model = new DefaultTableModel(
            dataTabel, colNames);
        return model;
    }
    
    public DefaultTableModel getModelGuruByGuru(Guru guru) {
        Object[][] dataTable = new Object[1][9];
        String jenkel;
        
        dataTable[0][0] = guru.getNik();
        dataTable[0][1] = guru.getNama();
        jenkel = "L".equals(guru.getJenkel()) ? "Laki-Laki" : "Perempuan";
        dataTable[0][2] = jenkel;
        dataTable[0][3] = guru.getTmpLahir();
        dataTable[0][4] = guru.getTglLahir();
        dataTable[0][5] = guru.getAlamat();
        dataTable[0][6] = guru.getTelp();
        dataTable[0][7] = guru.getUser();
        dataTable[0][8] = guru.getWaktu();
        
        String[] colNames = {"NIK", "Nama", "Jenis Kelamin", "Tempat Lahir", "Tanggal Lahir", "Alamat", "Telepon", "User", "Waktu"};
        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);
        
        return model;
    }
}
