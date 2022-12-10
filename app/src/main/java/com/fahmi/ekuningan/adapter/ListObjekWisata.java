package com.fahmi.ekuningan.adapter;

public class ListObjekWisata {

    String nama_objek,url_foto1;

    public ListObjekWisata(){

    }

    public ListObjekWisata(String nama_objek,String url_foto1){
        this.nama_objek=nama_objek;
        this.url_foto1=url_foto1;
    }

    public String getNama_objek() {
        return nama_objek;
    }

    public void setNama_objek(String nama_objek) {
        this.nama_objek = nama_objek;
    }

    public String getUrl_foto1() {
        return url_foto1;
    }

    public void setUrl_foto1(String url_foto1) {
        this.url_foto1 = url_foto1;
    }
}
