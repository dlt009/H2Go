package com.example.h2go.h2go;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 4/8/2017.
 */

    public class FilterActivity extends ListActivity implements AdapterView.OnItemSelectedListener {

        private Spinner spinnerRadius, spinnerRating;
        private ListView TypesList;
        private int numIndeTraits = 2;
        private int numTypes = MainActivity.Types.length;

        private static String TAG = "FilterActivity";

        private String[] filter;
        private List<String> types;

    public ArrayList<String> filters = new ArrayList<String>();

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.filter_layout);

            // Spinner element
            spinnerRadius = (Spinner) findViewById(R.id.spinner1);
            spinnerRadius.setOnItemSelectedListener(this);

            spinnerRating = (Spinner) findViewById(R.id.spinner2);
            spinnerRating.setOnItemSelectedListener(this);

            //takes list of specialized tags from
            Integer[] Radius = MainActivity.Radius;
            String[] Types = MainActivity.Types;

            // Spinner Drop down elements
            List<Integer> radii = new ArrayList<>();
            for(int i=0; i<Radius.length; i++){
                radii.add(Radius[i]);
            }

            List<String> ratings = new ArrayList<>();
            for(int i=0; i<5; i++){
                ratings.add(i+"+");
            }

            types = new ArrayList<>();
            for(int i=0; i<Types.length; i++){
                types.add(Types[i]);
            }

            //List of radius, rating, price, then Types
            filter = new String[numIndeTraits+numTypes];

            // Creating adapter for spinner
            ArrayAdapter<Integer> dataAdapter1 = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, radii);
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, ratings);

            // Drop down layout style - list view with radio button
            dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerRadius.setAdapter(dataAdapter1);
            spinnerRadius.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    filter[0] = Integer.toString((position+1));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            //attaches data adapter to spinner
            spinnerRating.setAdapter(dataAdapter2);
            spinnerRating.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    filter[1]= Double.toString(((double)(position)));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            //initializes the list for the non-mutually exclusive tags
            TypesList = getListView();
            TypesList.setChoiceMode(TypesList.CHOICE_MODE_MULTIPLE);
            TypesList.setTextFilterEnabled(true);
            setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked,
                    types));
            TypesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CheckedTextView item = (CheckedTextView) view;
                    filter[position] = types.get(position-1);
                }
            });

            //create button for applying the filter to our map through a BroadcastReceiver
            Button apply = (Button)findViewById(R.id.button_apply);
            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.filtered = filter;
                    Intent intent = new Intent("filter_done");
                    sendBroadcast(intent);
                    finish();
                }
            });
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            CheckedTextView item = (CheckedTextView) view;
            filter[position] = types.get(position-1);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }


    }

