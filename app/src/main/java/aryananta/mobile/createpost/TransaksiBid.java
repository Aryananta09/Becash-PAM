package aryananta.mobile.createpost;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TransaksiBid extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Bid> koleksiBid;
    private KoleksiBidAdapter koleksiBidAdapter;
    private RecyclerView rvKoleksi;
    private Button btBid, btProduk, btTampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        this.koleksiBid =new ArrayList<Bid>();

//        this.koleksiBid.add(new Bid("Rak Meja", "Dhiya Ulhaq", "Rumah Dhiya", "20.000", "12-03-2024", "20.19", R.drawable.rak_meja));
//        this.koleksiBid.add(new Bid("Kursi Kayu", "Asep Mulyana", "Toko Asep", "50.000", "15-04-2024", "10.30", R.drawable.kursi_kayu));
//        this.koleksiBid.add(new Bid("Lemari Antik", "Siti Aminah", "Rumah Siti", "100.000", "20-05-2024", "14.45", R.drawable.lemari_antik));
//        this.koleksiBid.add(new Bid("Meja Belajar", "Budi Santoso", "Kantor Budi", "75.000", "25-06-2024", "18.00", R.drawable.meja_belajar));
//        this.koleksiBid.add(new Bid("Sofa Kulit", "Rinawati", "Galeri Rina", "250.000", "30-07-2024", "21.15", R.drawable.sofa));

        this.koleksiBidAdapter = new KoleksiBidAdapter(this, this.koleksiBid);

        this.rvKoleksi =this.findViewById(R.id.rvBid);
        this.rvKoleksi.setLayoutManager(new LinearLayoutManager(this));
        this.rvKoleksi.setAdapter(this.koleksiBidAdapter);

        this.btBid = this.findViewById(R.id.btBid);
        this.btProduk = this.findViewById(R.id.btProduct);
        this.btTampil = this.findViewById(R.id.btTampil);

        this.btBid.setOnClickListener(this);
        this.btProduk.setOnClickListener(this);
        this.btTampil.setOnClickListener(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

// Set selected item to auctions when in TransaksiAct
        bottomNavigationView.setSelectedItemId(R.id.navigation_auctions);

// Set Listener pada BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Intent notificationsIntent = new Intent(TransaksiBid.this, MainActivity.class);
                    startActivity(notificationsIntent);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_add) {
                    Intent notificationsIntent = new Intent(TransaksiBid.this, PostIklan.class);
                    startActivity(notificationsIntent);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_auctions) {
                    Intent notificationsIntent = new Intent(TransaksiBid.this, TransaksiBid.class);
                    startActivity(notificationsIntent);
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btBid){
            Intent i = new Intent(this, TransaksiBid.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        } else if (view.getId() == R.id.btProduct) {
            Intent i = new Intent(this, TransaksiProduct.class);
            startActivity(i);
        } if (view.getId() == R.id.btTampil) {
            // URL dari server yang akan diambil datanya
            String url = "http://172.16.118.83/BeCash/bid.php";
            // Memulai AsyncTask untuk mendapatkan data dari server
            new FetchDataTask().execute(url);
        }
    }
    private class FetchDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                try {
                    Gson gson = new Gson();
                    Bid[] b = gson.fromJson(response, Bid[].class);
                    koleksiBid.clear();
                    for (Bid bid : b) {
                        koleksiBid.add(bid);
                    }
                    koleksiBidAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(TransaksiBid.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TransaksiBid.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}