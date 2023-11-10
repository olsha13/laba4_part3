package client;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;

class ClientTCP extends Frame implements ActionListener, WindowListener {
    TextField tf, tf1, tf2; 
    TextArea ta;
    Label la, la1, la2, la3, la4;
    Socket sock = null;
    InputStream is = null;
    OutputStream os = null;
    public static void main(String args[]) {
        ClientTCP c = new ClientTCP();
        c.GUI();
    }
    private void GUI() {
        // super("Клиент");
        setTitle("КЛИЕНТ");
        tf = new TextField("127.0.0.1");//ip adress клиента
        tf1 = new TextField("1024");// port клиента
        tf2 = new TextField();
        ta = new TextArea();
        la = new Label("IP ADRESS");
        la1 = new Label("port");
        la2 = new Label("sending date");
        la3 = new Label("result ");
        la4 = new Label(" ");
        Button btn = new Button("connect ");
        Button btn1 = new Button("send ");
        tf.setBounds(200, 50, 70, 25);
        tf1.setBounds(330, 50, 70, 25);
        tf2.setBounds(150, 200, 100, 25);
        ta.setBounds(150, 300, 150, 50);
        btn.setBounds(50, 50, 70, 25);
        btn1.setBounds(50, 200, 70, 25);
        la.setBounds(130, 50, 150, 25);
        la1.setBounds(280, 50, 150, 25);
        la2.setBounds(150, 150, 150, 25);
        la3.setBounds(160, 250, 150, 25);
        la4.setBounds(600, 10, 150, 25);
        add(tf);
        add(tf1);
        add(tf2);
        add(btn);
        add(btn1);
        add(ta);
        add(la);
        add(la1);
        add(la2);
        add(la3);
        add(la4);
        setSize(600, 600);
        setVisible(true);
        addWindowListener(this);
        btn.addActionListener(al);
        btn1.addActionListener(this);
        tf2.getText();
    }
    public void windowClosing(WindowEvent we) {
        if (sock != null && !sock.isClosed()) { // если сокет не пустой и сокет открыт 
            try {
                sock.close(); // сокет  закрывается
            } catch (IOException e) {
            }
        }
        this.dispose();
    }
    public void windowActivated(WindowEvent we) {}   ;
    public void windowClosed(WindowEvent we) {};
    public void windowDeactivated(WindowEvent we) {};
    public void windowDeiconified(WindowEvent we) {}   ;
    public void windowIconified(WindowEvent we) {};
    public void windowOpened(WindowEvent we) { } ;
    public void actionPerformed(ActionEvent e) {
        if (sock == null) {
            return;
        }
        try {
            is = sock.getInputStream(); // входной поток для чтения данных
            os = sock.getOutputStream();// выходной поток для записи данных
            String numbers = new String(); //перменная,в которую записываются введенные числа
            numbers = tf2.getText();
            
            os.write(numbers.getBytes()); // отправляем введенные данные. Тип string переводим в byte 
            
            byte[] bytes = new byte[1024];
            is.read(bytes); //получаем назад информацию,которую послал сервер
            String str = new String(bytes, "UTF-8"); // переводим тип byte в String
            ta.append(str + "\n");

            ///String[] n = str.split(" "); // разбиваем строку на подстроки пробелами 
            //for (int i = 0; i < n.length - 1; i++) {
            //    ta.append(n[i] + "\n"); // в text area записываем полученные данные
            //}
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                os.close();//закрытие выходного потока
                is.close();//закрытие входного потока
                sock.close();//закрытие сокета, выделенного для работы с сервером
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void actionPerformed2(ActionEvent e) {}
        ActionListener al = new ActionListener() { //событие на нажатие кнопки 
        @Override
        public void actionPerformed(ActionEvent arg0) {
           try {
                sock = new Socket(InetAddress.getByName(tf.getText()), Integer.parseInt(tf1.getText()));
                //создается сокет по ip адрессу и порту
            } catch (NumberFormatException e) {
            } catch (UnknownHostException e) {
            } catch (IOException e) {
            }
        } 
    };
}

