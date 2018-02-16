package br.com.darisson.agenda;


import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebClient {
    public String post(String json){

        try {
            //URL url = new URL("http://www.caelum.com.br/mobile");// para realizar a conexao, cria o objeto url informando o endereco do servidor
            //HttpURLConnection connection = (HttpURLConnection) url.openConnection();//realiza a conexao com o servidor
            //connection.setRequestProperty("Content-type", "application/json");//dizendo a requisicao que estamos usando um json e o que a gente queria saber de resposta, se era json, xml ou outro
            //connection.setRequestProperty("Accept", "application/json");//o content é um tipo que estamos enviando, colocamos o acept para dizer que aceitamos um json

            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);//enviando dados

            // PrintStream output = new PrintStream(connection.getOutputStream());//colocando o corpo da requisicao
            // output.println(json);
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            connection.connect();//estabelecendo a conecxao

            //Scanner scanner = new Scanner(connection.getInputStream());//cria o scanner para ler a resposta do servidor
            //String resposta = scanner.next();//o next le do servidor, o servidor devolve a resposta
            //return resposta;//a gente devolve essa resposta la para o lista alunos activity

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();
            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
