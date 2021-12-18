package haarHuffman;

import java.util.ArrayList;

public class HuffmanDecode {

    public NodeDecode root;

    public HuffmanDecode(){
        this.root = new NodeDecode();
    }

    public void add(char data, String sequence){

        NodeDecode temp = this.root;
        int i = 0;
        for(i=0;i<sequence.length()-1;i++){

          if(sequence.charAt(i)=='0'){
                if(temp.left == null){
                    temp.left = new NodeDecode();
                    temp = temp.left;
                }
                else{
                   temp = (NodeDecode) temp.left;
                }
            }
            else
              if(sequence.charAt(i)=='1'){
                if(temp.right == null){
                    temp.right = new NodeDecode();
                    temp = temp.right;
                }
                else{
                    temp = (NodeDecode) temp.right;
                }
         }}

        if(sequence.charAt(i)=='0'){

            temp.left = new NodeDecode(data); 
           }
        else{
            temp.right = new NodeDecode(data); 

        }
        }

    public String getDecodedMessage(String encoding){

        String output = "";
        NodeDecode temp = this.root;
        for(int i = 0;i<encoding.length();i++){

            if(encoding.charAt(i) == '0'){
                temp = temp.left;

                if(temp.left == null && temp.right == null){
                    output+= temp.getData();
                    temp = this.root;
                }
            }
            else
            {
                temp = temp.right;
                if(temp.left == null && temp.right == null){
                    output+= temp.getData();
                    temp = this.root;  
                }

            }
        }
        return output;
    }
    
    public ArrayList<String> getDecodedMessage(ArrayList<String> encoding){

        ArrayList<String> outputta = new ArrayList<String>();
        NodeDecode temp = this.root;
        String tmp = "";
        for(int i = 0;i<encoding.get(0).length();i++){

            if(encoding.get(0).charAt(i) == '0'){
                temp = temp.left;

                if(temp.left == null && temp.right == null){
                	if (String.valueOf(temp.getData()).equalsIgnoreCase("["))
                		doNothing();
                	else
                		if (String.valueOf(temp.getData()).equalsIgnoreCase(",")) {
                			outputta.add(tmp);
                			tmp="";
                		} else
                			if (String.valueOf(temp.getData()).equalsIgnoreCase(" "))
                				doNothing();
                			else
                				if (String.valueOf(temp.getData()).equalsIgnoreCase("]")) {
                        			outputta.add(tmp);
                        			tmp="";
                				} else
                					if (String.valueOf(temp.getData()).equalsIgnoreCase(";"))
                						doNothing();
                					else
                						if (i == (encoding.get(0).length()-1)) {
                							tmp+=String.valueOf(temp.getData());
                							outputta.add(tmp);
                							tmp="";
                						}else
                							tmp+=String.valueOf(temp.getData());
                	
                    temp = this.root;
                }
            }
            else
            {
                temp = temp.right;
                if(temp.left == null && temp.right == null){
                	if (String.valueOf(temp.getData()).equalsIgnoreCase("["))
                		doNothing();
                	else
                		if (String.valueOf(temp.getData()).equalsIgnoreCase(",")) {
                			outputta.add(tmp);
                			tmp="";
                		} else
                			if (String.valueOf(temp.getData()).equalsIgnoreCase(" "))
                				doNothing();
                			else
                				if (String.valueOf(temp.getData()).equalsIgnoreCase("]")) {
                        			outputta.add(tmp);
                        			tmp="";
                				} else
                					if (String.valueOf(temp.getData()).equalsIgnoreCase(";"))
                						doNothing();
                						//outputta.add(String.valueOf(temp.getData()));
                					else
                						if (i == (encoding.get(0).length()-1)) {
                							tmp+=String.valueOf(temp.getData());
                							outputta.add(tmp);
                							tmp="";
                						}else
                							tmp+=String.valueOf(temp.getData());
                	
                    temp = this.root;  
                }
            }
        }
        return outputta;
    }
    // Traversal of reconstructed huffman tree for debugging.
    public void traversal(NodeDecode node){

        if(node == null)
              return;
        traversal(node.left);
        traversal(node.right);

    }
    private void doNothing() {}

    }


class NodeDecode{

    NodeDecode left;
    NodeDecode right;
    char data;

    public NodeDecode(){

    }
    public NodeDecode(char data){
        this.data = data;
    }
    public void setData(char data){
        this.data = data;
    }
    public char getData(){
        return this.data;
    }
    @Override
    public String toString(){
       if(this.data == Character.UNASSIGNED){
           return "No Value";
       } 
       else
           return ""+this.data;
    }
}