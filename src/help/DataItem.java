package help;

public class DataItem {
	public String id;
	public String item;
	public String dir;
	public String way;
	public String number;
	public String time;
	public String note;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+"/"+item+"/"+dir+"/"+way+"/"+number+"/"+time+"/"+note;
	} 
	
}
