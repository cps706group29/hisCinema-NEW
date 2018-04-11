import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class HttpResponse {

  HttpRequest req;
  String response = "";
  String responseHeader = "";
  String responseBody = "";
  
  String root = "C:/Users/J E D/Documents/Ryerson/8th semester/CPS 706 - Networks/Assignment/hisCinema-NEW";
  File f;
  
  final int OK = 200;
  final int NOT_FOUND = 404;
  final int ERROR = 500;
  
  public HttpResponse(HttpRequest request){
    req = request;
    System.out.println("EQUALS? " + req.filename.equals("/"));
    if(req.filename.equals("/")){
      f = new File(root + "/index.html");
    }else{
      f = new File(root + req.filename);
    }
    try {
      FileInputStream fis = new FileInputStream(f);
      
      responseHeader = createResponseHeader(OK, f.length());
      
      int s;
      while ((s = fis.read()) != -1 ) {
        responseBody += (char) s;
      }

      fis.close();
     
      response = responseHeader + responseBody;
      
    }catch(FileNotFoundException e){
      System.out.println("COULDNT FIND FILE: " + req.filename);
      System.out.println("    (is your root variable set correctly?)");
      
      try{
        File file404 = new File(root + "/404.html");
        FileInputStream file404is = new FileInputStream(file404);
        
        responseHeader = createResponseHeader(NOT_FOUND, file404.length());
        
        int s;
        while ((s = file404is.read()) != -1 ) {
          responseBody += (char) s;
        }
        
        file404is.close();
        
        response = responseHeader + responseBody;

      }catch(FileNotFoundException e1){
        //Ignore
      }catch(Exception e2){
        //Ignore
      }
    }catch(Exception e){
      System.out.println("Something went wrong: " + e);
      try{
        File file500 = new File(root + "/500.html");
        FileInputStream file500is = new FileInputStream(file500);
        
        responseHeader = createResponseHeader(ERROR, file500.length());

        int s;
        while ((s = file500is.read()) != -1 ) {
          responseBody += (char) s;
        }
        file500is.close();

        response = responseHeader + responseBody;
      }catch(FileNotFoundException e1){
        //Ignore
      }catch(Exception e2){
        //Ignore
      }
    }
  }
  
  private String createResponseHeader(int statusCode, long fileLength){
    String header = "";
    header += "HTTP/1.1 " + statusCode + " \r\n";
    header += "Server: Our Java Server/1.0 \r\n";
    header += "Connection: close \r\n";
    header += "Content-Length: " + fileLength + "\r\n";
    header += "\r\n";
    return header;
  }

}
