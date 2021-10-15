import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

//github token : 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

public class Graph {
    private ArrayList<String> vertexList;  //存储顶点集合
    private int[][] edges;  //存储图对应的邻接矩阵
    private int numofEdges;  //表示边的个数
    private boolean[] isVisited;  //定义一个数组，用于存放是否节点已被访问过

    public static void main(String[] args) {
        //测试
        int n = 5;
        String[] Vertexs = {"A","B","C","D","E"};
        //创建图对象
        Graph graph = new Graph(n);
        //循环添加顶点
        for (String vertex:Vertexs){
            graph.insertVertex(vertex);
        }
        //添加边
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);

        //显示图的邻接矩阵
        graph.showGraph();

        //进行测试
        //System.out.println("图的深度优先遍历：");
        //graph.dfs();
        System.out.println("图的广度优先遍历：");
        graph.bfs();
    }

    //获取节点的第一个邻接节点的下标
    /**
     * @param index
     * @return 如果存在就返回对应的下标，不存在返回-1
     */
    public int getFirstNeighbor(int index){
        for (int j = 0;j < vertexList.size();j++){
            if (edges[index][j] > 0){
                return j;
            }
        }
        return -1;
    }

    //根据前一个邻接节点的下标来获取下一个邻接节点
    public int getNextNeighbor(int v1,int v2){
        for (int j = v2 + 1;j < vertexList.size();j++){
            if (edges[v1][j] > 0){
                return j;
            }
        }
        return -1;
    }

    //深度遍历
    public void dfs(boolean[] isVisited,int i){
        //输出这个节点
        System.out.print(vertexList.get(i) + "->");
        //将这个设置为已被访问过
        isVisited[i] = true;
        //得到下一个邻接节点
        int next = getFirstNeighbor(i);
        //如果得到的值不为-1，说明有下一个邻接节点
        while (next != -1){
            //并且没有被访问过
            if (!isVisited[next]){
                dfs(isVisited,next);
            }
            next = getNextNeighbor(i,next);
        }
    }

    //对dfs进行重载
    public void dfs(){
        //由于上一个针对连通图，如果有孤立的点，则需要循环。所以要重载dfs函数。
        for (int i = 0;i < vertexList.size();i++){
            if (!isVisited[i]){
                dfs(isVisited,i);
            }
        }
    }

    //广度遍历，深度遍历是递归，所以用个数组存储遍历状态。广度需要存储邻接节点，所以要queue存储
    public void bfs(boolean[] isVisited,int index){
        //广度遍历需要使用队列作为存储邻接节点的结构
        int u;  //表示队列的头节点对应的下标
        int w;  //邻接节点w
        //队列，记录节点的访问顺序
        LinkedList queue = new LinkedList();
        //访问节点，输出节点的信息
        System.out.print(vertexList.get(index) + "->");
        //将该节点加入到队列中
        queue.addLast(index);
        //将其设置为已访问
        isVisited[index] = true;
        while (!queue.isEmpty()){
            //取出队列的头节点下标
            u = (Integer) queue.removeFirst();
            //得到vertexList[index]的邻接节点
            w = getFirstNeighbor(u);
            //如果存在邻接节点
            while (w != -1){
                //如果没有被访问过
                if (!isVisited[w]){
                    System.out.print(vertexList.get(w) + "->");
                    isVisited[w] = true;
                    //将其加入队列，后边遍历其邻接节点
                    queue.addLast(w);
                }
                //u为前驱节点，找w后面的下一个邻接节点
                w = getNextNeighbor(u,w);  //体现了广度优先
            }
        }
    }

    //重载bfs，遍历所有的节点，防止有孤立的节点
    public void bfs(){
        for (int i = 0;i < vertexList.size();i++){
            if (!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }

    //类的构造器，用于初始化图
    public Graph(int n){
        //初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<String>();
        numofEdges = 0;
        isVisited = new boolean[n];
    }

    //返回节点的个数
    public int getNumofVertex(){
        return vertexList.size();
    }

    //得到边的数目
    public int getNumofEdges(){
        return numofEdges;
    }

    //返回节点i(下标)对应的节点
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    //返回v1和v2的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    //显示图对应的矩阵
    public void showGraph(){
        //学会这个循环表达方法
        for (int[] link:edges){
            System.out.println(Arrays.toString(link));
        }
    }

    //插入节点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * @param v1  表示点的下标，即第几个点
     * @param v2  第二个顶点对应的下标
     * @param weight  边的权值，意味着添加的边
     * 添加边
     */
    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numofEdges++;
    }
}
