package com.example.cris.mytabs;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Image_BodyPart extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    int galleryPhotoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__body_part);

        // Get the Intent that started this activity and extract the int
        Intent intent = getIntent();
        galleryPhotoId=intent.getIntExtra("photo id",-1);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent();
        EditText editText = (EditText) findViewById(R.id.bodyDescription);
        String message=editText.getText().toString();
        intent.putExtra("mole_place",message);
        intent.putExtra("photo_id",galleryPhotoId);
        setResult(Activity.RESULT_OK, intent);
        finish();
        //startActivity(intent);

        // Do something in response to button
    }

}
