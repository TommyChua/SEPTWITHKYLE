package PartADemoedCheckPoint;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerPanel {
    private static Scanner in=new Scanner(System.in);           //scanner object to input from user in console
    
    public static void BookAppointment(String[] data2) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("services.txt"));
        ArrayList<String> services = new ArrayList<String>();
        ArrayList<String> activities = new ArrayList<String>();
        String line = "";
        while((line=br.readLine())!=null){
            services.add(line);
        }
        System.out.println("Services");
        for(int a=0;a<services.size();a++){
            System.out.println((a+1)+". "+services.get(a));
        }
        String choice = Utils.validateIntegerInput("Select a Service: ", 1,4);
        int selection = Integer.parseInt(choice);
        String fileName = services.get(selection-1);
        
        System.out.println("Activities");
        for(int a=0;a<activities.size();a++){
            System.out.println((a+1)+". "+activities.get(a));
        }
        System.out.print("Select an Activity: ");
        choice = Utils.validateIntegerInput("Select a Service: ", 1,4);
        String activity = activities.get(selection-1);
        
        fileName = fileName.toLowerCase();
        String service = fileName;
        fileName = fileName+".txt";
        br.close();
        Booking book = new Booking();
        ArrayList<String> data=new ArrayList<>();
        try
        {
            FileReader fr=new FileReader(fileName);
            br=new BufferedReader(fr);
            line="";
            System.out.printf("%1s%10s%25s%20s%12s\n","Employee Name","Day","Activity","Time Available","Status");
            int i=1;
            while((line=br.readLine())!=null)
            {
                data.add(line);     //save list of bookings
                String arr[]=line.split(",");
                System.out.printf("%1s%1s%20s%25s%15s%20s\n",i+". ",arr[0],arr[1],arr[2],arr[3],arr[4]);
                i++;
            }
            br.close();
            String select = Utils.validateInput("\nSelect Time i.e 1,2", "[\\d][,][\\d][)]");
            String[] selected = select.split(",");
            selection = 0;
            for(int a=0;a<selected.length;a++){
            	selection = Integer.parseInt(selected[a]);
                if(selection<=data.size())
                {
                    String arr[]=data.get(selection-1).split(",");
                    if(arr[4].equals("available"))
                    {
                        book.BookSlot(data,selection,fileName);
                        BufferedWriter writer2 = new BufferedWriter(new FileWriter("BookingSummaries.txt",true));
                        String s=data.get(selection-1);
                        s=s.replace("available", "");
                        s=s.replace(",", " ");
                        writer2.write("Customer "+data2[0]+" "+data2[1]+" booked Appointment on "+s);
                        writer2.newLine();
                        System.out.println("Customer "+data2[0]+" "+data2[1]+" booked Appointment on "+s);
                        writer2.close();
                    }
                    else
                        System.out.println("Slot Already Booked");
                }
                else
                    System.out.println("Not Valid Slot Try Again!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void showBookingTimeTable() throws IOException
    {
    	int a;
        BufferedReader br = new BufferedReader(new FileReader("services.txt"));
        ArrayList<String> services = new ArrayList<String>();
        String line = "";
        while((line=br.readLine())!=null){
            services.add(line);
        }
        System.out.println("Services");
        for(a=0;a<services.size();a++){
            System.out.println((a+1)+". "+services.get(a));
        }

        String choice = Utils.validateIntegerInput("Select a Service: ", 1,a+1);
        int selection = Integer.parseInt(choice);
        String fileName = services.get(selection-1);
        fileName = fileName.toLowerCase();
        String service = fileName;
        fileName = fileName+".txt";
        br.close();
        try
        {
            FileReader fr=new FileReader(fileName);
            br=new BufferedReader(fr);
            line="";
            System.out.printf("%1s%10s%25s%20s%12s\n","Employee Name","Day","Activity","Time Available","Status");
            while((line=br.readLine())!=null)
            {
                String arr[]=line.split(",");
                System.out.printf("%1s%20s%25s%15s%20s\n",arr[0],arr[1],arr[2],arr[3],arr[4]);
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void BookCustomAppointment(String[] data2) throws FileNotFoundException, IOException{
        
        
        String choice = "";
        String fileName = "";
        String empName = "";
        boolean empSet = false;
        BufferedReader br = new BufferedReader(new FileReader("services.txt"));
        ArrayList<String> services = new ArrayList<String>();
        String line = "";
        while((line=br.readLine())!=null){
            services.add(line);
        }
        System.out.println("Services");
        for(int a=0;a<services.size();a++){
            System.out.println((a+1)+". "+services.get(a));
        }
        
        choice = Utils.validateIntegerInput("Select a Service: ", 1,4);
        int selection = Integer.parseInt(choice);
       	fileName = services.get(selection-1);

    	
    	fileName = fileName.toLowerCase();
        String service = fileName;
        fileName = fileName+".txt";
        br.close();
        boolean repeat = true, daySet = false ,activitySet =false;
        String day = "";
        String activity="";
        ArrayList<String> list = new ArrayList<String>();
        
        br = new BufferedReader(new FileReader(fileName));
        line = "";
        while((line=br.readLine())!=null){
            list.add(line);
        }
        ArrayList<String> temp = new ArrayList<String>();
        
        while(repeat){
        	if(activitySet==false){
				activity = Utils.validateActivity(); // validates input for Activity
        	}
            if(daySet==false){
                day = Utils.validateDay("\nSelect day of appointment i.e.(Monday, ...,Friday):"); // validates input for Day
            }
            if(empSet==false){
                ArrayList<String> employeeNames = Utils.getEmployeeNames(fileName); //checks if employee name exists ---
                empName =  Utils.validateEmployees(employeeNames); //validates input for Selected Employee and it exists in file

            }
            
            int one = 0;
            for(int a=0;a<list.size();a++){
                String recs[] = list.get(a).split(",");
                if(recs[1].toLowerCase().equals(day) && (recs[0].toLowerCase().equals(empName) || empName.equals("*"))&&(recs[2].toLowerCase().equals(activity))){
                    daySet = true;
                    activitySet=true;
                    empSet = true;
                    temp.add(list.get(a));
                    one++;
                }
                if(one<1){
                    if(recs[1].toLowerCase().equals(day) && !recs[0].toLowerCase().equals(empName)){
                        daySet = true;
                        empSet = false;
                    }
                    else if(!recs[1].toLowerCase().equals(day) && recs[0].toLowerCase().equals(empName)){
                        daySet = false;
                        empSet = true;
                    }
                }
            }
            if(daySet==true && empSet==true && activitySet==true){
                repeat = false;
                break;
            }
            if(daySet==false){
                System.out.println("Please Enter Some Other Day!");
                repeat = true;
            }
            
            if(empSet == false){
                System.out.println("Please Select Some Other Employee!");
                repeat = true;
            }
            
        }
        br.close();
        int i;
        System.out.println("Displaying.......");
        for (i = 0; i < temp.size(); i++) {
            System.out.println((i+1)+"."+temp.get(i));
        }
        int select = 0;
       // System.out.print("Select From Above: ");
        String selected;
        String recs[];
        while(true){
            int max = temp.size();
            choice = Utils.validateIntegerInput("Select From Above: ", 1, max);
            selection = Integer.parseInt(choice);
	        selected = temp.get(selection-1);
	        recs = selected.split(",");
	        
	        if(recs[4].equals("Un-available")){
	            System.out.println("Booking Un-Available");
	            System.out.println("Please select another");
	        }else{
	        	recs[4] = "Un-available";
	        	break;
	        }
            
        }
        
        String modified = recs[0]+","+recs[1]+","+recs[2]+","+ recs[3] + "," + recs[4];
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for(int a=0;a<list.size();a++){
            if(list.get(a).equals(selected)){
                list.set(a, modified);
                bw.write(list.get(a));
                bw.newLine();
            }
            else{
                bw.write(list.get(a));
                bw.newLine();
            }
        }
        bw.close();
        BufferedWriter writer2 = new BufferedWriter(new FileWriter("BookingSummaries.txt",true));
        writer2.write("Customer "+data2[0]+" "+data2[1]+" booked Appointment on "+recs[0]+" "+recs[1]+" "+recs[2] +" " + recs[3]);
        writer2.newLine();
        writer2.close();
    }
    public void cancelBooking() throws FileNotFoundException, IOException{
        
        BufferedReader br = new BufferedReader(new FileReader("services.txt"));
        ArrayList<String> services = new ArrayList<String>();
        String line = "";
        while((line=br.readLine())!=null){
            services.add(line);
        }
        System.out.println("Services");
        for(int a=0;a<services.size();a++){
            System.out.println((a+1)+". "+services.get(a));
        }
        String choice = Utils.validateIntegerInput("Select a Service: ", 1,4);
        int selection = Integer.parseInt(choice);
        String fileName = services.get(selection-1);
        fileName = fileName.toLowerCase();
        String service = fileName;
        fileName = fileName+".txt";
        br.close();
        br = new BufferedReader(new FileReader("BookingSummaries.txt"));
        line = "";
        ArrayList<String> bookings = new ArrayList<String>();
        while((line=br.readLine())!=null){
            bookings.add(line);
        }
        for(int a=0;a<bookings.size();a++){
            System.out.println((a+1)+". "+bookings.get(a));
        }
        br.close();
        String delete ="";
        BufferedWriter bw = new BufferedWriter(new FileWriter("BookingSummaries.txt"));
        int max = bookings.size();
        choice = Utils.validateIntegerInput("Select from above to cancel: ", 1, max);
        selection = Integer.parseInt(choice);
        for(int a=0;a<bookings.size();a++){
            if((selection-1)==a){
                delete = bookings.get(a);
            }
            else{
                bw.write(bookings.get(a));
                bw.newLine();
            }
        }
        bw.close();
        System.out.println("Delete "+delete+" \nfrom "+fileName);
        String del[] = delete.split(" ");
        
        
        br = new BufferedReader(new FileReader(fileName));
        ArrayList<String> records = new ArrayList<String>();
        while((line=br.readLine())!=null){
            records.add(line);
        }
        for(int a=0;a<records.size();a++){
            String[] recs = records.get(a).split(",");
            String record = recs[0] + recs[2] + recs[1] + recs[3];
            String cancelledAppointment = del[6] + del[8] + " " + del[9]+ del[7] + del[10];
            if( record.equalsIgnoreCase(cancelledAppointment)){
                recs[4] = "available";
                records.set(a, recs[0]+","+recs[1]+","+recs[2]+","+recs[3]+","+recs[4]);
            }
        }
        br.close();
        bw = new BufferedWriter(new FileWriter(fileName));
        for(int a=0;a<records.size();a++){
            bw.write(records.get(a));
            bw.newLine();
        }
        bw.close();
        
        
    }

}

