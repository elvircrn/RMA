package ba.unsa.etf.rma.elvircrn.rma17_17455;

import java.io.Serializable;

public class Muzicar implements Serializable {
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getZanr() {
        return zanr;
    }

    public void setZanr(String zanr) {
        this.zanr = zanr;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public Muzicar() { }

    public Muzicar(String ime, String prezime, String zanr, String web, String bio) {
        this.ime = ime;
        this.prezime = prezime;
        this.zanr = zanr;
        this.web = web;
        this.bio = bio;
    }

    public Muzicar(String ime) {
        this.ime = ime;
        this.web = "https://www.google.ba/";
    }

    protected String ime;
    protected String prezime;
    protected String zanr;
    protected String web;
    protected String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return ime;
    }
}
