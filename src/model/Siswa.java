/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Serge
 */
public class Siswa {
    private String nis;
    private String nama;
    private String tmpLahir;
    private Date tglLahir;
    private String alamat;
    private String jenkel;
    private String telp;
    private String ibuKandung;
    private String ayahKandung;
    private String user;
    private Date waktu;
    private String foto;
    
    public Siswa (String nis, String nama, String tmpLahir, Date tglLahir, String alamat, String jenkel, 
            String telp, String ibuKandung, String ayahKandung, String user, String foto) {
        this.nis = nis;
        this.nama = nama;
        this.tmpLahir = tmpLahir;
        this.tglLahir = tglLahir;
        this.alamat = alamat;
        this.jenkel = jenkel;
        this.telp = telp;
        this.ibuKandung = ibuKandung;
        this.ayahKandung = ayahKandung;
        this.user = user;
        this.foto = foto;
    }
    
    public Siswa() {}

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTmpLahir() {
        return tmpLahir;
    }

    public void setTmpLahir(String tmpLahir) {
        this.tmpLahir = tmpLahir;
    }

    public Date getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(Date tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenkel() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getIbuKandung() {
        return ibuKandung;
    }

    public void setIbuKandung(String ibuKandung) {
        this.ibuKandung = ibuKandung;
    }

    public String getAyahKandung() {
        return ayahKandung;
    }

    public void setAyahKandung(String ayahKandung) {
        this.ayahKandung = ayahKandung;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getWaktu() {
        return waktu;
    }

    public void setWaktu(Date waktu) {
        this.waktu = waktu;
    }
    
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
