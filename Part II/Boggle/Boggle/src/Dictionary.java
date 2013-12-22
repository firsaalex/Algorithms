/**
 * Created by firsaalex on 07.12.13.
 */
public class Dictionary {
    String[] dict;
    int N;

    public Dictionary(String filename) {
        String s;
        N = 0;
        dict = new String[584983];
        In in = new In(filename);

        while ((s = in.readLine())!=null) {
            dict[N] = s;
            N++;
        }

    }

    public String[] getAllWords() {
        return dict;
    }

    public int length() {
        return N;
    }
}
