package su.ias.teledoc.data;

/**
* Created with IntelliJ IDEA.
* User: n.senchurin
* Date: 21.10.2014
* Time: 18:19
*/
public class MainMenuItemData {

    private String title;
    private String description;
    private int color;
    private int bcgDrawableId;


    public MainMenuItemData(String title, String description, int color, int bcgDrawableId) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.bcgDrawableId = bcgDrawableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBcgDrawableId() {
        return bcgDrawableId;
    }

    public void setBcgDrawableId(int bcgDrawableId) {
        this.bcgDrawableId = bcgDrawableId;
    }
}
