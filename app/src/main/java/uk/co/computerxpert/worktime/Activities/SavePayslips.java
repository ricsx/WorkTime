package uk.co.computerxpert.worktime.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;

import uk.co.computerxpert.worktime.R;


public class SavePayslips extends AppCompatActivity {
    int TAKE_PHOTO_CODE = 0;
    public String payslipName;
    EditText payslipNameBox;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_payslips);

        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        //noinspection ResultOfMethodCallIgnored
        newdir.mkdirs();

        payslipNameBox = findViewById(R.id.ed_payslipName);
        Button savePhoto = findViewById(R.id.btn_savePhoto);


        savePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                payslipName = payslipNameBox.getText().toString();
                String file = dir+payslipName+".jpg";
                File newfile = new File(file);
                try {
                    //noinspection ResultOfMethodCallIgnored
                    newfile.createNewFile();
                }
                catch (IOException ignored)
                {
                }

                Uri outputFileUri = Uri.fromFile(newfile);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
            }
        });
    }
}

