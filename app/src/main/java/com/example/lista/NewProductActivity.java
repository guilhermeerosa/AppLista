package com.example.lista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lista.model.Product;

public class NewProductActivity extends AppCompatActivity {

    private final int RESULT_CODE_NEW_PRODUCT = 10;
    private final int RESULT_CODE_PRODUCT_EDITED = 11;
    private final int RESULT_CODE_DELETE_PRODUCT = 30;

    private boolean edit = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        setTitle("Cadastro de Produto");

        loadProduct();
    }

    private void loadProduct() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("productEdit") != null) {
            Product product = (Product) intent.getExtras().get("productEdit");
            EditText editTextNome = findViewById(R.id.editText_name);
            EditText editTextValor = findViewById(R.id.editText_valor);
            editTextNome.setText(product.getName());
            editTextValor.setText(String.valueOf(product.getValor()));
            edit = true;
            id = product.getId();
        }
    }

    public void onClickBack(View v) {
        finish();
    }

    public void onClickSave(View v) {
        EditText editTextName = findViewById(R.id.editText_name);
        EditText editTextValor = findViewById(R.id.editText_valor);

        String name = editTextName.getText().toString();
        Float valor = Float.parseFloat(editTextValor.getText().toString());

        Product product = new Product(id, name, valor);
        Intent intent= new Intent();
        if (edit){
            intent.putExtra("productEdited", product);
            setResult(RESULT_CODE_PRODUCT_EDITED, intent);
        }else {
            intent.putExtra("newProduct", product);
            setResult(RESULT_CODE_NEW_PRODUCT, intent);
        }

        finish();
    }

    public void onClickDelete(View v) {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("productDelete") != null) {
            Product product = (Product) intent.getExtras().getSerializable("productDelete");
            intent.putExtra("productDeleted", product);
            setResult(RESULT_CODE_DELETE_PRODUCT, intent);
            finish();
        } else {
            Toast.makeText(NewProductActivity.this, "Não foi possivel excluir", Toast.LENGTH_LONG).show();
        }
    }
}