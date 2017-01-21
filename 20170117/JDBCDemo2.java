/**
 * JDBC   java data base connectivity
 * JDBC --> 操作 --> ODBC -- 操作 --> DBMS
 */
import java.sql.*;

class JDBCDemo2 {
    public static void main(String[] args) {
        Connection conn =  null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            // 加载驱动至内存 java 1.8 后要下载sqljdbc4.jar放到pathto/Java/jre/lib/ext下
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // 连接数据库  window nt 验证无需账密  sa(sql administrator) sqlserver默认端口1433
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=mydb", "sa", "root");
            // 创建Statement或PreparedStatement，用于发送sql语句  预编译
            pst = conn.prepareStatement("select * from [user] where id=?");
            pst.setInt(1, 1);   // 设置第1个占位符值为1
            // 执行sql语句  boolean execute(sql) executeUpdate(insert|update|delete sql)
            rs = pst.executeQuery();
            // 循环取出结果
            while(rs.next()){
                int id = rs.getInt(1);
                String username = rs.getString(2);
                String password = rs.getString(3);
                String email = rs.getString(4);
                System.out.println("["+id+", "+username+", "+password+", "+email+"]");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // 关闭资源，后打开的先关闭
            try{
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}