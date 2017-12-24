import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;

/**
 * Created by muhamd gomaa on 12/24/2017.
 */
public class Main {

    // we create 3 strings to put querys on it debend on his type (create / update./create)
    public static StringBuffer insertbuffer = new StringBuffer();
    static StringBuffer updatebuffer = new StringBuffer();
    static StringBuffer createbuffer = new StringBuffer();


    public static void main(String[] args) throws SQLException {

        // we intials and create tables in first script
        database.resetDatabase();
        /// end ///////////

        /// read file and seprate it to make three threads depends on query type
        String s = new String();
        // contain all file
        StringBuffer sb = new StringBuffer();
        // StringBuilder sb= new StringBuilder();

        try {
            FileReader fr = new FileReader(new File("E:\\javatask\\DigitalWellnez_Java_trial\\SQL\\statements.sql"));
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

            BufferedReader br = new BufferedReader(fr);

            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");

            for (int i = 0; i < inst.length; i++) {

                if (inst[i].trim().toLowerCase().startsWith("insert")) {
                    insertbuffer.append(inst[i] + ";");
                } else if (inst[i].trim().toLowerCase().startsWith("update")) {
                    updatebuffer.append(inst[i] + ";");
                } else if (inst[i].trim().toLowerCase().startsWith("create")) {

                    createbuffer.append(inst[i] + ";");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
////

        // make  three threds classes each one excute one type of query

        insertThread thread1 = new insertThread();

        updateThred thread2 = new updateThred();

        createThred thread3 = new createThred();

        thread1.start();

        thread2.start();

        thread3.start();

        boolean thread1IsAlive = true;

        boolean thread2IsAlive = true;

        boolean thread3IsAlive = true;


        do {

            if (thread1IsAlive && !thread1.isAlive()) {

                thread1IsAlive = false;

                System.out.println("Thread 1 is dead.");

            }

            if (thread2IsAlive && !thread2.isAlive()) {

                thread2IsAlive = false;

                System.out.println("Thread 2 is dead.");

            }
            if (thread3IsAlive && !thread3.isAlive()) {

                thread3IsAlive = false;

                System.out.println("Thread 3 is dead.");

            }

        } while (thread1IsAlive || thread2IsAlive || thread3IsAlive);

    }


}
