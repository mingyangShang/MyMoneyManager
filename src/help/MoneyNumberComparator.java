package help;

import java.util.Comparator;
import java.util.HashMap;


public class MoneyNumberComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		HashMap<String,Object> map1 = (HashMap<String,Object>)o1;
		HashMap<String,Object> map2 = (HashMap<String,Object>)o2;
		int number1=0,number2=0;
		try{
			number1=Integer.valueOf((String)map1.get("totalMoney"));
			number2=Integer.valueOf((String)map2.get("totalMoney"));
		}catch(Exception e){
		}
		return (number1-number2);
	}
}
