import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
public class Main {
    public static Map<String, Map<String, String>> instructionSet = new HashMap<>();
  //  public static HashMap<String, String> tempStorage = new HashMap<String, String>();
    public static Map<String, String> temp = new HashMap<>();



    public static void main(String[] args) throws Exception {
        String contentTxt = null;
        String contentAsm = null;
        String result = null;
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader("instuctions.txt"));


            Map<String, String> tempStorage = new HashMap<String, String>();

            while ((contentTxt = bufferreader.readLine()) != null) {
                if(contentTxt.equals("HLT")){break;}

                String[] dataArray = contentTxt.split(" ",3);
              //  System.out.println(contentTxt);

                if(instructionSet.get(dataArray[0]) == null) {
                	   instructionSet.put(dataArray[0], new HashMap<String,String>() {{
        					put(dataArray[1], dataArray[2]);
 						}}); 

                }else{
                	 //  instructionSet.get(dataArray[0], new HashMap<String,String>(Map.of(dataArray[1],dataArray[2])));
                	   instructionSet.get(dataArray[0]).put(dataArray[1],dataArray[2]);
                   }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileName = "code.asm";
		BufferedReader br = new BufferedReader(new FileReader(fileName));
  		String str, ins, oper, value, addr, temp, res = new String();
  		int line = 0;
  		Map<String, String> map = new HashMap<String, String>();


  		while ((str = br.readLine()) != null){
    		line++;
    		ins = str.split(" ")[0];
    		if(ins.equals("HLT")){
    			System.out.println("OK");
    			res+="23";
    			break;
    		}
    		String s = null;
    		oper = str.split(" ")[1];

    		//System.out.println(ins);

    		if((map = instructionSet.get(ins)) == null){
    			System.out.println(ins+" Syntax Error occured in line: "+line);
    			return;
    		}else{

    			if(oper.contains("#")){
    				temp = oper.split("#")[0]+"#data";
    				value = oper.split("#")[1];

    				//System.out.println(temp);

    				if( (s = map.get(temp)) == null){System.out.println("Error with operand: "+temp+"line: "+line); return;}
    				else{ res += s+value; }
    			}else if(oper.contains(",")){
    				if(oper.split(",")[0].length() == 4){
    					temp = "Address,"+oper.split(",")[1];
    					if( (s = map.get(temp)) == null){System.out.println("Error with operand: "+temp+"line: "+line); return;}
    					else{ res += s+oper.split(",")[0]; }
    				}else if(oper.split(",")[1].length() == 4){
    					temp = oper.split(",")[1]+"Adress";
    					if( (s = map.get(temp)) == null){System.out.println("Error with operand: "+temp+"line: "+line); return;}
    					else{ res += s+oper.split(",")[1]; }
    				}else{
    					if( (s = map.get(oper)) == null){System.out.println("Error with operand: "+oper+"line: "+line); return;}
    					else{ res += s; }
    				}
    			}else if(oper.length() == 4){
    				if( (s = map.get("Address")) == null){System.out.println("Error with operand: "+oper+"line: "+line); return;}
    				else{ res += s; }
    			}else{
    				if( (s = map.get(oper)) == null){System.out.println("Error with operand: "+oper+"line: "+line); return;}
    				else{ res += s; }
    			}
    		}
  		}
        br.close();
  		fileName = "output.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(res);
        bw.close();
    }
}
