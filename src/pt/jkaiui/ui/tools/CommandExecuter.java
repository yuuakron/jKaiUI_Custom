package pt.jkaiui.ui.tools;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yuuakron
 */
public class CommandExecuter implements Runnable {

    private StringWriter strWriter;
    private PrintWriter pwriter;
    private BufferedReader buffReader;

    public CommandExecuter() {
    }

    private String doExec(List<String> commands) throws IOException {

        ProcessBuilder pb = new ProcessBuilder(commands);
        Process proc = pb.start();
        buffReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        strWriter = new StringWriter();
        pwriter = new PrintWriter(strWriter);


        Thread th = new Thread(this);

        th.start();

        try {

            th.join();

        } catch (InterruptedException e) {

            throw new IOException("Command Exec Failed");

        }

        buffReader.close();

        pwriter.close();

        String temp = strWriter.toString();

        if ((temp.length() > 1) && (temp.substring(temp.length() - 1).getBytes()[0] == 10)) {

            temp = temp.substring(0, temp.length() - 1);

        }

        return temp;

    }

    public void doCommand(String command) {
        try {
            ArrayList<String> commands = new ArrayList<String>();
            commands.add("/bin/sh");
            commands.add("-c");
            commands.add(command+" &");
            doExec(commands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        String line;

        try {

            while ((line = buffReader.readLine()) != null) {

                pwriter.println(line);

            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}