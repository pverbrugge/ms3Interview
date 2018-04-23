package interview.question;



public class Main {
	
	
	public static void main(String[] args) {
		//reads example file in if no filename given as a command line argument
		FileImporter fi = new FileImporter();
		if (args.length == 0) {
			fi = new FileImporter("src\\main\\resources\\ms3Interview.csv");
		}else {
			try {
				fi = new FileImporter(args[0]);

			}catch(Exception e) {
				System.out.println(e);
				System.exit(1);
			}
		}
		fi.readFile();
		System.out.println("Records recieved: " + fi.records_recieved + "\r\n" + 
							"Records successful: " + fi.records_successful + "\r\n" +
							"Records failed: " + fi.records_failed + "\r\n");	
	}
}

