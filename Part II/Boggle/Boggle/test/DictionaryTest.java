import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by firsaalex on 07.12.13.
 */
public class DictionaryTest {


    @Test
    public void testLength() throws Exception {
        Dictionary d = new Dictionary("test/dictionary-nursery.txt");
        Assert.assertEquals(1647,d.length());

        d = new Dictionary("test/dictionary-algs4.txt");
        Assert.assertEquals(6013,d.length());

        d = new Dictionary("test/dictionary-common.txt");
        Assert.assertEquals(20068,d.length());

        d = new Dictionary("test/dictionary-shakespeare.txt");
        Assert.assertEquals(23688,d.length());

        d = new Dictionary("test/dictionary-enable2k.txt");
        Assert.assertEquals(173528,d.length());

        d = new Dictionary("test/dictionary-twl06.txt");
        Assert.assertEquals(178691,d.length());

        d = new Dictionary("test/dictionary-yawl.txt");
        Assert.assertEquals(264061,d.length());

        d = new Dictionary("test/dictionary-sowpods.txt");
        Assert.assertEquals(267751,d.length());

        d = new Dictionary("test/dictionary-zingarelli2005.txt");
        Assert.assertEquals(584983,d.length());

    }
}
