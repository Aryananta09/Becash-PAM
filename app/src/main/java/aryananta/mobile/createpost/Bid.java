package aryananta.mobile.createpost;

public class Bid {
    public String nama_barang;
    public String nama_penjual;
    public String alamat;
    public String harga;
    public String tanggal;
    public String waktu;
//    public int gambar;
    public String gambar;

    public Bid(String nama_barang, String nama_penjual, String alamat, String harga, String tanggal, String waktu, String gambar) {
        this.nama_barang = nama_barang;
        this.nama_penjual = nama_penjual;
        this.alamat = alamat;
        this.harga = harga;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.gambar = gambar;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public String getNama_penjual() {
        return nama_penjual;
    }

    public String getAlamat() {
        return alamat;
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
