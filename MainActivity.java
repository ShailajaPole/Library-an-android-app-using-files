package com.example.library;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    EditText et;
    Button iss , ret;
    Spinner sp;
    String str_user,str_book,status,entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iss = (Button) findViewById(R.id.issue);
        ret = (Button) findViewById(R.id.ret);
        et = (EditText) findViewById(R.id.user);

        sp = (Spinner) findViewById(R.id.spin);

        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.books_array,
                android.R.layout.simple_spinner_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(aa);

        iss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_user = et.getText().toString();
                str_book = sp.getSelectedItem().toString();
                status = getstatus(str_book);

                if (status.equals("i")) {
                    Toast.makeText(getApplicationContext(), "Issued to other User", Toast.LENGTH_SHORT).show();
                } else {
                    String entry = ""+str_user+","+str_book+","+"i"+"\n";
                    writerec(entry);
                    Toast.makeText(getApplicationContext(), "Issue", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_user = et.getText().toString();
                str_book = sp.getSelectedItem().toString();
                status = getstatus(str_book);

                if(status.equals("i")){
                    String entry = ""+str_user+","+str_book+","+"r"+"\n";
                    writerec(entry);
                    Toast.makeText(getApplicationContext(), "Returned", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No book to return on this userid", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void writerec(String data) {
        try {
            FileOutputStream FOS = openFileOutput("LibraryTrans.txt", MODE_APPEND);
            //String out = "" + name + "," + book + "," + status + "\n";
            FOS.write(data.getBytes());
            FOS.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getstatus(String book) {
        String status = "r";
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("LibraryTrans.txt")));
            String line;
            while ((line = br.readLine()) != null) {
                String arr[] = line.split(",");
                if (arr[1].equals(book)) {
                    status = arr[2];
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
