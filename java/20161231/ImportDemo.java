import java.util.*;
import static java.util.Arrays.*;  //静态导入Arrays类的静态成员

class ImportDemo{
	public static void main(String[] args) {
		int[] arr = {10, 2, 4};
		sort(arr);    // 等价于 Arrays.sort(arr);

		/**
		 * 注意： 类名重名，需要指定包名   顾名思义
		 * 	    方法名重名，需要指定类名
		 */
		System.out.println(Arrays.toString(arr));  // 直接写toString会和Object的重名导致编译失败
	}
}