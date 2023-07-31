package br.com.elgin.testnapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import br.com.elgin.e1_egr.E1_EGR;

public class MainActivity extends AppCompatActivity {

    E1_EGR e1;
    String vendas = "{\"rows\": [{\"default_date\": \"2022-05-01 14:23:33\", \"default_discount_amount\": 0.0, \"default_invoice_number\": \"29393\", \"default_order_id\": \"325\", \"default_order_number\": \"00001\", \"default_payment_id\": \"432\", \"default_payment_installments\": \"1\", \"default_payment_type\": \"Boleto / Cartão de Crédito\", \"default_salesperson_code\": \"X381A\", \"default_salesperson_id\": \"123\", \"default_shipping_amount\": 0.0, \"default_shipping_type\": \"SEDEX\", \"default_status\": \"Faturado\", \"default_subtotal_amount\": 10000.0, \"default_total_amount\": 10000.0, \"items\": [{\"default_item_discount_amount\": 0, \"default_item_ean\": \"7898960921267\", \"default_item_id\": \"432\", \"default_item_list_price_amount\": 10000.0, \"default_item_name\": \"Camisa Preta\", \"default_item_price_amount\": 10000.0, \"default_item_quantity\": 1, \"default_item_sku\": \"A1202\", \"default_item_status\": \"Faturado\"}]}], \"ts\": \"20230516174400\"}";
    String catalogo = "{\"rows\": [{\"default_brand\": \"Balmain\", \"default_category\": \"Mulher > Roupa > Camiseta\", \"default_description\": \"Camisa preta com manga curta\", \"default_ean\": \"7898960921267\", \"default_height\": \"150\", \"default_list_price\": 45322.0, \"default_manufacturer\": \"Balmain LTDA\", \"default_price\": 39999.0, \"default_price_id\": \"625\", \"default_product_id\": \"100\", \"default_product_name\": \"Camisa Preta\", \"default_product_sku\": \"XXX\", \"default_sku\": \"XS001-M\", \"default_sku_id\": \"325\", \"default_status\": \"Faturado\", \"default_stock_id\": \"525\", \"default_stock_quantity\": 10, \"default_weight_net\": \"0,5\", \"default_width\": \"150\"}], \"ts\": \"20230516174400\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            e1 = E1_EGR.getInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result;

        Toast.makeText(getApplicationContext(), "Teste", Toast.LENGTH_LONG).show();

        //SetupConfigLogin -------------------------------------------------------------------------
        result = e1.SetupConfigLogin("", "");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        //VerificarConfigLogin ---------------------------------------------------------------------
        result =  String.valueOf(e1.VerificarConfigLogin());
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        //EnviarDadosVenda -------------------------------------------------------------------------
        result = e1.EnviarDadosVenda(vendas);
        Toast.makeText(getApplicationContext(), "Enviar Dados Venda: " + result, Toast.LENGTH_LONG).show();

        //EnviarDadosCatalogo ----------------------------------------------------------------------
        result = e1.EnviarDadosCatalogo(catalogo);
        Toast.makeText(getApplicationContext(), "Enviar Dados Catalogo: " + result, Toast.LENGTH_LONG).show();
    }
}
