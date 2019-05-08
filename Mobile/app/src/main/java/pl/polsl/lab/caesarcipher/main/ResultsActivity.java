package pl.polsl.lab.caesarcipher.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pl.polsl.lab.caesarcipher.R;
import pl.polsl.lab.caesarcipher.model.*;


public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        String message = getIntent().getStringExtra("message");
        int key = Integer.parseInt(getIntent().getStringExtra("key"));
        String mode = getIntent().getStringExtra("mode");
        TextView introText = (TextView)findViewById(R.id.textViewIntro);
        TextView messageText = (TextView)findViewById(R.id.textViewMessage);
        TextView keyText = (TextView)findViewById(R.id.textViewKey);
        TextView resultText = (TextView)findViewById(R.id.textViewResult);
        CaesarModel model = new CaesarModel();
        if (mode.equals("Encode")){
            try{
                String result = model.encode(message,key);
                introText.setText("Encoding successful");
                messageText.setText("Message: "+message);
                keyText.setText("Key: "+key);
                resultText.setText("Encoded message: "+result);
            }catch(InvalidInputException e){
                introText.setText("Input error!");
                messageText.setText("");
                keyText.setText("");
                resultText.setText("");
            }

        }else if (mode.equals("Decode")){
            try{
                String result = model.decode(message,key);
                introText.setText("Decoding successful");
                messageText.setText("Message: "+message);
                keyText.setText("Key: "+key);
                resultText.setText("Decoded message: "+result);
            }catch(InvalidInputException e){
                introText.setText("Input error!");
                messageText.setText("");
                keyText.setText("");
                resultText.setText("");
            }
        }
    }
}
