public class HelloJNI {

    public native int add(int a, int b);

    static {
        System.loadLibrary("hello_jni");
    }

    public static void main(String[] args) {
        HelloJNI helloJNI = new HelloJNI();
        int result = helloJNI.add(3, 10);
        System.out.printf("Result - %d\n", result);
    }
}
