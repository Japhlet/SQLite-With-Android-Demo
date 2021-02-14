package com.example.sqlitewithandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Private class variables
    EditText name, age;
    Switch isActive;
    Button btnViewAll, btnAdd, btnUpdate, btnDelete;
    ListView lvCustomerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reference to buttons and other controls in the layout
        name = (EditText) findViewById(R.id.etName);
        age = (EditText) findViewById(R.id.etAge);
        isActive = (Switch) findViewById(R.id.swIsActive);

        btnViewAll = (Button)findViewById(R.id.btnViewAll);
        btnAdd = (Button)findViewById(R.id.btnAddCustomer);
        btnUpdate = (Button)findViewById(R.id.btnUpdateCustomer);
        btnDelete = (Button)findViewById(R.id.btnDeleteCustomer);

        lvCustomerList = (ListView) findViewById(R.id.lvCustomerList);

        //Add Button click listeners
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());

                List<CustomerModel> allCustomers = dbh.getAllCustomers();

                Toast.makeText(getApplicationContext(), allCustomers.toString(), Toast.LENGTH_LONG).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel = null;
                try {
                    customerModel = new CustomerModel(1, name.getText().toString(), Integer.parseInt(age.getText().toString()), isActive.isChecked());
                    Toast.makeText(getApplicationContext(), customerModel.toString(), Toast.LENGTH_LONG).show();
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "There was an error creating the customer", Toast.LENGTH_LONG).show();
                    customerModel = new CustomerModel(-1, "Error", 0, false );
                }

                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
                boolean success = dbh.addOneRecord(customerModel);

                if(success) {
                    reload();
                    Toast.makeText(getApplicationContext(), "Successfully added customer record", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "There was an error adding customer record", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Update", Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void reload() {
        name.setText("");
        age.setText("");
        isActive.setChecked(false);
    }
}