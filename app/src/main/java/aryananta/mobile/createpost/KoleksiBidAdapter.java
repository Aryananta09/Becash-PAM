package aryananta.mobile.createpost;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class KoleksiBidAdapter
extends RecyclerView.Adapter {

    private final Context ctx;
    private final List<Bid> koleksi;

    public KoleksiBidAdapter(Context ctx, List<Bid> koleksi){
        this.ctx = ctx;
        this.koleksi = koleksi;
    }

    private class VH extends RecyclerView.ViewHolder{
        private final TextView nama_barang;
        private final TextView nama_penjual;
        private final TextView alamat;
        private final TextView harga;
        private final TextView tanggal;
        private final TextView waktu;
        private final ImageView gambar;
        private final Button btTawar;

        private Bid bid;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.nama_barang = itemView.findViewById(R.id.tv_namaBarang);
            this.nama_penjual = itemView.findViewById(R.id.tv_penjual);
            this.alamat = itemView.findViewById(R.id.tv_Alamat);
            this.harga = itemView.findViewById(R.id.tv_Harga);
            this.tanggal = itemView.findViewById(R.id.tv_Tanggal);
            this.waktu = itemView.findViewById(R.id.tv_Waktu);
            this.gambar = itemView.findViewById(R.id.imageView);
            this.btTawar = itemView.findViewById(R.id.btTawar);
        }
        private void setBid(Bid b){

            this.bid = b;
            Glide.with(ctx)
                    .load(b.gambar)  // URL atau path gambar
                    .placeholder(R.drawable.ic_launcher_foreground) // Gambar placeholder saat loading
                    .into(gambar);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.item_bid, parent, false);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bid b =this.koleksi.get(position);
        VH vh = (VH) holder;
        vh.nama_barang.setText(b.nama_barang);
        vh.nama_penjual.setText(b.nama_penjual);
        vh.harga.setText(b.harga);
        vh.alamat.setText(b.alamat);
        vh.tanggal.setText(b.tanggal);
        vh.waktu.setText(b.waktu);

//        vh.gambar.setImageResource(b.gambar);
        vh.btTawar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat EditText untuk input harga bid
                EditText inputHarga = new EditText(ctx);
                inputHarga.setInputType(InputType.TYPE_CLASS_NUMBER);

                // Membuat AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Masukkan harga bid : "); // Judul dengan nama produk
                builder.setView(inputHarga);

                // Menambahkan tombol selesai
                builder.setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hargaBid = inputHarga.getText().toString();
                        // Tambahkan aksi yang diperlukan setelah input harga
                        Toast.makeText(ctx, "Harga bid adalah " + hargaBid, Toast.LENGTH_SHORT).show();
                    }
                });

                // Menampilkan AlertDialog
                builder.show();
            }
        });

        vh.setBid(b);
    }

    @Override
    public int getItemCount() {
        return this.koleksi.size();
    }
}
