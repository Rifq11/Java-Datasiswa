/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Serge
 */
public class Nilai {
    private String nis;
    private String semester;
    private String kodeMapel;
    private String kodeGuru;
    private double nilai;
    private Guru guru;
    private Siswa siswa;
    private Mapel mapel;

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getKodeMapel() {
        return kodeMapel;
    }

    public void setKodeMapel(String kodemapel) {
        this.kodeMapel = kodemapel;
    }

    public String getKodeGuru() {
        return kodeGuru;
    }

    public void setKodeGuru(String kodeGuru) {
        this.kodeGuru = kodeGuru;
    }

    public double getNilai() {
        return nilai;
    }

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }

    public Guru getGuru() {
        return guru;
    }

    public void setGuru(Guru guru) {
        this.guru = guru;
    }

    public Siswa getSiswa() {
        return siswa;
    }

    public void setSiswa(Siswa siswa) {
        this.siswa = siswa;
    }

    public Mapel getMapel() {
        return mapel;
    }

    public void setMapel(Mapel mapel) {
        this.mapel = mapel;
    }
    
    
    
    
}
