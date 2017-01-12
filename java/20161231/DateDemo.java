import java.text.*;	 //SimpleDateFormat
import java.util.*;  //Date

class DateDemo{
	public static void main(String[] args) {
		Date d = new Date();

		String time = new SimpleDateFormat("yyyy年MM月d日 E HH:mm").format(d);
		System.out.println("现在时间：" + time);
	}
}