package service;



public class ServiceMain {

	public static void main(String[] args) {
		Recorder rec = new Recorder();
		while (true) {
			System.out.println(rec.getActiveProgramDescription());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}

}
