package com.example.grocerylist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GroceryAdapter.OnItemCheckedChangeListener {

    private RecyclerView mGroceryListRV;
    private EditText mGroceryEntryET;
    private GroceryAdapter mGroceryAdapter;
    private Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGroceryListRV = findViewById(R.id.rv_grocery_list);
        mGroceryEntryET = findViewById(R.id.et_item_entry);

        mGroceryListRV.setLayoutManager(new LinearLayoutManager(this));
        mGroceryListRV.setHasFixedSize(true);

        mGroceryAdapter = new GroceryAdapter(this);
        mGroceryListRV.setAdapter(mGroceryAdapter);

        mGroceryListRV.setItemAnimator(new DefaultItemAnimator());

        mToast = null;

        Button addTodoButton = findViewById(R.id.btn_add_item);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoText = mGroceryEntryET.getText().toString();
                if (!TextUtils.isEmpty(todoText)) {
                    mGroceryListRV.scrollToPosition(0);
                    mGroceryAdapter.addItem(todoText);
                    mGroceryEntryET.setText("");
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ((GroceryAdapter.GroceryViewHolder)viewHolder).removeFromList();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGroceryListRV);
    }

    @Override
    public void onItemCheckedChanged(String item, boolean b){
        if (mToast != null){
            mToast.cancel();
        }
        String completedState = b ? "COMPLETED" : "MARKED INCOMPLETE";
        String toastText = completedState + ": " + item;
        mToast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        mToast.show();
    }
}
