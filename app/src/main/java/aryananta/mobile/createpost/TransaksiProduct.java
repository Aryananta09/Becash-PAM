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

public class TransaksiProduct extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Produk> koleksiProduct;
    private KoleksiProductAdapter koleksiProductAdapter;
    private RecyclerView rvKoleksi;
    private Button btBid, btProduk, btTampil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);

        this.koleksiProduct = new ArrayList<Produk>();

//        this.koleksiProduct.add(new Produk("Sepatu Nike", "Naufal Rafly", "200.000", "12-03-2024", "20.19", R.drawable.sepatu_nike));
//        this.koleksiProduct.add(new Produk("Sepatu Converse", "Dua Sinar", "150.000", "15-04-2024", "10.30", R.drawable.sepatu_converse));
//        this.koleksiProduct.add(new Produk("Hoodie Erigo", "Bang Jay", "100.000", "20-05-2024", "14.45", R.drawable.hoodie_erigo));

        this.koleksiProductAdapter = new KoleksiProductAdapter(this, this.koleksiProduct);
        this.rvKoleksi = findViewById(R.id.rvProduct);
        this.rvKoleksi.setLayoutManager(new LinearLayoutManager(this));
        this.rvKoleksi.setAdapter(this.koleksiProductAdapter);

        this.btProduk = findViewById(R.id.btProduct);
        this.btBid = findViewById(R.id.btBid);
        this.btTampil = findViewById(R.id.btTampil);

        btProduk.setOnClickListener(this);
        btBid.setOnClickListener(this);
        btTampil.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

// Set selected item to auctions when in TransaksiAct
        bottomNavigationView.setSelectedItemId(R.id.navigation_auctions);

// Set Listener pada BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Intent notificationsIntent = new Intent(TransaksiProduct.this, MainActivity.class);
                    startActivity(notificationsIntent);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_add) {
                    Intent notificationsIntent = new Intent(TransaksiProduct.this, PostIklan.class);
                    startActivity(notificationsIntent);
                    return true;
                }
                if (item.getItemId() == R.id.navigation_auctions) {
                    Intent notificationsIntent = new Intent(TransaksiProduct.this, TransaksiBid.class);
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
            startActivity(i);
        } else if (view.getId() == R.id.btProduct) {
            Intent i = new Intent(this, TransaksiProduct.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        } if (view.getId() == R.id.btTampil) {
            // URL dari server yang akan diambil datanya
            String url = "http://172.16.118.83/BeCash/product.php";
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
                    Produk[] p = gson.fromJson(response, Produk[].class);
                    koleksiProduct.clear();
                    for (Produk produk : p) {
                        koleksiProduct.add(produk);
                    }
                    koleksiProductAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(TransaksiProduct.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(TransaksiProduct.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}