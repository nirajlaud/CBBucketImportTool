import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

	public static int j;
	public static int k;
	public static void main(String[] args) {

		//Collection<File> a = FileUtils.listFiles(new File("F:\\Downloads"), null, true);
		//System.out.println(a.size());
		String s = null;
		try {
			s = args[0];
			String s1 = args[1];
		} catch(Exception e) {
			s = new String("abcd");	
		}
		
		Character c;//char c
		Integer i = 0;//int i
		Float f;//float f
		Long l;//long l		
		
		Runnable r = new Runnable() {

			@Override
			public void run() {				
				System.out.println("running anon Runnable thread "+ (k++));
				
			}
			
		};
		Thread t0 = new Thread() {
			@Override
			public void run() {				
				System.out.println("running anon Thread thread"+ (k++));
				
			}
			
		};
		
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(new MyClass());
		Thread t3 = new Thread(t0);
		Thread t4 = new Thread(() -> {System.out.println("Functional Interface thread called");});
		
		
		ExecutorService es = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),
                new ThreadPoolExecutor.CallerRunsPolicy()
			);

		for(j = 0; j <= 20; j++)
			es.execute(()-> {
				System.out.println("test"+j);
				try {
					Thread.sleep(20000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
	}


}
