/**
 * 面向对象多态特性应用实例
 * 思想：
 * 主板使用接口PCI，通过PCI打开设备
 * 新设备符合接口PCI规则，让主板可以打开新设备
 */
interface PCI{
	public abstract void open();
	public abstract void close();
}
class MainBoard{
	public void run(){
		System.out.println("主板通电");
	}
	public void usePCI(PCI pci){
		if (pci != null) {
			pci.open();
		}
	}
	public void closePCI(PCI pci){
		if (pci != null) {
			pci.close();
		}
	}
}
class NetCard implements PCI{
	public void open(){
		System.out.println("网卡打开");
	}
	public void close(){
		System.out.println("网卡关闭");
	}
}
class SoundCard implements PCI{
	public void open(){
		System.out.println("声卡打开");
	}
	public void close(){
		System.out.println("声卡关闭");
	}
}
class DuotaiDemo3 {
	public static void main(String[] args) {
		MainBoard mb = new MainBoard();
		mb.run();

		mb.usePCI(null);	      //传入空对象没事，加个判断即可

		mb.usePCI(new NetCard()); //传入网卡对象，打开网卡
		
		PCI soundcard = new SoundCard(); 
		mb.usePCI(soundcard);     //传入声卡对象，打开声卡
	}
}