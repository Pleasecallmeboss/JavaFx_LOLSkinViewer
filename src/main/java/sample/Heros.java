package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Heros
{
    private StringProperty SkinName;
    private StringProperty Name;
    private StringProperty SkinUrl;

    public Heros()
    {
        Name = new SimpleStringProperty("Null");
        SkinName = new SimpleStringProperty("Null");
        SkinUrl = new SimpleStringProperty("Null");

    }

    public Heros(String name, String skinname ,String url)
    {
        super();
        Name = new SimpleStringProperty(name);
        SkinName = new SimpleStringProperty(skinname);
        SkinUrl = new SimpleStringProperty(url);
    }

    public String getSkinUrl()
    {
        return SkinUrl.get();
    }

    public StringProperty skinUrlProperty()
    {
        return SkinUrl;
    }

    public void setSkinUrl(String skinUrl)
    {
        this.SkinUrl.set(skinUrl);
    }

    public String getSkinName()
    {
        return SkinName.get();
    }

    public StringProperty skinNameProperty()
    {
        return SkinName;
    }

    public void setSkinName(String skinName)
    {
        this.SkinName.set(skinName);
    }

    public String getName()
    {
        return Name.get();
    }

    public StringProperty nameProperty()
    {
        return Name;
    }

    public void setName(String name)
    {
        this.Name.set(name);
    }
}
