import com.sun.jna.Pointer;

/**
 * @author Yiheng
 * @create 2018/7/3
 * @since 1.0.0
 */
public class TestJni {

    public native void jyGetUserInfo();

    public native Pointer jyFetchData();

    public static void main(String[] args){
        System.load("jyTQAITools");
        TestJni testJni = new TestJni();
        testJni.jyGetUserInfo();
        Pointer pointer = testJni.jyFetchData();
        String str = pointer.getString(0);
        System.out.println(str);
    }
}
