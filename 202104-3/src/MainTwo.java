import java.io.*;
import java.util.StringTokenizer;
//40分

public class MainTwo {
    public static void dhcpServer() throws IOException{
        //BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bf = new BufferedReader(new FileReader("C:/Users/17865/Desktop/data.txt"));
        //PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(bf.readLine());
        int N = Integer.parseInt(st.nextToken());               //ip地址数量
        int T_def = Integer.parseInt(st.nextToken());           //默认过期时间
        int T_max = Integer.parseInt(st.nextToken());           //最长过期时长
        int T_min = Integer.parseInt(st.nextToken());           //最短过期时长
        String H = st.nextToken();                              //DHCP服务器主机名
        IPStatus[] ip_status = new IPStatus[N];
        for (int i = 0;i < N;i++){
            ip_status[i] = new IPStatus(i+1,0);
        }

        st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());

        Message message;
        for (int i = 0;i < n;i++){
            st = new StringTokenizer(bf.readLine());
            int t_i = Integer.parseInt(st.nextToken());         //报文收到时间t_i
            String sender = st.nextToken();                     //发送方
            String receiver = st.nextToken();                   //接收方
            String messageType = st.nextToken();                    //报文类型
            int ip = Integer.parseInt(st.nextToken());          //ip地址
            int time = Integer.parseInt(st.nextToken());        //期望过期时长

            if (check(receiver,H,messageType)){
                switch (messageType){
                    case "DIS":
                        ip_status = overDue(t_i,ip_status);
                        message = discover(sender,receiver,t_i,time,T_max,T_min,T_def,H,ip_status);
                        if (message != null){
                            System.out.println(message.sendHost + " " + message.receiveHost + " " + message.messageType + " " + message.ip + " " + message.time);
                        }
                        break;
                    case "REQ":
                        if (!receiver.equals("*")){
                            ip_status = overDue(t_i,ip_status);
                            message = request(sender,receiver,t_i,time,T_max,T_min,T_def,ip,H,ip_status);
                            if (message != null){
                                System.out.println(message.sendHost + " " + message.receiveHost + " " + message.messageType + " " + message.ip + " " + message.time);
                            }
                            break;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public static boolean check(String receiver, String H, String messageType){
        if (!receiver.equals(H) && !receiver.equals("*") && !messageType.equals("REQ")){
            return false;
        }else if (!messageType.equals("DIS") && !messageType.equals("REQ")){
            return false;
        }else if ((receiver.equals("*") && !messageType.equals("DIS")) || (receiver.equals(H) && messageType.equals("Dis"))){
            return false;
        }
        return true;
    }

    /**
     *
     * @param sender 报文发送方
     * @param receiver 报文接收方
     * @param t_i 报文接收时刻
     * @param time 报文期望过期时长
     * @param t_max 服务器最长过期时长
     * @param t_min 服务器最短过期时长
     * @param t_def 默认过期时长
     * @param H 服务器主机名
     * @param ip_status ip地址信息
     *
     * @return 返回选取后的ip地址信息
     */
    public static Message discover(String sender,String receiver,int t_i,int time,int t_max,int t_min,int t_def,String H,IPStatus[] ip_status){
        Message message;

        boolean isOccupier = false,isStatus0 = false,isStatus3 = false;
        int occupier = Integer.MAX_VALUE,status0 = Integer.MAX_VALUE,status3 = Integer.MAX_VALUE;
        //选取ip占用者为该申请者的IP地址
        for (int i = 0;i < ip_status.length;i++){
            if (ip_status[i].occupier == sender){
                isOccupier = true;
                if (i <= occupier){
                    occupier = i;
                }
            }
            if (ip_status[i].ipStatus == 0){
                isStatus0 = true;
                if (i <= status0){
                    status0 = i;
                }
            }
            if (ip_status[i].ipStatus == 3){
                isStatus3 = true;
                if (i <= status3){
                    status3 = i;
                }
            }
        }

        int temp = Integer.MAX_VALUE;
        if (isOccupier){
            temp = occupier;
        }else if (isStatus0){
            temp = status0;
        }else if (isStatus3){
            temp = status3;
        }

        if (temp != Integer.MAX_VALUE){
            ip_status[temp].ipStatus = 1;
            ip_status[temp].occupier = sender;

            t_max += t_i; t_min += t_i;
            if (time == 0){
                time += t_def;
            }
            if (time >= t_max){
                ip_status[temp].time_expect = t_max;
            }else if (time <= t_min){
                ip_status[temp].time_expect = t_min;
            }else {
                ip_status[temp].time_expect = time;
            }

            message = new Message(H,sender,"OFR",ip_status[temp].ip,ip_status[temp].time_expect);
            return message;
        }else {
            return null;
        }
    }

    //处理request类型报文
    public static Message request(String sender,String receiver,int t_i,int time,int t_max,int t_min,int t_def,int ip,String H,IPStatus[] ip_status){
        if (!receiver.equals(H)){
            //*改变不了外界ip_status的值。值传递和引用传递，浅拷贝和深拷贝。
            for (int i = 0;i < ip_status.length;i++){
                if (ip_status[i].occupier.equals(sender)){
                    ip_status[i].ipStatus = 0;
                    ip_status[i].time_expect = 0;
                    ip_status[i].occupier = "none";
                }
            }

            return null;
        }
        Message message;

        for (int i = 0;i < ip_status.length;i++){
            if (ip_status[i].ip == ip){
                if (!ip_status[i].occupier.equals(sender)){
                    break;
                }else{
                    ip_status[i].ipStatus = 2;
                    //处理时间问题
                    t_max += t_i; t_min += t_i;
                    if (time == 0){
                        time += t_def;
                    }
                    if (time >= t_max){
                        ip_status[i].time_expect = t_max;
                    }else if (time <= t_min){
                        ip_status[i].time_expect = t_min;
                    }else {
                        ip_status[i].time_expect = time;
                    }

                    message = new Message(H,sender,"ACK",ip_status[i].ip, ip_status[i].time_expect);
                    return message;
                }
            }
        }
        message = new Message(H,sender,"NAK",ip, 0);
        return message;
    }

    //过期更新函数
    public static IPStatus[] overDue(int t_i,IPStatus[] ipStatuses){
        for (int i = 0;i < ipStatuses.length;i++){
            //过期
            if (t_i >= ipStatuses[i].time_expect){
                //ip处于待分配状态
                if (ipStatuses[i].ipStatus == 1){
                    ipStatuses[i].ipStatus = 0;
                    ipStatuses[i].occupier = "none";
                    ipStatuses[i].time_expect = 0;
                }else if (ipStatuses[i].ipStatus == 2){
                    ipStatuses[i].ipStatus = 3;
                    ipStatuses[i].time_expect = 0;
                }
            }
        }
        return ipStatuses;
    }

    public static void main(String[] args) throws IOException{
        dhcpServer();
    }

    //封装ip信息
    static class IPStatus{
        int ip;             //ip地址
        int ipStatus;       //ip状态，0为未分配，1为待分配，2为占用，3为过期
        String occupier;    //占用者，没用时用none表示。有占用者时，用占用者名称。
        int time_expect;    //期望过期时长。

        public IPStatus(int ip, int ipStatus){
            this.ip = ip;
            this.ipStatus = ipStatus;
            this.occupier = "none";
            this.time_expect = 0;
        }

        public IPStatus(int ip, int ipStatus, String occupier, int time_expect){
            this.ip = ip;
            this.ipStatus = ipStatus;
            this.occupier = occupier;
            this.time_expect = time_expect;
        }
    }

    //封装报文信息
    static class Message{
        String sendHost;            //发送主机
        String receiveHost;         //接收主机
        String messageType;         //报文类型
        int ip;                     //IP地址
        int time;                   //过期时刻
        public Message(String sendHost, String receiveHost, String messageType, int ip, int time){
            this.sendHost = sendHost;
            this.receiveHost = receiveHost;
            this.messageType = messageType;
            this.ip = ip;
            this.time = time;
        }
    }

}
