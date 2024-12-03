/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import koneksi.Koneksi;
import model.Cabeka;
import model.Guru;
import model.Mapel;
import model.Nilai;
import model.Siswa;

/**
 *
 * @author rifqi
 */
public class CabekaDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<Siswa> listSiswa;
    ArrayList<Guru> listGuru;
    ArrayList<Cabeka> listCabeka;
    Siswa siswa;
    Guru guru;
    Cabeka cabeka;
    
    public CabekaDAO() {
        con = Koneksi.getConnection();
    }
    
    public ArrayList<Cabeka> getListCabeka(String[] args) {
        String query = "SELECT * FROM cabekaView WHERE nis = '" + args[0].trim() + "'";
        if (!args[1].isEmpty())
            query += " AND tgl = '" + args[1].trim() + "'";

        try {
            listCabeka = new ArrayList<>();
            ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();

            while (rs.next()) {
                cabeka = new Cabeka();
                cabeka.setNis(rs.getString("nis"));
                cabeka.setTgl(rs.getDate("tgl"));
                cabeka.setPermasalahan(rs.getString("permasalahan"));
                cabeka.setSolusi(rs.getString("solusi"));
                cabeka.setKehadiran(rs.getString("kehadiran"));
                cabeka.setPetugasBK(rs.getString("petugasBK"));

                siswa = new Siswa();
                siswa.setNama(rs.getString("namaSiswa"));
                cabeka.setSiswa(siswa);

                guru = new Guru();
                guru.setNama(rs.getString("namaGuru"));
                cabeka.setGuru(guru);

                listCabeka.add(cabeka);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listCabeka;
    }

    
    public DefaultTableModel getModelAllCabeka(String[] args) {
        listCabeka = getListCabeka(args);

        Object[][] dataTable = new Object[listCabeka.size()][7];

        for (int i = 0; i < listCabeka.size(); i++) {
            dataTable[i][0] = listCabeka.get(i).getNis();
            dataTable[i][1] = listCabeka.get(i).getSiswa().getNama(); 
            dataTable[i][2] = listCabeka.get(i).getTgl();
            dataTable[i][3] = listCabeka.get(i).getPermasalahan();
            dataTable[i][4] = listCabeka.get(i).getSolusi();
            dataTable[i][5] = listCabeka.get(i).getKehadiran();
            dataTable[i][6] = listCabeka.get(i).getGuru().getNama();
        }

        String[] colNames = {"NIS", "Nama Siswa", "Tanggal", "Permasalahan", "Solusi", "Kehadiran", "Nama Guru"};

        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);
        return model;
    }

    public Cabeka getSingleCabeka(Cabeka cabeka) {
        this.cabeka = new Cabeka();
        try {
            String query = "SELECT * FROM cabekaView WHERE nis = ? AND tgl = ?";
            ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ps.setString(1, cabeka.getNis());
            ps.setDate(2, cabeka.getTgl());

            rs = ps.executeQuery();
            rs.beforeFirst();

            if (rs.next()) {
                this.cabeka.setNis(rs.getString("nis"));
                this.cabeka.setTgl(rs.getDate("tgl"));
                this.cabeka.setPetugasBK(rs.getString("petugasBK"));
                this.cabeka.setPermasalahan(rs.getString("permasalahan"));
                this.cabeka.setSolusi(rs.getString("solusi"));
                this.cabeka.setKehadiran(rs.getString("kehadiran"));

                siswa = new Siswa();
                siswa.setNama(rs.getString("namaSiswa"));

                guru = new Guru();
                guru.setNama(rs.getString("namaGuru"));

                this.cabeka.setSiswa(siswa);
                this.cabeka.setGuru(guru);
            } else {
                this.cabeka = null;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            this.cabeka = null;
        }
        return this.cabeka;
    }

    public String getNamaSiswa(String nis) {
        String nama = null;
        try {
            ps = con.prepareStatement("SELECT * FROM siswa WHERE nis = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, nis);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                nama = rs.getString("nama");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
            nama = "";
        }
        return nama;
    }
    
    public String getNamaGuru(String nik) {
        String nama = null;
        try {
            ps = con.prepareStatement("SELECT * FROM guru WHERE nik = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, nik);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                nama = rs.getString("nama");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
            nama = "";
        }
        return nama;
    }

    public DefaultTableModel getLookSiswa() {
        DefaultTableModel modelSiswa = new DefaultTableModel();
        try {
            listSiswa = new ArrayList<>();
            ps = con.prepareStatement("SELECT nis, nama FROM siswa", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                siswa = new Siswa();
                siswa.setNis(rs.getString("nis"));
                siswa.setNama(rs.getString("nama"));
                listSiswa.add(siswa);
            }
        } catch (SQLException e) {
            System.out.println(e);
            listSiswa = null;
        }
        
        Object[][] dataTable = new Object[listSiswa.size()][2];
        for (int i = 0; i < listSiswa.size(); i++) {
            dataTable[i][0] = listSiswa.get(i).getNis();
            dataTable[i][1] = listSiswa.get(i).getNama();
        }
        String[] colNames = {"NIS", "Nama"};
        modelSiswa = new DefaultTableModel(dataTable, colNames);
        return modelSiswa;
    }
    
    public DefaultTableModel getLookGuru() {
        DefaultTableModel modelGuru = new DefaultTableModel();
        try {
            listGuru = new ArrayList<>();
            ps = con.prepareStatement("SELECT nik, nama FROM guru", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                guru = new Guru();
                guru.setNik(rs.getString("nik"));
                guru.setNama(rs.getString("nama"));
                listGuru.add(guru);
            }
        } catch (SQLException e) {
            System.out.println(e);
            listGuru = null;
        }
        
        Object[][] dataTable = new Object[listGuru.size()][2];
        for (int i = 0; i < listGuru.size(); i++) {
            dataTable[i][0] = listGuru.get(i).getNik();
            dataTable[i][1] = listGuru.get(i).getNama();
        }
        String[] colNames = {"NIK", "Nama"};
        modelGuru = new DefaultTableModel(dataTable, colNames);
        return modelGuru;
    }

    public void saveCabeka(Cabeka cabeka) {
        String query = "SELECT * FROM cabeka WHERE nis = ? AND tgl = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, cabeka.getNis());
            ps.setDate(2, cabeka.getTgl());
            rs = ps.executeQuery();

            if (rs.next()) {
                query = "UPDATE cabeka SET permasalahan = ?, solusi = ?, kehadiran = ?, petugasBK = ? WHERE nis = ? AND tgl = ?";
            } else {
                query = "INSERT INTO cabeka (permasalahan, solusi, kehadiran, petugasBK, nis, tgl) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
            }

            ps = con.prepareStatement(query);
            ps.setString(1, cabeka.getPermasalahan());
            ps.setString(2, cabeka.getSolusi());
            ps.setString(3, cabeka.getKehadiran());
            ps.setString(4, cabeka.getPetugasBK());
            
            ps.setString(5, cabeka.getNis());
            ps.setDate(6, cabeka.getTgl());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteCabeka(String nis, Date tgl) {
        String query = "DELETE FROM cabeka WHERE nis = ? AND tgl = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nis);
            ps.setDate(2, tgl);

            ps.executeUpdate();
            System.out.println("Cabeka berhasil dihapus");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
}
