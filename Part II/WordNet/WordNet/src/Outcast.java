/**
 * Created with IntelliJ IDEA.
 * User: firsa_as
 * Date: 08.11.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
public class Outcast {
    private  WordNet wordNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int maxDist = 0;
        int curDist = 0;
        int outcastIndex = 0;

        for (int  i = 0; i < nouns.length; i++) {
            curDist = 0;
            for (int j = 0; j < nouns.length; j++) {
                if (i != j ){
                    curDist += wordNet.distance(nouns[i],nouns[j]);
                }
            }
            if (curDist > maxDist) {
                maxDist = curDist;
                outcastIndex = i;
            }
        }

        return  nouns[outcastIndex];
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }



}
