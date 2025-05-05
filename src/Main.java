import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Variables iniciales
        Scanner teclado = new Scanner(System.in);
        int opcion_seleccionada;
        int salir = 1;
        var direccion_general = "https://v6.exchangerate-api.com/v6/93936407eb469bbf1639dffb/latest/";
        String moneda_base="";
        String moneda_conversion="";
        Double valor_convertir;


        //Loop de menú
        while (salir!=0){
            //Listar opciones de menu en un loop con opción salir
            System.out.println("**********************************************");
            System.out.println("1) MXN -> USD");
            System.out.println("2) USD -> EUR");
            System.out.println("3) EUR -> CAD");
            System.out.println("4) CAD -> JPY");
            System.out.println("5) JPY -> BZD");
            System.out.println("6) BZD -> MXN");
            System.out.println("7) SALIR");
            System.out.println("**********************************************");
            System.out.println("SELECCIONA UNA OPCIÓN DE CONVERSIÓN DEL MENÚ");

            opcion_seleccionada = teclado.nextInt();
            System.out.println("INGRESE EL VALOR A CONVERTIR");
            valor_convertir = teclado.nextDouble();

            switch (opcion_seleccionada){
                //MXN
                case 1:
                    moneda_base = "MXN";
                    moneda_conversion = "USD";
                break;

                //USD
                case 2:
                    moneda_base = "USD";
                    moneda_conversion = "EUR";
                break;

                //EUR
                case 3:
                    moneda_base = "EUR";
                    moneda_conversion = "CAD";
                break;

               //CAD
                case 4:
                    moneda_base = "CAD";
                    moneda_conversion = "JPY";
                break;

                //JPY
                case 5:
                    moneda_base = "JPY";
                    moneda_conversion = "BZD";
                break;

                //BZD
                case 6:
                    moneda_base = "BZD";
                    moneda_conversion = "MXN";
                break;

                case 7:
                   salir = 0;
                break;

                default:
                    salir = 0;
                break;

            }

            //CONSULTA A LA API DE EXCHANGE
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(direccion_general+moneda_base)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            Gson gson = new Gson();
            Exchange respuesta = gson.fromJson(json, Exchange.class);

            //OBTENCION DE VALORES SELECCIONADOS EXTRAIDOS DE LA API
            Double valor_moneda_base = respuesta.conversion_rates.get(moneda_base);
            Double valor_moneda_conversion = respuesta.conversion_rates.get(moneda_conversion);
            double resultado = valor_convertir * valor_moneda_conversion;
            System.out.println(moneda_base+" base: "+valor_moneda_base+", "+moneda_conversion+" conversion: "+valor_moneda_conversion);
            System.out.println("Operación: "+valor_convertir+" "+moneda_base+" a "+moneda_conversion+" equivale a "+resultado+" "+moneda_conversion);
        }




    }
}