package aryananta.mobile.createpost;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FragmentBid extends Fragment {


    private RecyclerView rvBid;
    private KoleksiBidAdapter koleksiBidAdapter;
    private ArrayList<Bid> koleksi;

    public FragmentBid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid, container, false);

        koleksi = new ArrayList<>();
        rvBid = v.findViewById(R.id.rvBid);
        koleksiBidAdapter = new KoleksiBidAdapter(getContext(), koleksi);
        rvBid.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBid.setAdapter(koleksiBidAdapter);

        // URL dari server yang akan diambil datanya
        String url = "http://172.16.153.215/BeCash/bid.php";
        // Memulai AsyncTask untuk mendapatkan data dari server
        new FetchDataTask().execute(url);

        return v;
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
                    koleksi.clear();
                    for (Bid bid : b) {
                        koleksi.add(bid);
                    }
                    koleksiBidAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}