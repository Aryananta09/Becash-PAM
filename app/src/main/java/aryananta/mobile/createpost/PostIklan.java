package aryananta.mobile.createpost;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class PostIklan extends AppCompatActivity {

//    static final int PICK_IMAGE = 1;
//    static final int REQUEST_IMAGE_CAPTURE = 2;

    private Button btnPost;
    private ImageButton btnBack, btnImage;
    private EditText etJudulBarang, etDeskripsi, etHargaOpenBid, etHargaBuyNow, etTanggal, etWaktu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_input_data);

        btnPost = this.findViewById(R.id.btn_post_iklan);
        btnBack = this.findViewById(R.id.backButton);
        btnImage = this.findViewById(R.id.bt_foto_barang);

        etJudulBarang = findViewById(R.id.et_judul_barang);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etHargaOpenBid = findViewById(R.id.et_harga_open_bid);
        etHargaBuyNow = findViewById(R.id.et_harga_buy_now);
        etTanggal = findViewById(R.id.et_tanggal);
        etWaktu = findViewById(R.id.et_waktu);

        btnPost.setEnabled(false);

        // Tambahkan TextChangedListener ke setiap EditText
        etJudulBarang.addTextChangedListener(textWatcher);
        etDeskripsi.addTextChangedListener(textWatcher);
        etHargaOpenBid.addTextChangedListener(textWatcher);
        etHargaBuyNow.addTextChangedListener(textWatcher);
        etTanggal.addTextChangedListener(textWatcher);
        etWaktu.addTextChangedListener(textWatcher);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menggunakan Intent untuk kembali ke MainActivity
                Intent intent = new Intent(PostIklan.this, MainActivity.class);
                startActivity(intent);
                finish(); // Menutup ActivityB jika tidak ingin kembali lagi
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan dialog sukses
                showSuccessDialog();
            }
        });



        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        etWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Cek apakah semua field sudah terisi
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // Method untuk mengecek apakah semua field sudah terisi
    private void checkFieldsForEmptyValues() {
        String judulBarang = etJudulBarang.getText().toString();
        String deskripsi = etDeskripsi.getText().toString();
        String hargaOpenBid = etHargaOpenBid.getText().toString();
        String hargaBuyNow = etHargaBuyNow.getText().toString();
        String tanggal = etTanggal.getText().toString();
        String waktu = etWaktu.getText().toString();

        // Jika semua EditText tidak kosong, aktifkan tombol Post
        btnPost.setEnabled(!judulBarang.isEmpty() && !deskripsi.isEmpty() && !hargaOpenBid.isEmpty()
                && !hargaBuyNow.isEmpty() && !tanggal.isEmpty() && !waktu.isEmpty());
    }

    private void showSuccessDialog() {
        // Membuat dan menampilkan AlertDialog
        new AlertDialog.Builder(PostIklan.this)
                .setTitle("Sukses")
                .setMessage("Iklan berhasil diposting!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tutup dialog ketika tombol OK ditekan
                        Intent intent = new Intent(PostIklan.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();
    }
    private void showDatePicker() {
        // Mengambil tanggal saat ini
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Membuat DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(PostIklan.this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Mengatur tanggal yang dipilih ke EditText
            etTanggal.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        // Mengambil waktu saat ini
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Membuat TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(PostIklan.this, (view, selectedHour, selectedMinute) -> {
            // Mengatur waktu yang dipilih ke EditText
            etWaktu.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
        }, hour, minute, true);

        timePickerDialog.show();
    }

}