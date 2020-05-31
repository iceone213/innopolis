package innopolis.part1.lesson10;

import innopolis.part1.lesson2.task2.Logger;

import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Client
 *
 * @author Stanislav_Klevtsov
 */
public class MyChatClient {
    private DatagramSocket clientSocket;
    // private static final int MULTICAST_PORT = 11000;
    private static final int PORT = new Random().nextInt(100) + 12001; // Клиенты на разных портах
    private InetAddress ip;

    public MyChatClient() throws SocketException, UnknownHostException {
        clientSocket = new DatagramSocket(PORT);
        // clientSocket = new DatagramSocket(MULTICAST_PORT);
        clientSocket.setBroadcast(true);
        ip = InetAddress.getByName("localhost");
    }

    /**
     * Запускает нового клиента.
     */
    public static void main(String[] args) {

        MyChatClient myChatClient;
        try {
            myChatClient = new MyChatClient();
            myChatClient.start();
        } catch (SocketException | UnknownHostException e) {
            Logger.e("Не удается запустить клиент\n" + e);
        }
    }

    /**
     * Создает потоки для приема и получения сообщений.
     */
    public void start() {
        ClientSender sender = new ClientSender();
        ClientReceiver receiver = new ClientReceiver();
        sender.setReceiver(receiver); // Управляем обоими потоками, при отправке -quit завершаем
        sender.start();
        receiver.start();
    }

    /**
     * Класс - отправитель сообщений.
     */
    class ClientSender extends Thread {

        ClientReceiver receiver;

        public void setReceiver(ClientReceiver receiver) {
            this.receiver = receiver;
        }

        public void run() {
            Logger.p("Чтобы отправить личное сообщение введите \"-u пользователь сообщение\"\n" +
                    "Для выхода введите \"-quit\"");
            Logger.p("Выберите ваш логин:");
            while (true) {
                String clientMsg = new Scanner(System.in).nextLine();
                byte[] sendMsg = clientMsg.getBytes();
                DatagramPacket sendPacket;
                try {
                    sendPacket = new DatagramPacket
                            (sendMsg, 0, sendMsg.length, ip, MyChatServer.PORT);
                    clientSocket.send(sendPacket);
                } catch (IOException e) {
                    Logger.e("Не удается отправить пакет\n" + e);
                    clientSocket.close();
                    break;
                }
                if (clientMsg.equalsIgnoreCase(ChatCmd.QUIT.getCmd())) {
                    clientSocket.close();
                    receiver.stop();
                    break;
                }

            }

        }
    }

    /**
     * Класс - получатель сообщений.
     */
    class ClientReceiver extends Thread {

        public void run() {
            while (true) {

                byte[] receivedMsg = new byte[MyChatServer.BUFFER_SIZE];
                DatagramPacket receivedPacket = new DatagramPacket(receivedMsg, receivedMsg.length);
                try {
                    clientSocket.receive(receivedPacket);
                } catch (IOException e) {
                    Logger.e("Не удалось получить пакет\n" + e.getMessage());
                    e.printStackTrace();
                    clientSocket.close();
                    break;
                }
                String serverMsg = new String(receivedPacket.getData());
                Logger.p(serverMsg);
            }
        }
    }
}