import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Socket sock = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static final String split = "#";

    Client(String ip, int port) throws IOException{
        // establish connection
        sock = new Socket(ip,port);
        // get input/output streams
        in = new BufferedReader(
                new InputStreamReader(sock.getInputStream( )));
        out = new PrintWriter(sock.getOutputStream(), true);
    }

    public Directory directoryFindById(Long id) {
        var query = "DirectoryFindById"+split+id.toString();
        out.println(query);
        String response = "";
        try {
            response = in.readLine();
            return new Directory(id,response);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public File fileFindByName(String name) {
        var query = "FileFindByName"+split+name;
        out.println(query);
        String response = "";
        try {
            response = in.readLine();
            String[] fields = response.split(split);
            var id = Long.parseLong(fields[0]);
            var directoryid = Long.parseLong(fields[1]);
            var size = Long.parseLong(fields[3]);
            return new File(id,name,directoryid,size);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public Directory directoryFindByName(String name) {
        var query = "DirectoryFindByName"+split+name;
        out.println(query);
        try {
            var response = Long.parseLong(in.readLine());
            return new Directory(response,name);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public boolean fileUpdate(File file) {
        var query = "FileUpdate"+split+file.getId().toString()+
                "#"+file.getDirectoryId().toString()+"#"+file.getName()
                +"#"+file.getSize().toString();
        out.println(query);
        try {
            var response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean directoryUpdate(Directory directory) {
        var query = "DirectoryUpdate"+split+directory.getId().toString()+
                "#"+directory.getName();
        out.println(query);
        try {
            var response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean fileInsert(File file) {
        var query = "FileInsert"+
                "#"+file.getDirectoryId().toString()+"#"+file.getName()
                +"#"+file.getSize().toString();
        out.println(query);
        try {
            var response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean directoryInsert(Directory directory) {
        var query = "DirectoryInsert"+
                "#"+directory.getName();
        out.println(query);
        try {
            var response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean directoryDelete(Directory directory) {
        var query = "DirectoryDelete"+split+directory.getId().toString();
        out.println(query);
        try {
            var response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean fileDelete(File file) {
        var query = "FileDelete"+split+file.getId().toString();
        out.println(query);
        try {
            var response = in.readLine();
            if ("true".equals(response))
                return true;
            else
                return false;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public List<Directory> directoryAll(){
        var query = "DirectoryAll";
        out.println(query);
        var list = new ArrayList<Directory>();
        try {
            var response = in.readLine();
            String[] fields = response.split(split);
            for(int i=0;i<fields.length; i+=2) {
                var id = Long.parseLong(fields[i]);
                var name = fields[i+1];
                list.add(new Directory(id,name));
            }
            return list;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public List<File> fileAll(){
        var query = "FileAll";
        out.println(query);
        var list = new ArrayList<File>();
        try {
            var response = in.readLine();
            String[] fields = response.split(split);
            for(int i=0;i<fields.length; i+=4) {
                var id = Long.parseLong(fields[i]);
                var directoryid = Long.parseLong(fields[i+1]);
                var name = fields[i+2];
                var size = Long.parseLong(fields[i+3]);
                list.add(new File(id,name,directoryid,size));
            }
            return list;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public List<File> fileFindByDirectoryid(Long idc){
        var query = "FileFindByDirectoryid"+split+idc.toString();
        out.println(query);
        var list = new ArrayList<File>();
        try {
            var response = in.readLine();
            String[] fields = response.split(split);
            for(int i=0;i<fields.length; i+=4) {
                var id = Long.parseLong(fields[i]);
                var directoryid = Long.parseLong(fields[i+1]);
                var name = fields[i+2];
                var size = Long.parseLong(fields[i+3]);
                list.add(new File(id,name, directoryid,size));
            }
            return list;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public void disconnect(){
        try {
            sock.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws IOException {
//        //(new Client()).test("localhost",12345);
//    }
}