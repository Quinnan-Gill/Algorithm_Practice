import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.regex.*;

/*
 * The program that runs the autocompletion engine
 */
public class AutoComplete {
    private TrieSTInterface<Integer> dictionary; // the DBL for the dictionary
    private TrieSTInterface<Integer> user_history; // A smaller DBL for the user_history
    String filePath;
    private String dict;
    private String user_dict;
    private long total_time;
    private int timed;

    public AutoComplete() {
        // A DBL for both the user_history and dictionary symbol tables
        dictionary = new DeLaBriandais<Integer>();
        user_history = new DeLaBriandais<Integer>();

        // The data files
        dict = "dictionary.txt";
        user_dict = "user_history.txt";
        // The absolute path to the current present working directory
        filePath = new File("").getAbsolutePath() + "/";
        // The number of times the program ran for the average
        timed = 0;
    }

    /*
     * A method to run the program to completetion
     */
    public void run() {
        loadDictionaries();
        loadUserHistory();
        prompt();
        completeHistory();
    }

    /*
     * Opens the dictionary.txt file and loads it into the DBL
     */
    private void loadDictionaries() {
        String word;
        File dictFile;
        BufferedReader reader = null; // File input stream reader

        try {
            dictFile = new File(filePath + dict); // create the file
            reader = new BufferedReader(new FileReader(dictFile));

            // read each line and put it into the dictionary DBL
            while ((word = reader.readLine()) != null) {
                dictionary.put(word, Integer.valueOf(0));
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the file
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * If the user_history.txt file exists load it contents as a:
     * key num
     * The number is for the freq of the word used
     */
    private void loadUserHistory() {
        String word;
        BufferedReader reader = null;;
        File userFile = new File(filePath + user_dict);

        // If the file exists load it
        if (userFile.exists()) {
            try {
                reader = new BufferedReader(new FileReader(userFile));

                while ((word = reader.readLine()) != null) {
                    String[] split = word.split(" ");
                    String key = split[0];
                    int value = Integer.parseInt(split[1]);

                    dictionary.put(key, Integer.valueOf(value));
                    user_history.put(key, Integer.valueOf(value));
                }
            } catch(IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * Take everything keys and values from the user DBL and add it
     * to user_history.txt
     */
    private void completeHistory() {
        Writer writer = null;
        File userFile = null;

        try {
            userFile = new File(filePath + user_dict);
            userFile.createNewFile();

            // Create a writer stream
            writer = new BufferedWriter(new FileWriter(filePath + user_dict));

            // Get the iterable
            Iterable<ValueNode<Integer>> hist = user_history.keys();

            String output = "";
            for(ValueNode<Integer> line: hist) {
                output = "";
                // Write as: key value
                output += line.dbl_key + " " + line.pq_key.toString();
                writer.write(output + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    // close
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * This is the prompt in which the user interacts with the user and the DBLs
     */
    private void prompt() {
        String input = "";
        String word = "";
        boolean first = true;
        BufferedReader reader = null;
        ValueNode<Integer>[] options = (ValueNode<Integer>[]) new ValueNode<?>[5];
        int size = 0;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            // Break to exit
            while (true) {
                // If it is the first character the only possible input is a
                // character or to exit
                if (first) {
                    word = "";
                    System.out.print("Enter your first character: ");
                    input = reader.readLine();
                    System.out.println("");

                    word += input;
                    if (input.matches("[A-Za-z\']{1}")) {
                        // User entered a character
                        size = getResults(word, options);
                        first = false; // No longer the first character
                    } else if (input.matches("[!]{1}")) {
                        // ! to exit
                        exit();
                        break;
                    } else {
                        // Exit
                        System.out.println("INVALID INPUT");
                    }
                } else {
                    System.out.print("Enter the next character: ");
                    input = reader.readLine();
                    System.out.println("");

                    word += input;
                    if (input.matches("[A-Za-z\']{1}")) {
                        // User entered character
                        size = getResults(word, options);
                    } else if(input.matches("[0-9]{1}")) {
                        // The user entered a number to select from list
                        int select = Integer.parseInt(input);
                        first = selection(select, options, size);
                    } else if(input.matches("[!]{1}")) {
                        // entered ! to exit
                        exit();
                        break;
                    } else if(input.matches("[$]{1}")){
                        // The user created a new word
                        word = word.substring(0, word.length()-1); // parse out the $
                        new_word(word);
                        first = true;
                    } else {
                        System.out.println("INVALID INPUT");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /*
     * Takes a prefix and loads the options into the options array. It also returns
     * the size if less that 5.
     * @param String prefix the key to search for all words that start with this string
     * @param ValueNode<Integer>[] options the ValueNode array with generic Integer
     * return size of the values loaded
     */
    private int getResults(String prefix, ValueNode<Integer>[] options) {
        int i = 0;
        long start = System.nanoTime(); // Start of loading the results
        long end;
        double seconds; // number of seconds for when converting from long
        String output = "";
        // Get the iterable of ValueNodes
        Iterable<ValueNode<Integer>> results = dictionary.keysWithPrefix(prefix);

        // Go through the
        for(ValueNode<Integer> s : results) {
            if (i >= 5) break; // only get the top five
            options[i] = s;
            output += "("+(i+1)+") "+s.dbl_key+"    ";
            i++;
        }

        // end of reading
        end = System.nanoTime();
        seconds = (double) (end - start) / 1_000_000_000.0; // convert to seconds
        total_time += (end - start); // for the average time

        // The output display
        System.out.printf("(%.6f s)\n", seconds); // for the time taken
        System.out.println("Predictions:");
        System.out.println(output); // print the outputs
        System.out.println("");

        timed++;
        return i;
    }

    /*
     * Makes sure the selection within range and returns the selected option
     * @param int select The users choice
     * @param ValueNode<Integer>[] the choices for the user
     * @param int size the max size of the number of options
     * return if the user input needs to be reset
     */
    private boolean selection(int select, ValueNode<Integer>[] options, int size) {
        // Check if within range
        if (select <= 0 || select > size) {
            System.out.println("INVALID INPUT");
            return false; // does not need to be reset
        } else {
            ValueNode<Integer> selection = options[select-1];
            // Put it in the dictionary DLB
            dictionary.put(selection.dbl_key, selection.pq_key+1);
            // Put it in the user DLB
            user_history.put(selection.dbl_key, selection.pq_key+1);
            // Print out to the display
            System.out.println("");
            System.out.println(" WORD COMPLETED:  "+selection.dbl_key);
            System.out.println("");
            return true; // does need to be reset
        }
    }

    /*
     * Adds a new word to the user created to the dictionary
     *
     */
    private void new_word(String word) {
        Integer new_word;
        if((new_word = dictionary.get(word)) == null) {
            dictionary.put(word, Integer.valueOf(1));
            user_history.put(word, Integer.valueOf(1));
        } else {
            dictionary.put(word, Integer.valueOf(new_word.intValue()+1));
            user_history.put(word, Integer.valueOf(new_word.intValue()+1));
        }
        System.out.println("");
        System.out.println(" WORD COMPLETED:  "+word);
        System.out.println("");
    }

    /*
     * Exits but needs to display the stats
     */
    private void exit() {
        if (timed == 0) { // Exits to prevent dividing by zero
            System.out.printf("Average time:  %.6f s\n", (double) 0.0);
        } else {
            // Displays the avaerage time
            double aver_time = (double) total_time / 1_000_000_000.0;
            aver_time /= (double) timed;
            System.out.printf("Average time:  %.6f s\n", aver_time);
        }
        System.out.println("Bye!");
    }
}
