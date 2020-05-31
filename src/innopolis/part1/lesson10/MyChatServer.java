package innopolis.part1.lesson10;

import innopolis.part1.lesson2.task2.Logger;
import javafx.util.Pair;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Server
 *
 * @author Stanislav_Klevtsov
 */
public class MyChatServer {

    public final static int PORT = 12000;
    public final static int BUFFER_SIZE = 5000;

    private DatagramSocket serverSocket;
    private Map<Pair<InetAddress, Integer>, String> clientMap = new ConcurrentHashMap<>();


    public MyChatServer() throws SocketException, UnknownHostException {
        serverSocket = new DatagramSocket(PORT, InetAddress.getByName("localhost"));
        serverSocket.setBroadcast(true);
    }

    /**
     * Запускает сервер
     */
    public static void main(String[] args) {
        MyChatServer server = null;

        try {
            server = new MyChatServer();
            server.start();
        } catch (SocketException | UnknownHostException e) {
            Logger.e("Не удается запустить сервер");
            e.printStackTrace();
        } catch (IOException e) {
            Logger.e("Ошибка пересылки пакета\n" + e);
            e.printStackTrace();
        } finally {
            if (server != null)
                server.serverSocket.close();
        }

    }

    /**
     * Метод обеспечивает получение сообщений от клиентов.
     * Если клиент отправил первое сообщение, сервер считывает это сообщение как логин и сохраняет в clientMap
     * Последущие сообщения от клиента будут подписываться его именем
     */
    public void start() throws IOException {

        while (true) {
            byte[] receiveMsg = new byte[1000];
            DatagramPacket in = new DatagramPacket(receiveMsg, receiveMsg.length);

            serverSocket.receive(in);

            Pair ipPort = new Pair(in.getAddress(), in.getPort());

            if (!clientMap.containsKey(ipPort)) {
                storeNewClient(in, ipPort);
            } else {
                prepareMsg(in, ipPort);
            }

        }
    }

    /**
     * Добавляет нового клиента в clientMap.
     * Ключом выступает пара <ip, port> клиента, значением введенный логин.
     * Перед сохранением проверяется, не занят ли запрошенный логин, если занят - предлагается использовать другой.
     * После сохранения сервер оповещает всех пользователей (broadcast) о подключении нового клиента.
     *
     * @param receivedPacket Полученный фрейм
     * @param ipPort         <ip, port> отправителя
     * @throws IOException
     */
    private void storeNewClient(DatagramPacket receivedPacket, Pair<InetAddress, Integer> ipPort) throws IOException {
        String clientLogin = new String(receivedPacket.getData()).trim();
        if (clientMap.containsKey(clientLogin)) {
            byte[] sendMsg = "Логин занят, попробуйте другой".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, ipPort.getKey(), ipPort.getValue());
            serverSocket.send(sendPacket);
        } else {
            clientMap.put(ipPort, clientLogin);
            String greeting = "Подключен новый пользователь: " + clientLogin;
            broadcast(greeting);
        }
    }

    /**
     * Метод анализирует сообщение и выбирает дальнейший режим отправки.
     *
     * @param receivedPacket Полученный фрейм
     * @param ipPort        <ip, port> отправителя
     * @throws IOException
     */
    private void prepareMsg(DatagramPacket receivedPacket, Pair<InetAddress, Integer> ipPort)
            throws IOException {
        String clientName = clientMap.get(ipPort);
        String msg = new String(receivedPacket.getData()).trim();

        if (msg.equals(ChatCmd.UNICAST.getCmd())) { // Cписок доступных адресатов
            sendListOfUsers(ipPort);

        } else if (msg.startsWith(ChatCmd.UNICAST.getCmd())) { //клиент запрашивает unicast конкретному юзеру
            String[] words = msg.split("\\s");
            String unimsg = clientName + ": " + msg.substring(msg.indexOf(words[1]) + words[1].length());
            try {
                String name = words[1];
                Pair<InetAddress, Integer> userData;
                for (Map.Entry<Pair<InetAddress, Integer>, String> client : clientMap.entrySet()) {
                    if (client.getValue().equals(name)) {
                        userData = client.getKey();
                        unicast(unimsg, userData);
                        break;
                    }
                }
            } catch (Exception e) {
                unicast("Неверный запрос", ipPort);
            }

        } else if (msg.equalsIgnoreCase(ChatCmd.QUIT.getCmd())) { // Пользователь вышел из чата
            String exitmsg = clientName + " покинул чат";
            broadcast(exitmsg); // Оповещаем других пользователей
        } else {
            String msgWithName = clientName + ": " + msg; // Широковещательная рассылка
            broadcast(msgWithName);
        }
    }

    /**
     * Формирует список доступных адресатов и отправляет на ipPort, который запросил данные.
     *
     * @param ipPort <ip, port> получателя
     * @throws IOException
     */
    private void sendListOfUsers(Pair<InetAddress, Integer> ipPort) throws IOException {
        StringBuilder users = new StringBuilder("Доступные пользователи:\n");
        for (String name : clientMap.values()) {
            users.append("\n").append(name);
        }

        users.append("Введите -u, логин получателя и сообщение:\n");
        unicast(users.toString(), ipPort);
    }

    /**
     * Отправка сообщения в режиме unicast.
     *
     * @param unimsg Сообщение
     * @param ipPort <ip, port> получателя
     * @throws IOException
     */
    private void unicast(String unimsg, Pair<InetAddress, Integer> ipPort) throws IOException {
        byte[] sendMsg = unimsg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, ipPort.getKey(), ipPort.getValue());
        serverSocket.send(sendPacket);
    }


    /**
     * Широковещательная рассылка сообщений.
     *
     * @param clientMsg Текст сообщения
     * @throws IOException
     */
    private void broadcast(String clientMsg) throws IOException {

        byte[] sendMsg = clientMsg.getBytes();
        // Эмуляция для тестирования на разных портах с одним ip
        for (Pair<InetAddress, Integer> clientData : clientMap.keySet()) {
            DatagramPacket sendPacket = new DatagramPacket(
                    sendMsg, sendMsg.length,
                    clientData.getKey(),
                    clientData.getValue()
            );
            serverSocket.send(sendPacket);
        }
        // В реальном приложении, если все клиенты слушают определенный порт, например 11000
        // DatagramPacket sendPacket = new DatagramPacket(sendMsg, sendMsg.length, InetAddress.getByName("224.0.0.0"), 11000);
        // serverSocket.send(sendPacket);
    }

}