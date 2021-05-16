import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server = null;
    private Socket sock = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            sock = server.accept();
            in = new BufferedReader(new InputStreamReader(sock.getInputStream( )));
            out = new PrintWriter(sock.getOutputStream(), true);
            while (processQuery());
        }
    }

    private boolean processQuery() {
        int result = 0;
        String response = "";
        try {
            String query = in.readLine();
            if (query==null)
                return false;

            String[] fields = query.split("#");
            if (fields.length == 0){
                return true;
            } else {
                var action = fields[0];
                Directory directory;
                File file;

                System.out.println(action);

                switch(action) {
                    case "DirectoryFindById":
                        var id = Long.parseLong(fields[1]);
                        directory = DirectoryDAO.findById(id);
                        response = directory.getName();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "FileFindByDirectoryid":
                        id = Long.parseLong(fields[1]);
                        var list = FileDAO.findByDirectoryId(id);
                        var str = new StringBuilder();
                        for(File c: list) {
                            str.append(c.getId());
                            str.append("#");
                            str.append(c.getDirectoryId());
                            str.append("#");
                            str.append(c.getName());
                            str.append("#");
                            str.append(c.getSize());
                            str.append("#");
                        }
                        response = str.toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "FileFindByName":
                        var name = fields[1];
                        file = FileDAO.findByName(name);
                        response = file.getId().toString()+"#"+file.getDirectoryId().toString()+"#"+file.getName()+"#"+file.getSize().toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "DirectoryFindByName":
                        name = fields[1];
                        directory = DirectoryDAO.findByName(name);
                        response = directory.getId().toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "FileUpdate":
                        id = Long.parseLong(fields[1]);
                        var directoryid = Long.parseLong(fields[2]);
                        name = fields[3];
                        var size = Long.parseLong(fields[4]);
                        file = new File(id,name,directoryid,size);
                        if(FileDAO.update(file))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "DirectoryUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        directory = new Directory(id,name);
                        if (DirectoryDAO.update(directory))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "FileInsert":
                        directoryid = Long.parseLong(fields[1]);
                        name = fields[2];
                        size = Long.parseLong(fields[3]);
                        file = new File((long) 0,name,directoryid,size);
                        if(FileDAO.insert(file))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "DirectoryInsert":
                        name = fields[1];
                        directory = new Directory();
                        directory.setName(name);

                        System.out.println(name);

                        if(DirectoryDAO.insert(directory))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "FileDelete":
                        id = Long.parseLong(fields[1]);
                        file = new File();
                        file.setId(id);
                        if(FileDAO.delete(file))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "DirectoryDelete":
                        id = Long.parseLong(fields[1]);
                        directory = new Directory();
                        directory.setId(id);
                        if(DirectoryDAO.delete(directory))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "FileAll":
                        var list1 = FileDAO.findAll();
                        str = new StringBuilder();
                        for(File c: list1) {
                            str.append(c.getId());
                            str.append("#");
                            str.append(c.getDirectoryId());
                            str.append("#");
                            str.append(c.getName());
                            str.append("#");
                            str.append(c.getSize());
                            str.append("#");
                        }
                        response = str.toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "DirectoryAll":
                        var list2 = DirectoryDAO.findAll();
                        str = new StringBuilder();
                        for(Directory c: list2) {
                            str.append(c.getId());
                            str.append("#");
                            str.append(c.getName());
                            str.append("#");
                        }
                        response = str.toString();
                        System.out.println(response);
                        out.println(response);
                        break;
                }
            }

            return true;
        }
        catch(IOException e){
            return false;
        }
    }
    public static void main(String[] args) {
        try {
            Server srv = new Server();
            srv.start(12345);
        } catch(IOException e) {
            System.out.println("Возникла ошибка");
        }
    }
}
