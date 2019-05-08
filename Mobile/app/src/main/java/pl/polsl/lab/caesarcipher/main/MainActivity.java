package pl.polsl.lab.caesarcipher.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.polsl.lab.caesarcipher.R;
import pl.polsl.lab.caesarcipher.model.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnEncode = (Button) findViewById(R.id.buttonEncode);
        Button btnDecode = (Button) findViewById(R.id.buttonDecode);

        btnEncode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageField=(EditText)findViewById(R.id.editTextMessage);
                EditText keyField=(EditText)findViewById(R.id.editTextKey);
                if (messageField.getText().toString().isEmpty() || keyField.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Zle dane",Toast.LENGTH_SHORT).show();
                }else {
                    String message = messageField.getText().toString();
                    int key = Integer.parseInt(keyField.getText().toString());
                    Intent i = new Intent(MainActivity.this, ResultsActivity.class);
                    i.putExtra("mode", "Encode");
                    i.putExtra("message",message);
                    i.putExtra("key",Integer.toString(key));
                    startActivity(i);
                }
            }
        });
        btnDecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageField=(EditText)findViewById(R.id.editTextMessage);
                EditText keyField=(EditText)findViewById(R.id.editTextKey);
                if (messageField.getText().toString().isEmpty() || keyField.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Zle dane",Toast.LENGTH_SHORT).show();
                }else {
                    String message = messageField.getText().toString();
                    int key = Integer.parseInt(keyField.getText().toString());
                    Intent i = new Intent(MainActivity.this, ResultsActivity.class);
                    i.putExtra("mode", "Decode");
                    i.putExtra("message",message);
                    i.putExtra("key",Integer.toString(key));
                    startActivity(i);
                }
            }
        });
    }
}
