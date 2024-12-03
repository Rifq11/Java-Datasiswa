/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author rifqi
 */
public class Cabeka {
    String nis;
    Date tgl;
    String permasalahan;
    String solusi;
    String kehadiran;
    String petugasBK;
    Guru guru;
    Siswa siswa;

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

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public String getPermasalahan() {
        return permasalahan;
    }

    public void setPermasalahan(String permasalahan) {
        this.permasalahan = permasalahan;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public String getKehadiran() {
        return kehadiran;
    }

    public void setKehadiran(String kehadiran) {
        this.kehadiran = kehadiran;
    }

    public String getPetugasBK() {
        return petugasBK;
    }

    public void setPetugasBK(String petugasBK) {
        this.petugasBK = petugasBK;
    }
}
