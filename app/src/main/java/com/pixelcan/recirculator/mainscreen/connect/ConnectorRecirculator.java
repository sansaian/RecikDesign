package com.pixelcan.recirculator.mainscreen.connect;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Class for connect and interaction with Recirculator
 * @author saian
 */
public class ConnectorRecirculator  extends AsyncTask<Void, Void, Void> {

/*
    @Override
    protected String doInBackground(Void... params) {
        int serverPort = 6666; // здесь обязательно нужно указать порт к которому привязывается сервер.
        String address = "192.168.1.38"; // это IP-адрес компьютера, где исполняется наша серверная программа.
        // Здесь указан адрес того самого компьютера где будет исполняться и клиент.

        try {
            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            Log.d("ConnectorRecirculator","Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            Log.d("ConnectorRecirculator","Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            // Создаем поток для чтения с клавиатуры.
           // BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            Log.d("ConnectorRecirculator","Type in something and press enter. Will send it to the server and tell ya what it thinks.");

            while (true) {
                line = "test test test"; // ждем пока пользователь введет что-то и нажмет кнопку Enter.
                Log.d("ConnectorRecirculator","Sending this line to the server...");
                out.writeUTF(line); // отсылаем введенную строку текста серверу.
                out.flush(); // заставляем поток закончить передачу данных.
                line = in.readUTF(); // ждем пока сервер отошлет строку текста.
                Log.d("ConnectorRecirculator","The server was very polite. It sent me this : " + line);
                Log.d("ConnectorRecirculator","Looks like the server is pleased with us. Go ahead and enter more lines.");
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }*/



    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    String dstAddress;
    int dstPort;
    String response = "";

    public ConnectorRecirculator(){
        dstAddress = "192.168.1.38";
        dstPort = 6666;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Log.d("ConnectorRecirculator","start???");
        Socket socket = null;

        try {
            Log.d("ConnectorRecirculator","Any of you heard of a socket with IP address ?");
            socket = new Socket(dstAddress, dstPort);
            Log.d("ConnectorRecirculator","Create socket");
            ByteArrayOutputStream byteArrayOutputStream =
                    new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

				/*
				 * notice:
				 * inputStream.read() will block if no data return
				 */
            while ((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
       // textResponse.setText(response);
        super.onPostExecute(result);
    }

}


