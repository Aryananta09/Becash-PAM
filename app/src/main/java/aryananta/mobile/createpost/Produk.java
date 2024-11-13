package aryananta.mobile.createpost;

public class Produk {
    public String nama_barang;
    public String nama_penawar;
    public String harga;
    public String tanggal;
    public String waktu;
//    public int gambar;
    public String gambar;

    public Produk(String nama_barang, String nama_penawar, String harga, String tanggal, String waktu, String gambar){
        this.nama_barang = nama_barang;
        this.nama_penawar = nama_penawar;
        this.harga = harga;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.gambar = gambar;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getNama_penawar() {
        return nama_penawar;
    }

    public String getHarga() {
        return harga;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getGambar() {
        return gambar;
    }
}
