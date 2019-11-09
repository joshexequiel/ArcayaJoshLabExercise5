package com.arcaya.arcaya_josh_labexercise5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] compNames, compCountries, compCEOs, compIndustries, compDescriptions;
    int[] logos = {R.drawable.icbc, R.drawable.jpmorgan, R.drawable.chinaconstruction, R.drawable.agrichina,
            R.drawable.bankofamerica, R.drawable.apple, R.drawable.pingan, R.drawable.bankofchina, R.drawable.shell,
            R.drawable.wellsfargo, R.drawable.exxon, R.drawable.att, R.drawable.samsung, R.drawable.citi};

    ArrayList<TopCompanies> companies = new ArrayList<>();

    ListView listCompanies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TOP GLOBAL COMPANIES");

        compNames = getResources().getStringArray(R.array.compName);
        compCountries = getResources().getStringArray(R.array.compCountry);
        compCEOs = getResources().getStringArray(R.array.compCEO);
        compIndustries = getResources().getStringArray(R.array.compIndustry);
        compDescriptions = getResources().getStringArray(R.array.compDesc);

        for (int i = 0; i < compNames.length; i++) {
            companies.add(i, new TopCompanies(logos[i], compNames[i], compCountries[i], compCEOs[i], compIndustries[i], compDescriptions[i]));
        }

        CompanyAdapter adapter = new CompanyAdapter(this, R.layout.company, companies);

        listCompanies = findViewById(R.id.lvCompanies);
        listCompanies.setAdapter(adapter);
        listCompanies.setOnItemClickListener(this);

    }

    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long id) {
        final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "company.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            String choice = "Name: " + compNames[i] + "\nCEO: " + compCEOs[i] + "\nCountry: " + compCountries[i] + "\nIndustry: " + compIndustries[i];
            fos.write(choice.getBytes());
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setIcon(logos[i]);
            dialog.setTitle(compNames[i]);
            dialog.setMessage(compDescriptions[i]);
            dialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                        try {
                            FileInputStream fis;
                            fis = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/company.txt"));
                            int i;
                            String contents = "";
                            while ((i = fis.read()) != -1) {
                                contents += Character.toString((char) i);
                            }
                            fis.close();
                            Toast.makeText(MainActivity.this, contents, Toast.LENGTH_LONG).show();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            });
            dialog.create().show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
