package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Fxml.TableViewController;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.util.List;

@XmlRootElement(name = "Heros")
public class ObservedListWrapped
{
    private List<Heros> list ;

    @XmlElement(name = "Hero")
    public List<Heros> getList()
    {
        return list;
    }

    public void setList(List<Heros> list)
    {
        this.list = list;
    }





}
