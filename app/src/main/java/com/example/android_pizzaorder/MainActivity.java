package com.example.android_pizzaorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner pizza;
    RadioButton small, medium,large;
    TextView price, topPrice, quantity, totPrice;
    CheckBox cheese,olive,garlic,corn;
    SeekBar sbQty;
    Button order, clear;

    String pizzaList[] = {"7-Cheesy", "Margherita", "Garlic Pizza", "Yummi Pizza"};
    double priceList[] = {5.99, 3.99, 2.99, 4.99};
    public static double originalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pizza = findViewById(R.id.spPizza);
        small = findViewById(R.id.rbSmall);
        medium = findViewById(R.id.rbMedium);
        large = findViewById(R.id.rbLarge);
        price = findViewById(R.id.tvPrice);
        topPrice = findViewById(R.id.tvTopPrice);
        quantity = findViewById(R.id.tvQty);
        totPrice = findViewById(R.id.tvTotPrice);
        cheese = findViewById(R.id.cbCheese);
        olive = findViewById(R.id.cbOlive);
        garlic = findViewById(R.id.cbGarlic);
        corn = findViewById(R.id.cbCorn);
        sbQty = findViewById(R.id.sbQty);
        order = findViewById(R.id.btnOrder);
        clear = findViewById(R.id.btnClear);

        // FOR SPINNER
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pizzaList);
        pizza.setAdapter(aa);
        pizza.setOnItemSelectedListener(this);

        // FOR RADIO BUTTONS
        small.setOnClickListener(new RadioButtonEvents());
        medium.setOnClickListener(new RadioButtonEvents());
        large.setOnClickListener(new RadioButtonEvents());

        // FOR CHECKBOXES
        cheese.setOnCheckedChangeListener(new CheckBoxEvents());
        olive.setOnCheckedChangeListener(new CheckBoxEvents());
        corn.setOnCheckedChangeListener(new CheckBoxEvents());
        garlic.setOnCheckedChangeListener(new CheckBoxEvents());
        topPrice.setText("0");
        quantity.setText("1");
        totPrice.setText("0");

        // FOR SEEKBAR
        sbQty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                quantity.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // FOR BUTTONS
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double pizzaPrice = Double.parseDouble(price.getText().toString());
                double toppingPrice = Double.parseDouble(topPrice.getText().toString());
                int qty = Integer.parseInt(quantity.getText().toString());
                double totalPrice = (pizzaPrice+toppingPrice)*qty;
                totPrice.setText(String.format("%.2f",totalPrice));
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pizza.setSelection(0);
                small.setChecked(true);
                cheese.setChecked(false);
                olive.setChecked(false);
                corn.setChecked(false);
                garlic.setChecked(false);
                topPrice.setText("0");
                sbQty.setProgress(1);
                quantity.setText("1");
                totPrice.setText("0");

            }
        });

    }

    // FOR SPINNER
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        price.setText(String.valueOf(priceList[position]));
        originalPrice = priceList[position];

        // To select the small size pizza on click of any pizza as we're getting that price on selection of pizza
        small.setChecked(true);
    }

    // FOR SPINNER
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        price.setText(String.valueOf(priceList[0]));
    }

    // FOR RADIO BUTTONS
    private class RadioButtonEvents implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            double pizzaPrice = originalPrice;
            switch(v.getId()) {
                case R.id.rbSmall:
                    pizzaPrice = originalPrice;
                    break;
                case R.id.rbMedium:
                    pizzaPrice = originalPrice * 1.5;
                    break;
                case R.id.rbLarge:
                    pizzaPrice = originalPrice * 2.0;
            }
            price.setText(String.format("%.2f",pizzaPrice));
        }
    }

    // FOR CHECKBOXES
    private class CheckBoxEvents implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            double toppingPrice = 0;
            if(cheese.isChecked()){
                toppingPrice += 1.0;
            }
            if(olive.isChecked()){
                toppingPrice += 2.0;
            }
            if(corn.isChecked()){
                toppingPrice += 3.0;
            }if(garlic.isChecked()){
                toppingPrice += 4.0;
            }
            topPrice.setText(String.format("%.2f", toppingPrice));
        }
    }
}