import java.util.*;

class CalendarDemo{
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();  //单例设计模式

		// calendar.set(year, month, day);  //设置时间
		// calendar.add(Calendar.YEAR, offset)  //偏移时间

		System.out.print( "当前时间：" + calendar.get(Calendar.YEAR) + "年" );

		//查表法思想

		String[] month = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "拾壹", "拾贰"};
		System.out.print( month[calendar.get(Calendar.MONTH)] + "月" );

		System.out.print( calendar.get(Calendar.DAY_OF_MONTH) + "日 " );

		String[] weeks = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		System.out.println( weeks[calendar.get(Calendar.DAY_OF_WEEK)] );
	}
}