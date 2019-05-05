package com.yuning.gao.service;

import com.yuning.gao.config.dependConfig.WisdomdeviceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * nio socket服务端
 */
@Service
@Slf4j
public class SocketServer {
    //解码buffer
    private Charset cs = Charset.forName("UTF-8");
    //选择器（叫监听器更准确些吧应该）
    private static Selector selector;

    private final WisdomdeviceConfig wisdomdeviceConfig;
    /*映射客户端channel */
    public static Map<String, SocketChannel> clientsMap = new HashMap<String, SocketChannel>();

    public SocketServer(WisdomdeviceConfig wisdomdeviceConfig) {
        this.wisdomdeviceConfig = wisdomdeviceConfig;
    }

    /**
     * 启动socket服务，开启监听
     * @throws
     */
    @PostConstruct
    public void startSocketServer(){
//        try {
//            //打开通信信道
//            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//            //设置为非阻塞
//            serverSocketChannel.configureBlocking(false);
//            //获取套接字
//            ServerSocket serverSocket = serverSocketChannel.socket();
//            //绑定端口号
//            serverSocket.bind(new InetSocketAddress(wisdomdeviceConfig.getServicePort()));
//            //打开监听器
//            selector = Selector.open();
//            //将通信信道注册到监听器
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    //监听器会一直监听，如果客户端有请求就会进入相应的事件处理
//                    while (true){
//                        Set<SelectionKey> selectionKeys = null;//监听到的事件
//                        try {
//                            selector.select();//select方法会一直阻塞直到有相关事件发生或超时
//                            selectionKeys = selector.selectedKeys();
//                            for (SelectionKey key : selectionKeys) {
//                                handle(key);
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        selectionKeys.clear();//清除处理过的事件
//                    }
//                }
//            }).start();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }



    }

    /**
     * 处理不同的事件
     * @param selectionKey
     * @throws IOException
     */
    private void handle(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        String requestMsg = "";
        int count = 0;
        if (selectionKey.isAcceptable()) {
            //每有客户端连接，即注册通信信道为可读
            serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
        else if (selectionKey.isReadable()) {
            ByteBuffer rBuffer = ByteBuffer.allocate(1024);
            socketChannel = (SocketChannel)selectionKey.channel();
            System.out.println("socketChannel.hashCode() = "+socketChannel.hashCode());
            log.info("socketChannel.hashCode() = "+socketChannel.hashCode());
            clientsMap.put(String.valueOf(socketChannel.hashCode()),socketChannel);
            rBuffer.clear();
            count = socketChannel.read(rBuffer);
//            //读取数据
            if (count > 0) {
                rBuffer.flip();
                requestMsg = String.valueOf(cs.decode(rBuffer).array());
            }
            String responseMsg = "guangbo ke huduan xiaoxi:"+requestMsg;
            System.out.println("responseMsg = "+responseMsg);
//            UserService service = SpringUtil.getBean(UserServiceImpl.class);
            //返回数据

//            sBuffer.put(responseMsg.getBytes("UTF-8"));
//            sBuffer.flip();
//            socketChannel.write(sBuffer);
//            if (!clientsMap.isEmpty()) {
//                for (Map.Entry<String, SocketChannel> entry : clientsMap.entrySet()) {
//                    SocketChannel temp = entry.getValue();
//                    if (!socketChannel.equals(temp)) {
//                        ByteBuffer sBuffer;
//                        sBuffer = ByteBuffer.allocate(responseMsg.getBytes("UTF-8").length);
//                        sBuffer.clear();
//                        sBuffer.put(responseMsg.getBytes("UTF-8"));
//                        sBuffer.flip();
//                        //输出到通道
//                        temp.write(sBuffer);
//                    }
//                }
//            }
            //socketChannel.close();

        }
    }

}