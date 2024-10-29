package text.kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Posts_chapter07 {
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		
		
//		リスト
		Object[][] list = {
				{1003, "2023-02-08", "昨日の夜は徹夜でした・・", 13},
				{1002, "2023-02-08", "お疲れ様です！", 12},
				{1003, "2023-02-09", "今日も頑張ります！", 18},
				{1001, "2023-02-09", "無理は禁物ですよ！", 17},
				{1002, "2023-02-10", "明日から連休ですね！", 20},
		};
		try {
//			============データーベースに接続============
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"9eRxj!*$~X7-"
					);
			System.out.println("データベース接続成功");
			
//			============データーベースをリセット============
			statement = con.createStatement();
			String truncateSql = "TRUNCATE TABLE posts;";
			statement.executeUpdate(truncateSql);
			System.out.println("テーブルのリセットを実行");
		
//			============リストの１行目から順番にインサート============			
//			SQLクエリを準備
			String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES ( ?, ?, ?, ?);";
			preparedStatement = con.prepareStatement(insertSql);
			
			
			int rowCnt = 0;
			for ( Object[] row : list) {
//				?の部分をリストのデータに置き換える
				preparedStatement.setInt(1, (int) row[0]);
				preparedStatement.setString(2, (String) row[1]);
				preparedStatement.setString(3, (String) row[2]);
				preparedStatement.setInt(4, (int) row[3]);
				
//				SQLクエリを実行
				System.out.println("レコード追加：" + preparedStatement.toString() );
				rowCnt = preparedStatement.executeUpdate();
				System.out.println(rowCnt + "件のレコードが追加されました");
			}
			
//			============SQL抽出============
//			SQLクエリを準備
			statement = con.createStatement();
			String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
			
//			SQLクエリを実行
			ResultSet result = statement.executeQuery(selectSql);
			
//			SQLクエリの実行結果を抽出
			int i = 1;
			while(result.next()) {
				Date postedAt = result.getDate("posted_at"); // Data型 // 投稿日時
				String postContent = result.getString("post_content"); // 投稿内容
				int likes = result.getInt("likes");                    // いいね数
				System.out.println(i + "件目：投稿日時=" + postedAt + "/投稿内容=" + postContent + "/いいね数=" + likes );
				i++;
			}
			
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
//			使用したオブジェクトを開放
			if( statement != null ) {
				try { statement.close(); } catch (SQLException ignore) {}
			}
			if( preparedStatement != null) {
				try { preparedStatement.close(); } catch (SQLException ignore) {}
			}
			if( con != null ) {
				try { con.close(); } catch (SQLException ignore) {}
			}
		}	
	}
}
