package text.kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Connection con = null;
		Statement statement = null;
		
		try {
//			データベースに接続
			con = DriverManager.getConnection(
				"jdbc:mysql://localhost/challenge_java",
				"root",
				"9eRxj!*$~X7-"
				);
			System.out.println("データベース接続成功");
			
//			===================================更新===================================
//			更新SQLクエリを準備
			statement = con.createStatement();
			String sql = "UPDATE scores SET score_math=95, score_english=80 WHERE id=5;";
			
//			更新SQLクエリを実行
			System.out.println("レコード更新" + statement.toString() );
			int rowCnt = statement.executeUpdate(sql);
			System.out.println(rowCnt + "件のレコードが更新されました");
			System.out.println(); // 改行
			
//			===================================並べ替え===================================			
//			並べ替えクエリを準備
			sql = """
					SELECT * FROM scores
					ORDER BY score_math DESC,
							 score_english DESC;
				  """;
//			並べ替えSQLクエリを実行
			System.out.println("データ取得を実行：" + sql);
			ResultSet result = statement.executeQuery(sql);
//			並べ替え実行結果を抽出
			while(result.next()) {
				int    id           = result.getInt("id");
				String name         = result.getString("name");
				int    scoreMath    = result.getInt("score_math");
				int    scoreEnglish = result.getInt("score_english");
				System.out.println(result.getRow() + "件目：生徒ID=" + id + "/氏名=" + name 
						+ "/数学=" + scoreMath + "/英語=" + scoreEnglish);
			}
					
		} catch(SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
//			使用したオブジェクトの開放
			if( statement != null )	{
				try { statement.close(); } catch(SQLException ignore) {}
			}
			if( con != null ) {
				try { con.close(); } catch(SQLException ignore) {}
			}
		}
	}

}
