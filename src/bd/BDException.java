package bd;

public class BDException extends Exception{ 
	private String msg;
		  public BDException(String msg){
		    this.msg=msg;
		  }
		  
		  public String getMessage(){
			  return "BDException : "+msg;
		  }
		
	

}
