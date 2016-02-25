    import java.util.ArrayList;
	import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
	import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

	/**
	 * 1) ���������� ��������� ���������. ������ ��������� (������������) ������
	 *		 � ArrayIndexOutOfBoundException? ��� ���� �������, ����� ����� �� �����������?
	 * 2) ������ ���������� ��������� ���������� ������ � run() �� 10 � 
	 * 		�������� ����� �� ������ print() ����� ���������� ������ ��������.
	 * 		������ ���������� ConcurrentModificationException?
	 * 		��� �������, ����� ����� �� �����������?
	 *
	 */
	public class SynchronizedListTutor {
		static String [] langs =
		    {"SQL", "PHP", "XML", "Java", "Scala",
		    "Python", "JavaScript", "ActionScript", "Clojure", "Groovy",
		    "Ruby", "C++"};

		//1  - make collection synchronized Collection.synhronized(new ArrayList<String>()) but was exeption,
		// lets i use CopyOnWriteArrayList
		List<String> randomLangs = new CopyOnWriteArrayList<String>();
		
		
		public String getRandomLangs() {
			int index = (int)(Math.random()*langs.length);
			return langs[index];
		}
		
		class TestThread implements Runnable {
			String threadName;
			
			public TestThread(String threadName) {
				this.threadName = threadName;
			}
			
			@Override
			public synchronized void run() {
				for (int i=0;i<10;i++) {
					randomLangs.add(getRandomLangs());
					print(randomLangs);
				}
			}
		}
		
		public void print(Collection<?> c) {
			StringBuilder builder = new StringBuilder();
			Iterator<?> iterator = c.iterator();
			while(iterator.hasNext()) {
				 builder.append(iterator.next());
				 builder.append(" ");
			}
			System.out.println(builder.toString());
		}
		
		@Test
		public void testThread() {
			List<Thread> threads = new ArrayList<Thread>();
			for (int i=0;i<100;i++) {
				threads.add(new Thread(new TestThread("t"+i)));
			}
		    System.out.println("Starting threads");
			for (int i=0;i<100;i++) {
				threads.get(i).start();
			}
		    System.out.println("Waiting for threads");
		    try {
				for (int i=0;i<100;i++) {
					threads.get(i).join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    
		}


	}