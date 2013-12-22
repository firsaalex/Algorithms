import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by firsaalex on 07.12.13.
 */
public class BoggleSolverTest {
    BoggleSolver bs;
    @Before
    public void setUp() throws Exception {
        String[] s = new String[1];
        s[0] = "dd";
        bs = new BoggleSolver(s);

    }

    @Test
    public void testGetAllValidWords() throws Exception {

    }

    @Test
    public void testScoreOf() throws Exception {
        Assert.assertEquals(0, bs.scoreOf(""));
        Assert.assertEquals(0, bs.scoreOf("1"));
        Assert.assertEquals(0, bs.scoreOf("12"));
        Assert.assertEquals(1, bs.scoreOf("123"));
        Assert.assertEquals(1, bs.scoreOf("1234"));
        Assert.assertEquals(2, bs.scoreOf("12345"));
        Assert.assertEquals(3, bs.scoreOf("123456"));
        Assert.assertEquals(5, bs.scoreOf("1234567"));
        Assert.assertEquals(11, bs.scoreOf("12345678"));
        Assert.assertEquals(11, bs.scoreOf("123456789"));

    }
}
