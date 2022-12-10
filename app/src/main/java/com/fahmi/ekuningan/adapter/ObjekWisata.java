package com.fahmi.ekuningan.adapter;

public class ObjekWisata {
    String nama_wisata, lokasi,url_foto1,url_foto2,url_foto3,url_thumb, deskripsi, latitude,longitude;

    public ObjekWisata(){

    }

    public ObjekWisata(String nama_wisata, String lokasi, String url_foto1, String url_foto2,String url_foto3,String url_thumb, String deskripsi, String latitude, String longitude){
        this.nama_wisata=nama_wisata;
        this.lokasi=lokasi;
        this.url_foto1=url_foto1;
        this.url_foto2=url_foto2;
        this.url_foto3=url_foto3;
        this.url_thumb=url_thumb;
        this.deskripsi=deskripsi;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getNama_wisata() {
        return nama_wisata;
    }

    public void setNama_wisata(String nama_wisata) {
        this.nama_wisata = nama_wisata;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getUrl_foto1() {
        return url_foto1;
    }

    public void setUrl_foto1(String url_foto1) {
        this.url_foto1 = url_foto1;
    }

    public String getUrl_foto2() {
        return url_foto2;
    }

    public void setUrl_foto2(String url_foto2) {
        this.url_foto2 = url_foto2;
    }

    public String getUrl_foto3() {
        return url_foto3;
    }

    public void setUrl_foto3(String url_foto3) {
        this.url_foto3 = url_foto3;
    }

    public String getUrl_thumb() {
        return url_thumb;
    }

    public void setUrl_thumb(String url_thumb) {
        this.url_thumb = url_thumb;
    }

    public String getUrl_fotorecomend() {
        return url_thumb;
    }

    public void setUrl_fotorecomend(String url_thumb) {
        this.url_thumb = url_thumb;
    }

    public String getLatitude(){ return latitude;}
    public void  setLatitude(String latitude){ this.latitude=latitude;}

    public String getLongitude(){return  longitude;}
    public void setLongitude(String longitude){this.longitude=longitude;}
}
