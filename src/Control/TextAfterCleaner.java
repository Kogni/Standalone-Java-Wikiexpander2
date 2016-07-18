package Control;

public class TextAfterCleaner {

    public static String Cleanse(String uncleansed) {
	String cleansed = uncleansed;
	String[] datas = new String[5];
	datas[0] = uncleansed;
	datas[1] = "";
	datas[2] = cleansed;

	cleansed = TextHandler.removeAllCSS(datas, Thread.currentThread().getStackTrace()[1].getLineNumber() + "");

	return cleansed;
    }

}
