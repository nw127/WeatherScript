package weather;

public class Driver {
		
	public static void main(String[] args) {
		
		DBResponse response = new DBResponse();
		WeatherData wd = new WeatherData(response.getCity(), response.getState(), "token.txt");
		MainFrame.launch(wd);
		
	}

}
