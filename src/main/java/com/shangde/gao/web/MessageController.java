package com.shangde.gao.web;

import com.shangde.gao.domain.ResDTO;
import com.shangde.gao.domain.exception.ForbiddenException;
import com.shangde.gao.service.SocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static com.shangde.gao.domain.RsJsonManager.reNewError;
import static com.shangde.gao.domain.RsJsonManager.success;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 21:29 2019/2/22
 * Description：${description}
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    //发送客户端信息
    @PostMapping("/sendToClient")
    public ResDTO sendToClient(@RequestParam("clientHashCode") String clientHashCode, @RequestParam("responseMsg") String responseMsg) {
        if (!SocketServer.clientsMap.isEmpty()) {
            if (SocketServer.clientsMap.containsKey(clientHashCode)) {
                try {
                    SocketChannel socketChannel = SocketServer.clientsMap.get(clientHashCode);
                    ByteBuffer sBuffer;
                    sBuffer = ByteBuffer.allocate(responseMsg.getBytes("UTF-8").length);
                    sBuffer.clear();
                    sBuffer.put(responseMsg.getBytes("UTF-8"));
                    sBuffer.flip();
                    //输出到通道
                    socketChannel.write(sBuffer);
                    return success();
                } catch (IOException e) {
                    throw new ForbiddenException("发送数据给客户端异常!!!");
                }
            } else {
                return reNewError("当前发送的客户端尚未连上");
            }
        }
        return reNewError("目前暂无任何客户端连接");
    }
}
