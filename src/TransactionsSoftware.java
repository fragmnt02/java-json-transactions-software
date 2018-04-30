package transactionssoftware;

//Importing all needed libraries
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author franciscoarcegarcia
 */
public class TransactionsSoftware {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException{
        //Commands
        if(null == args[1]){
            //ERROR
            System.out.println("COMMAND NOT FOUND");
        }else switch (args[1]) {
            case "add":
                //ADD TRANSACTIONS
                System.out.println("ADD FUNCTION");
                Add(args[4],args[6],args[7],args[9]);
                break;
            case "list":
                //LIST TRANSACTIONS
                System.out.println("LIST FUNCTION");
                List(args[0]);
                break;
            case "sum":
                //SUM TRANSACTIONS
                System.out.println("SUM FUNCTION");
                Sum(args[0]);
                break;
            default:
                //SHOW TRANSACTIONS
                System.out.println("SHOW FUNCTION");
                Show(args[1]);
                break;
        }
    }
    
    //Add function that extracts all data from json
    static void Add(String amount, String description, String date, String id) throws FileNotFoundException, IOException, ParseException{
        // Adapting strings
        description=description.replace(",","");
        amount=amount.replace(",","");
        date=date.replace("date:","");
        date=date.replace(",","");
        //Creating unique id for every transaction
        String transaction=UUID.randomUUID().toString();

        // creating temporal JSON with all data to store
        JSONObject jo = new JSONObject();
        // putting data to JSONObject
        jo.put("transaction_id", transaction);
        jo.put("amount", amount);
        jo.put("description", description);
        jo.put("date", date);
        jo.put("user_id", id);
        String filename="";
        int currentid=0;
        boolean nuevo=false;
        //Looking for json file to store data
        for(int i=0;true;i++){
            File fileTemp = new File(i+"File.json");
            //If the file exist
            if(fileTemp.exists()){
                currentid=i;
                filename=i+"File.json";
                if(currentid==0){
                    FileReader f = new FileReader(fileTemp);
                    BufferedReader b = new BufferedReader(f);
                    //If the file is not empty
                    while(b.readLine()==null){
                        nuevo=true;
                        break;
                    }
                    break;
                }
                break;
            }
            
        }
        
        File file = new File(filename);
        //If file exists, there is more than one json in the file and the file is not empty
        if(file.exists() && currentid>=0 && nuevo==false) {
            currentid++;
            //Getting all jsons in the file
            Object obj = new JSONParser().parse(new FileReader(filename));
            JSONObject read = (JSONObject) obj;
            //adding the new json to the jsons in the file
            read.put(currentid+"", jo);
            file.delete();
            PrintWriter pw = new PrintWriter(currentid+"File.json");
            pw.write(read.toJSONString());
            pw.flush();
            pw.close();
            //Printing the current generated json
            System.out.println('{'+"\"transaction_id\": \""+transaction+"\", \"amount\": "+amount+", \"description\": \""+description+"\", \"date\":\""+date+"\", \"user_id\": "+id+'}');
        } else{
            //If the file is empty 
            JSONObject finalJson = new JSONObject();
            //Adding the json to the file
            finalJson.put("0",jo);
            PrintWriter pw = new PrintWriter("0File.json");
            pw.write(finalJson.toJSONString());
            pw.flush();
            pw.close();
            //Printing json
            System.out.println('{'+"\"transaction_id\": \""+transaction+"\", \"amount\": "+amount+", \"description\": \""+description+"\", \"date\":\""+date+"\", \"user_id\": "+id+'}');
        }
        
    }

    static void Sum(String user_id) throws FileNotFoundException, IOException, ParseException{
        String filename;
        int flag;
        //Looking for the file
        for(int i=0;true;i++){
            File fileTemp = new File(i+"File.json");
            if(fileTemp.exists()){
                flag=i;
                filename=i+"File.json";
                FileReader f = new FileReader(fileTemp);
                BufferedReader b = new BufferedReader(f);
                while(b.readLine()==null){
                    System.out.println("There is no data");
                    break;
                }
                break;
            }
        }
        //Getting all jsons in the file
        Object obj = new JSONParser().parse(new FileReader(filename));
        JSONObject read = (JSONObject) obj;
        double totalAmount=0;
        boolean exist=false;
        for(int j=0;j<=flag;j++){//going through all the jsons
            JSONObject temporal = (JSONObject) read.get(""+j);
            String id_temporal =(String) temporal.get("user_id");
            if(id_temporal.equals(user_id)){//If the json is from a specific user all the amounts are added
                exist=true;
                String amount= (String) temporal.get("amount");
                double tempAmount=Double.parseDouble(amount);
                totalAmount=totalAmount+tempAmount;
            }else if(j==flag && exist==false){//If we can not find transactions of an user
                System.out.println("Transactions not found");
            }
        }
        System.out.println('{'+"\"user_id\": "+user_id+","+" \"sum\": "+totalAmount+'}');
    }
    
    //from now on all methods have the same logic just by changing the condition...
    
    static void List(String user_id) throws FileNotFoundException, IOException, ParseException{
        String filename;
        int flag;
        for(int i=0;true;i++){
            File fileTemp = new File(i+"File.json");
            if(fileTemp.exists()){
                flag=i;
                filename=i+"File.json";
                FileReader f = new FileReader(fileTemp);
                BufferedReader b = new BufferedReader(f);
                while(b.readLine()==null){
                    System.out.println("There is no data");
                    break;
                }
                break;
            }
        }
        
        Object obj = new JSONParser().parse(new FileReader(filename));
        JSONObject read = (JSONObject) obj;
        boolean exist=false;
        for(int j=0;j<=flag;j++){
            JSONObject temporal = (JSONObject) read.get(""+j);
            String id_temporal = (String) temporal.get("user_id");
            if(id_temporal.equals(user_id)){
                exist=true;
                String amount=(String) temporal.get("amount");
                String description=(String) temporal.get("description");
                String date=(String) temporal.get("date");
                String transaction_id=(String) temporal.get("transaction_id");
                int newID=Integer.parseInt(user_id);
                double newAmount =Double.parseDouble(amount);
                System.out.println('{'+"\"transaction_id\": \""+transaction_id+"\", \"amount\": "+newAmount+", \"description\": \""+description+"\", \"date\":\""+date+"\", \"user_id\": "+newID+'}');
            }else if(j==flag && exist==false){
                System.out.println("Transaction not found");
            }
        }
    }
    
    static void Show(String transaction_id) throws FileNotFoundException, IOException, ParseException{
        String filename;
        int flag;
        for(int i=0;true;i++){
            File fileTemp = new File(i+"File.json");
            if(fileTemp.exists()){
                flag=i;
                filename=i+"File.json";
                FileReader f = new FileReader(fileTemp);
                BufferedReader b = new BufferedReader(f);
                while(b.readLine()==null){
                    System.out.println("There is no data");
                    break;
                }
                break;
            }
        }
        
        Object obj = new JSONParser().parse(new FileReader(filename));
        JSONObject read = (JSONObject) obj;
        boolean exist=false;
        for(int j=0;j<=flag;j++){
            JSONObject temporal = (JSONObject) read.get(""+j);
            String id_temporal = (String) temporal.get("transaction_id");
            if(id_temporal.equals(transaction_id)){
                exist=true;
                String amount=(String) temporal.get("amount");
                String description=(String) temporal.get("description");
                String date=(String) temporal.get("date");
                String user_id=(String) temporal.get("user_id");
                System.out.println('{'+"\"transaction_id\": \""+id_temporal+"\", \"amount\": "+amount+", \"description\": \""+description+"\", \"date\":\""+date+"\", \"user_id\": "+user_id+'}');
                break;
            }else if(j==flag && exist==false){
                System.out.println("Transaction not found");
            }
        }
    }
    
}
