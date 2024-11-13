package aryananta.mobile.createpost;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // Set Listener pada BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_add) {

                        // Pindah ke Activity Notifications
                        Intent notificationsIntent = new Intent(MainActivity.this, PostIklan.class);
                        startActivity(notificationsIntent);
                        return true;
                }
                if (item.getItemId() == R.id.navigation_auctions) {

                    // Pindah ke Activity Notifications
                    Intent notificationsIntent = new Intent(MainActivity.this, Transaksi.class);
                    startActivity(notificationsIntent);
                    return true;
                }

                return false;
            }
        });

//        btPost = this.findViewById(R.id.bt);

//        this.btPost.setOnClickListener(this);

//        Intent i = new Intent(this, input_data.class);
//        startActivity(i);


    }
//    public void onClick(View view) {
//        if(view.getId() == R.id.btPost){
//            try {
//                Intent i = new Intent(this, input_data.class);
//                startActivity(i);
//            } catch (Exception e){}
//        }
//    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}