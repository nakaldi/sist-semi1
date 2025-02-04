package kims.semi1.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "hamster2";
	private static final String PASSWORD = "1234";

	// static 블로은 1.1부터 사용 가능, 클래스 로드를 위해 자주 사용되는 방법
	// class.forName(Driver~~)은 사실 사용할 필요 없음. 자바 6부터 자동적으로 드라이버를 찾아 로드해줌. 명시적으로 작성해줌
	// 아래 스태틱 블록은 클래스가 호출될 때 최초 한 번만 실행됨. 처음에는 드라이버를 로드하냐고 느리지만 다음부터는 빠름
	// 안써도 똑같이 최초 한번만 로드함.
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver loaded successfully");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Driver load failed");
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * 아래 close 메서드들은 try-with-resources 를 쓰면 필요 없다. try-with-resources 문을 사용하면, 생성된
	 * 자원을 자동으로 반납해주므로 아래 close 메서드들은 사용하지 않음 try-with-resources 문은 자바 7에서 만들어져 현대에는
	 * 표준으로 쓰이고 있음. closable 인터페이스를 구현한 클래스들만 try-with-resources 문 사용가능
	 */
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn, PreparedStatement pstmt) {
		DBConnector.close(conn, pstmt, null);
	}

}
