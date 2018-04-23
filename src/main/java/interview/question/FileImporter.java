package interview.question;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.nio.file.Path;




public class FileImporter {
	String FileName;
	int records_recieved = 0;
	int records_successful = 0;
	int records_failed = 0;
	BufferedWriter error_bw;
	BufferedWriter log_bw;
	SQLWriter sql_writer = new SQLWriter();
	
	public FileImporter() {
		
	}
	
	public FileImporter(String filename) {
		 this.FileName=filename;
		 try {
			 //get file path and make writers for log and error file
			 Timestamp ts = new Timestamp(System.currentTimeMillis());
			 Path p = Paths.get(this.FileName);
			 
			 String error_file = ts.toString()+".csv";
			 error_file = error_file.replaceAll("\\:+", "-");
			 error_file = p.getParent()+"\\bad-data-"+ error_file;
			 
			 String log_file = p.getParent() + "\\log.txt";
			 
			 error_bw = new BufferedWriter(new FileWriter(error_file));
			 log_bw = new BufferedWriter(new FileWriter(log_file));
		 }catch(IOException e) {
			 e.printStackTrace();
		 }
	}
	
	public void readFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(this.FileName))) {
			String line = "";
			//line=br.readLine(); //discard the first line with the column names
			
			
			while((line = br.readLine()) != null) {
				List<String> linelist = new ArrayList<String>(Arrays.asList(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1)));
				linelist.removeAll(Arrays.asList("",null));
				this.proccessLine(linelist,line);
			}
			
			error_bw.close();
			log_bw.write("Records recieved: " + records_recieved + "\r\n" +
							"Records successful: " + records_successful + "\r\n" +
							"Records failed: " + records_failed + "\r\n");
			log_bw.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void proccessLine(List<String> linelist ,String line) {
		this.records_recieved += 1;
		//If record is the correct size adds to database
		if(linelist.size() != 10) {
			this.records_failed +=1;
			try {
				error_bw.write(line + "\n");
			}catch(IOException e) {
				e.printStackTrace();
			}
		}//if not the correct size the record is added to the error file
		else {
			this.records_successful +=1;
			//System.out.println(line);
			try {sql_writer.insert(linelist.get(0), linelist.get(1), linelist.get(2),
						linelist.get(3), linelist.get(4), linelist.get(5),
						Double.parseDouble(linelist.get(6).substring(1)), Boolean.valueOf(linelist.get(7)),
						Boolean.valueOf(linelist.get(8)), linelist.get(9));
			}catch(Exception e) {
				System.out.println("Incorectly formated entry at line " + records_recieved+ ": " + line + "   Entry Ignored");
				//e.printStackTrace();
			}

			
		}
	}
	
}
