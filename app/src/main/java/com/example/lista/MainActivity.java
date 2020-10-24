package com.example.lista;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.lista.model.Product;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_NEW_PRODUCT = 1;
    private final int RESULT_CODE_NEW_PRODUCT = 10;
    private final int REQUEST_CODE_EDIT_PRODUCT = 2;
    private final int RESULT_CODE_PRODUCT_EDITED = 11;
    private final int RESULT_CODE_DELETE_PRODUCT = 30;

    private ListView listViewProducts;
    private ArrayAdapter<Product> adapterProducts;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Lista de Produtos");

        listViewProducts = findViewById(R.id.listview_products);
        ArrayList<Product> products = new ArrayList<Product>();

        adapterProducts = new ArrayAdapter<Product>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                products);
        listViewProducts.setAdapter(adapterProducts);

        defineOnClickListenerListView();
    }

    private void defineOnClickListenerListView(){
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product productSelected = adapterProducts.getItem(position);
                Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
                intent.putExtra("productEdit", productSelected);
                intent.putExtra("productDelete", productSelected);
                startActivityForResult(intent, REQUEST_CODE_EDIT_PRODUCT);
            }
        });
    }

    public void onClickNewProduct(View v) {
        Intent intent = new Intent(MainActivity.this, NewProductActivity.class);
        startActivityForResult(intent, REQUEST_CODE_NEW_PRODUCT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_NEW_PRODUCT && resultCode == RESULT_CODE_NEW_PRODUCT){
            Product product = (Product) data.getExtras().getSerializable("newProduct");
            product.setId(++id);
            this.adapterProducts.add(product);
        } else if (requestCode == REQUEST_CODE_EDIT_PRODUCT && resultCode == RESULT_CODE_PRODUCT_EDITED){
            Product productEdited = (Product) data.getExtras().getSerializable("productEdited");
            Toast.makeText(MainActivity.this, "Produto Editado", Toast.LENGTH_LONG).show();
            for (int i = 0; i < adapterProducts.getCount(); i++) {
                Product product = adapterProducts.getItem(i);
                if (product.getId() == productEdited.getId()){
                    adapterProducts.remove(product);
                    adapterProducts.insert(productEdited, i);
                    break;
                }
            }
        } else if (requestCode == REQUEST_CODE_EDIT_PRODUCT && resultCode == RESULT_CODE_DELETE_PRODUCT) {
            Product productDeleted = (Product) data.getExtras().getSerializable("productDeleted");
            Toast.makeText(MainActivity.this, "Produto Excluído", Toast.LENGTH_LONG).show();
            for(int i = 0; i < adapterProducts.getCount(); i++) {
                Product product = adapterProducts.getItem(i);
                if (product.getId() == productDeleted.getId()) {
                    adapterProducts.remove(product);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}