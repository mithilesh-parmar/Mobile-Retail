package utils;

public class DatabaseHelper {

	private static DatabaseHelper instance;


	public static DatabaseHelper getInstance(){
		if (instance == null)
			synchronized (DatabaseHelper.class) {
				if (instance == null) instance = new DatabaseHelper();
			}
		return instance;
	}

}
