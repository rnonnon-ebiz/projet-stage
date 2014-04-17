package fr.stage.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtil {

    public static void close(ResultSet res) {
	if (res != null) {
	    try {
		res.close();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void close(PreparedStatement stm) {
	if (stm != null) {
	    try {
		stm.close();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void close(Statement stm) {
	if (stm != null) {
	    try {
		stm.close();
	    }
	    catch (SQLException e) {
		e.printStackTrace();
	    }
	}
    }

    public static void close(ResultSet res, Statement stm) {
	close(res);
	close(stm);
    }

    public static void close(ResultSet res, PreparedStatement stm) {
	close(res);
	close(stm);
    }
}
