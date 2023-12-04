package br.pro.adalto.appbuscarcep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView tvCep;
    private EditText etCep;
    private Button btnConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCep = findViewById(R.id.tvCep);
        etCep = findViewById(R.id.etCep);
        btnConsultar = findViewById(R.id.btnConsultar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar();
            }
        });
    }

    private void consultar() {
        String cep = etCep.getText().toString();
        if (cep.isEmpty()) {
            Toast.makeText(this, "Preencha o CEP", Toast.LENGTH_SHORT).show();
        } else {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            RequestQueue queue = Volley.newRequestQueue(this);
            tvCep.setText("Carregando...");

            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String logradouro = response.getString("logradouro");
                                String complemento = response.getString("complemento");
                                String bairro = response.getString("bairro");

                                String texto = logradouro + "\n" + complemento + "\n" + bairro;
                                tvCep.setText(texto);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Erro ao consultar o CEP", Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(jsonRequest);
        }
    }
}
