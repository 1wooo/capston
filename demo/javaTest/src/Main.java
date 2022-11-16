import java.io.*;



public class Main {

    public static void main(String[] args) {

        int res;
        char op;


        System.out.println("17011400 Jeong Junmin");
        File file = new File("C:\\Temp\\q71.txt");


        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufReader = new BufferedReader(fileReader);

            String line = "";
            String[] token;

            while ((line = bufReader.readLine()) != null) {
                token = line.split(" ");
                for (int i = 0; i < token.length; i++) {
                    System.out.print(token[i] + ' ');
                }
                op = line.charAt(3);

                switch (op) {
                    case '+':
                        res = Integer.parseInt(token[0]) + Integer.parseInt(token[2]);
                        System.out.println("= " + res);
                        break;
                    case '-':
                        res = Integer.parseInt(token[0]) - Integer.parseInt(token[2]);
                        System.out.println("= " + res);
                        break;
                    case '*':
                        res = Integer.parseInt(token[0]) * Integer.parseInt(token[2]);
                        System.out.println("= " + res);
                        break;
                    case '/':
                        res = Integer.parseInt(token[0]) / Integer.parseInt(token[2]);
                        System.out.println("= " + res);
                        break;
                }
                //System.out.println(line);
            }
            bufReader.close();
        } catch (IOException e) {
            System.out.println("!!");
        }
    }
}