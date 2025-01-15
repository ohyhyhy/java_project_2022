import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class StationInfo {
    String lineName;
    String stationName;

    public StationInfo(String lineName, String stationName) {
        this.lineName = lineName;
        this.stationName = stationName;
    }
    public String getLineName() {
        return lineName;
    }

    public String getStationName() {
        return stationName;
    }
}

public class WordList {
	BufferedReader br = null;
    String line = "";
	Vector<StationInfo> csvVector = new Vector<StationInfo>();
	
	public WordList() {
		try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("data.csv"), "CP949"));
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                List<String> aLine = new ArrayList<String>();
                String[] lineArr = line.split(","); // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
                csvVector.add(new StationInfo(lineArr[1], lineArr[0]));
            }
            csvVector.remove(0);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close(); // 사용 후 BufferedReader를 닫아준다.
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	}
	public Vector<StationInfo> getCsvVector() {
		return csvVector;
	}
		
	public String getWord() {
		int index = (int)(Math.random()*csvVector.size());
		return csvVector.get(index).getStationName();
	}
}