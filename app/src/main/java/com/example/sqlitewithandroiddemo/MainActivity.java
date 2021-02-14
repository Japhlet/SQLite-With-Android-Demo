package com.example.sqlitewithandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    DatabaseHelper dbh;
    ArrayAdapter customerAdapter;

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

        dbh = new DatabaseHelper(getApplicationContext());

        showCustomerListView(dbh);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //Add Button click listeners
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());

                List<CustomerModel> allCustomers = dbh.getAllCustomers();

                //Create an ArrayAdapter
                customerAdapter = new ArrayAdapter<CustomerModel>(getApplicationContext(), android.R.layout.simple_list_item_1, allCustomers);
                lvCustomerList.setAdapter(customerAdapter);

                //Toast.makeText(getApplicationContext(), allCustomers.toString(), Toast.LENGTH_LONG).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel = null;
                String customerName = name.getText().toString();
                String customerAge = age.getText().toString();

                if((customerName == "") || (customerName == null) || (customerName.isEmpty())) {
                    name.setError("Customer name is required.");
                    return;
                }

                if((customerAge == "") || (customerAge == null) || (customerAge.isEmpty())) {
                    age.setError("Customer age is required.");
                    return;
                }

                try {
                    customerModel = new CustomerModel(1, name.getText().toString(), Integer.parseInt(age.getText().toString()), isActive.isChecked());
                    Toast.makeText(getApplicationContext(), customerModel.toString(), Toast.LENGTH_LONG).show();
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "There was an error creating the customer", Toast.LENGTH_LONG).show();
                    return;
                }

                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());
                boolean success = dbh.addOneRecord(customerModel);

                if(success) {
                    reload();
                    showCustomerListView(dbh);
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

        lvCustomerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dbh = new DatabaseHelper(getApplicationContext());
                dbh.deleteOneRecord(clickedCustomer);
                showCustomerListView(dbh);

                Toast.makeText(getApplicationContext(), "Deleted"+clickedCustomer.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void reload() {
        name.setText("");
        age.setText("");
        isActive.setChecked(false);
    }

    private void showCustomerListView(DatabaseHelper dbh2) {
        customerAdapter = new ArrayAdapter<CustomerModel>(getApplicationContext(), android.R.layout.simple_list_item_1, dbh2.getAllCustomers());
        lvCustomerList.setAdapter(customerAdapter);
    }
}