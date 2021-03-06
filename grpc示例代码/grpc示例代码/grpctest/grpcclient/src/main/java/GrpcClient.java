import autogenerated.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Ticsmyc
 * @date 2021-03-15 21:01
 */
public class GrpcClient {
    /**
     * 接收rpc服务端ip和端口
     */
    private static final String host="localhost";
	//private static final String host="115.29.151.24";
    private static final int serverport=9999;

    public static void main(String[] args) throws Exception{

        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(host, serverport).usePlaintext().build();
        try {
            //建立连接
            CalculatorServiceGrpc.CalculatorServiceBlockingStub calService = CalculatorServiceGrpc.newBlockingStub(managedChannel);
            //构造rpc请求
            CalRequest request = CalRequest.newBuilder().setFirst("5").setSecond("3").build();
            System.out.println("send rpc request  :  5 + 3");
            //发送请求并等待响应
            CalResponse response = calService.add(request);
            //输出结果
            System.out.println("get rpc response : "+response.getResult());
        }finally {
            managedChannel.shutdown();
        }
    }
}
