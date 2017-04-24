/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planilha;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author usuario1
 */
public class Planilha {
     
    private static final String APPLICATION_NAME = "Cliente Email";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public static GoogleCredential authorize() throws IOException {
        InputStream resourceAsStream = Planilha.class.getClassLoader().getResourceAsStream("auth/ClienteEmail-64b892b033d1.json");
        return GoogleCredential.fromStream(resourceAsStream).createScoped(SCOPES);
    }

    
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    
    public void adicionarDados(String[] args) throws IOException {
      
        Sheets service = getSheetsService();
        String spreadsheetId = "1UGdPha1mfqkIuyEUtCm9t542Xpbfba4S2UM79X0-O7A";
        String range = "A:E";
        List<Object> data1 = new ArrayList<>();
        for(String s : args){
            data1.add(s);
        }
        List<List<Object>> data = new ArrayList<>();
        data.add (data1);
        
        ValueRange valueRange =new ValueRange();
        valueRange.setValues(data);
        service.spreadsheets().values().
        append(spreadsheetId, range, valueRange)
                .setValueInputOption("RAW")
                .execute();
        
        /*List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("Nenhum valor encontrado!");
        } else {
          System.out.println("A, B");
          values.forEach((row) -> {
              // Print columns A and E, which correspond to indices 0 and 4.
              System.out.printf("%s,%s\n", row.get(0),row.get(1));
            });
        }*/
    }
}
