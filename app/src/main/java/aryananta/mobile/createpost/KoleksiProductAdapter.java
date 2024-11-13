package aryananta.mobile.createpost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class KoleksiProductAdapter
extends RecyclerView.Adapter {

    private final Context ctx;
    private final List<Produk> koleksi;

    public KoleksiProductAdapter(Context ctx, List<Produk> koleksi){
        this.ctx = ctx;
        this.koleksi = koleksi;
    }
    private class VH extends RecyclerView.ViewHolder{
        private final TextView nama_barang;
        private final TextView nama_penawar;
        private final TextView harga;
        private final TextView tanggal;
        private final TextView waktu;
        private final ImageView gambar;
        private final Button btEnd;

        private Produk produk;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.nama_barang = itemView.findViewById(R.id.tv_namaBarang);
            this.nama_penawar = itemView.findViewById(R.id.tv_penawar);
            this.harga = itemView.findViewById(R.id.tv_harga);
            this.tanggal = itemView.findViewById(R.id.tv_Tanggal);
            this.waktu = itemView.findViewById(R.id.tv_Waktu);
            this.gambar = itemView.findViewById(R.id.imageView);
            this.btEnd = itemView.findViewById(R.id.btEnd);
        }
        private void setProduk(Produk b){
            this.produk = b;

            Glide.with(ctx)
                    .load(b.gambar)  // URL atau path gambar
                    .placeholder(R.drawable.ic_launcher_foreground) // Gambar placeholder saat loading
                    .into(gambar);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.item_produk, parent, false);
        VH vh = new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Produk p = this.koleksi.get(position);
        VH vh = (VH) holder;
        vh.nama_barang.setText(p.nama_barang);
        vh.nama_penawar.setText(p.nama_penawar);
        vh.harga.setText(p.harga);
        vh.tanggal.setText(p.tanggal);
        vh.waktu.setText(p.waktu);
//        vh.gambar.setImageResource(p.gambar);
        vh.btEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Anda telah mengakhiri lelang produk ini", Toast.LENGTH_SHORT).show();
                koleksi.remove(p);
                notifyDataSetChanged();
            }
        });

        vh.setProduk(p);
    }

    @Override
    public int getItemCount() {
        return this.koleksi.size();
    }
}
