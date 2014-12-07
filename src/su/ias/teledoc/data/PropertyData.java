package su.ias.teledoc.data;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 14.11.2014
 * Time: 17:31
 */
public class PropertyData {


    private int value;
    private String title;

    public PropertyData(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
