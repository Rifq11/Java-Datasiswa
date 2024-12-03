/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import koneksi.Koneksi;
import model.Mapel;
import model.Nilai;
import model.Siswa;
import model.Guru;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Serge
 */
public class NilaiDAO {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList<Nilai> listNilai;
    ArrayList<Siswa> listSiswa;
    ArrayList<Guru> listGuru;
    ArrayList<Mapel> listMapel;
    Nilai nilai;
    Siswa siswa;
    Mapel mapel;
    Guru guru;

    public NilaiDAO () {
        con = Koneksi.getConnection();
    }

    // CRUD

    public ArrayList<Nilai> getListNilai(String[] args) {
        String query = "SELECT * FROM nilaiview WHERE nis = '" + args[0].trim() + "'";
        if (!args[1].isEmpty())
            query += " AND kodemapel = '" + args[1].trim() + "'";
        if (!args[2].isEmpty())
            query += " AND kodeGuru = '" + args[2].trim() + "'";
        if (!args[3].isEmpty())
            query += " AND semester = '" + args[3].trim() + "'";
//        System.out.println(query);
        
        try {
            listNilai = new ArrayList<>();
            ps = con.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                nilai = new Nilai();
                nilai.setNis(rs.getString("nis"));
                nilai.setSemester(rs.getString("semester"));
                nilai.setKodeMapel(rs.getString("kodemapel"));
                nilai.setKodeGuru(rs.getString("kodeGuru"));
                nilai.setNilai(rs.getFloat("nilai"));

                siswa = new Siswa();
                siswa.setNama(rs.getString("nama"));

                mapel = new Mapel();
                mapel.setMapel(rs.getString("mapel"));
                
                guru = new Guru();
                guru.setNama(rs.getString("Pengampu"));
                
                nilai.setSiswa(siswa);
                nilai.setMapel(mapel);
                nilai.setGuru(guru);

                listNilai.add(nilai);
            }
        }
        catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return listNilai;
    }
    
    public DefaultTableModel getModelAllNilai(String[] args) {
        listNilai = getListNilai(args);
        Object[][] dataTable = new Object[listNilai.size()][5];
        
        for (int i = 0; i < listNilai.size(); i++) {
            dataTable[i][0] = listNilai.get(i).getKodeMapel();
            dataTable[i][1] = listNilai.get(i).getMapel().getMapel();
            dataTable[i][2] = listNilai.get(i).getNilai();
            dataTable[i][3] = listNilai.get(i).getKodeGuru();
            dataTable[i][4] = listNilai.get(i).getGuru().getNama();
        }
        String[] colNames = {"Kode Mapel", "Nama Mapel", "Nilai", "Kode Guru", "Nama Guru"};
        DefaultTableModel model = new DefaultTableModel(dataTable, colNames);
        return model;
    }
    
    public Nilai getSingleNilai(Nilai nilai) {
        this.nilai = new Nilai();
        try {
            String query = "SELECT * FROM nilaiview WHERE nis = ? AND kodemapel = ? AND kodeGuru = ?";
            ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, nilai.getNis());
            ps.setString(2, nilai.getKodeMapel());
            ps.setString(3, nilai.getKodeGuru());
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                this.nilai.setNis(rs.getString("nis"));
                this.nilai.setSemester(rs.getString("semester"));
                this.nilai.setKodeMapel(rs.getString("kodemapel"));
                this.nilai.setKodeGuru(rs.getString("kodeGuru"));
                this.nilai.setNilai(rs.getFloat("nilai"));

                siswa = new Siswa();
                siswa.setNama(rs.getString("nama"));

                mapel = new Mapel();
                mapel.setMapel(rs.getString("mapel"));
                
                guru = new Guru();
                guru.setNama(rs.getString("pengampu"));
                
                this.nilai.setSiswa(siswa);
                this.nilai.setMapel(mapel);
                this.nilai.setGuru(guru);
            }
            else {
                throw new SQLException();
            }
        }
        catch (SQLException e) {
            System.out.println(e);
            return this.nilai = null;
        }
        return this.nilai;
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
    
    public String getMapel(String kode) {
        String mapel = null;
        try {
            ps = con.prepareStatement("SELECT * FROM matapelajaran WHERE kode = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, kode);
            rs = ps.executeQuery();
            rs.beforeFirst();
            if (rs.next()) {
                mapel = rs.getString("mapel");
            }
        }
        catch (SQLException e) {
            System.out.println(e);
            mapel = "";
        }
        return mapel;
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
    
    public DefaultTableModel getLookMapel() {
        DefaultTableModel modelMapel = new DefaultTableModel();
        try {
            listMapel = new ArrayList<>();
            ps = con.prepareStatement("SELECT kode, mapel FROM matapelajaran", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                mapel = new Mapel();
                mapel.setKode(rs.getString("kode"));
                mapel.setMapel(rs.getString("mapel"));
                listMapel.add(mapel);
            }
        } catch (SQLException e) {
            System.out.println(e);
            listMapel = null;
        }
        
        Object[][] dataTable = new Object[listMapel.size()][2];
        for (int i = 0; i < listMapel.size(); i++) {
            dataTable[i][0] = listMapel.get(i).getKode();
            dataTable[i][1] = listMapel.get(i).getMapel();
        }
        String[] colNames = {"Kode Mapel", "Nama Mata Pelajaran"};
        modelMapel = new DefaultTableModel(dataTable, colNames);
        return modelMapel;
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
    
    public void saveNilai(Nilai nilai) {
        String query = "SELECT * FROM nilaiview WHERE nis = ? AND kodeMapel = ? AND kodeGuru = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nilai.getNis());
            ps.setString(2, nilai.getKodeMapel());
            ps.setString(3, nilai.getKodeGuru());
            rs = ps.executeQuery();
            if (rs.next()) {
                query = "UPDATE nilaiview SET semester = ?, nilai = ?, kodeMapel = ?, kodeGuru = ? WHERE nis = ?";
            } else {
                query = "INSERT INTO nilaiview (semester, nilai, kodeMapel, kodeGuru, nis) " +
                        "VALUES (?, ?, ?, ?, ?)";
            }
            ps = con.prepareStatement(query);
            ps.setString(1, nilai.getSemester());
            ps.setDouble(2, nilai.getNilai());
            ps.setString(3, nilai.getKodeMapel());
            ps.setString(4, nilai.getKodeGuru());
            ps.setString(5, nilai.getNis());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//    public void insertNilai (String nis, String semester, String kode, Float nilai) {
//        String query = "INSERT INTO nilai (nis, semester, kodemapel, nilai) VALUES (?, ?, ?, ?)";
//        try {
//            ps = con.prepareStatement(query);
//            ps.setString(1, nis);
//            ps.setString(2, semester);
//            ps.setString(3, kode);
//            ps.setFloat(4, nilai);
//
//            ps.executeUpdate();
//            System.out.println("Tambah nilai berhasil");
//        } catch (SQLException e) {
//            System.out.println("Error: " + e);
//        }
//    }
//
//    public void updateNilai (Float nilai, String nisOld, String semesterOld, String kodeOld) {
//        String query = "UPDATE nilai SET nilai = ? WHERE nis = ? AND semester = ? AND kodemapel = ?";
//        try {
//            ps = con.prepareStatement(query);
//            ps.setFloat(1, nilai);
//            ps.setString(2, nisOld);
//            ps.setString(3, semesterOld);
//            ps.setString(4, kodeOld);
//
//            if (ps.executeUpdate() != 1) throw new SQLException();
//            else ps.executeUpdate();
//            System.out.println("Update berhasil!");
//        } catch (SQLException e) {
//            System.out.println("Update gagal!");
//        }
//    }
//
    public void deleteNilai (String nis, String semester, String kode) {
        String query = "DELETE FROM nilai WHERE nis = ? AND semester = ? AND kodemapel = ?";
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, nis);
            ps.setString(2, semester);
            ps.setString(3, kode);

            ps.executeUpdate();
            System.out.println("Nilai berhasil dihapus");
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }
}
