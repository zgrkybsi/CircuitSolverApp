package com.cpen321.circuitsolver.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpen321.circuitsolver.R;
import com.cpen321.circuitsolver.model.components.CircuitElm;
import com.cpen321.circuitsolver.util.BaseActivity;
import com.cpen321.circuitsolver.util.CircuitElmsNotSetException;
import com.cpen321.circuitsolver.util.CircuitProject;
import com.cpen321.circuitsolver.util.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class AnalysisActivity extends BaseActivity {

    CircuitDisplay circuitDisplay;
    CircuitProject circuitProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        String dataLocation = null;

        if (extras != null) {
            dataLocation = (String) extras.get(Constants.CIRCUIT_PROJECT_FOLDER);
        }

        this.loadCircuit(new File(dataLocation));
    }

    public void loadCircuit(File circuitDirectory) {
        this.circuitProject = new CircuitProject(circuitDirectory);
        this.circuitProject.print();

        ImageView outputImageView = (ImageView) findViewById(R.id.output_image);
        outputImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        try {
            outputImageView.setImageBitmap(this.circuitProject.getProcessedImage());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        TextView outputTextView = (TextView) findViewById(R.id.output_text);

        try {
            outputTextView.setText(this.circuitProject.getCircuitText());
        }
        catch(IOException e){
            dialogAlert("Sorry, there was an error processing the circuit components.");
        }

    }

    private void dialogAlert(String msg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(msg);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent backToHome = new Intent(this, HomeActivity.class);
        startActivity(backToHome);
        super.onBackPressed();
    }

}
