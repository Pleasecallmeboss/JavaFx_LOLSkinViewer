package sample.Fxml;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.ConnetMongo;
import sample.Heros;
import sample.Main;
import sample.ObservedListWrapped;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;


public class TableViewController
{
    private Main main;
    private ObservableList<Heros> HeroList = FXCollections.observableArrayList();
    private Desktop desktop = Desktop.getDesktop();
    private Map<String, String> HeroMap = new HashMap<>();
    private Image imageshow;
    //private List<Heros> HeroList;

    private final Node rootIcon =
            new ImageView(new Image(String.valueOf(getClass().getResource("/RootTreeItem.png")),
                    16, 16, true, true));
    private final Image depIcon =
            new Image(getClass().getClassLoader().getResourceAsStream("RootTreeItem.png"), 16, 16, true, true);
//    List<Heros> HeroList = Arrays.<Heros>asList(
//            new Heros("黑暗之女", "黑暗之女" , "https://game.gtimg.cn/images/lol/act/img/skin/small1000.jpg"),
//            new Heros("黑暗之女", "哥特萝莉 安妮","https://game.gtimg.cn/images/lol/act/img/skin/small1001.jpg"),
//            new Heros("诡术妖姬","诡术妖姬","https://game.gtimg.cn/images/lol/act/img/skin/small7000.jpg"),
//            new Heros("诡术妖姬","潮流女王 乐芙兰","https://game.gtimg.cn/images/lol/act/img/skin/small7001.jpg"));

    TreeItem<String> rootNode;
    Preferences preferences = Preferences.userNodeForPackage(getClass());


    @FXML
    private Button BtnTest;
    @FXML
    private MenuItem OpenCatalog;

    @FXML
    private MenuItem SaveDocument;

    @FXML
    private MenuItem OpenPicture;

    @FXML
    private MenuItem OpenDocument;

    @FXML
    private ImageView ImageShow;

    @FXML
    private Label LbeShow;

    @FXML
    private JFXButton BtnAdd;

    @FXML
    private Button BtnDelete;


    @FXML
    private JFXTextField NameField;

    @FXML
    private JFXTextField NickNameField;

    @FXML
    private TableColumn<Heros, String> TableName;

    @FXML
    private TableView<Heros> TableView;

    @FXML
    private TableColumn<Heros, String> TableSkinName;


    @FXML
    private javafx.scene.control.TreeView<String> TreeView;

    @FXML
    private ImageView ImageTreeView;

    public TableViewController()
    {
        this.rootNode = new TreeItem<>("LOL英雄皮肤", rootIcon);
        Boolean isLoaded = preferences.getBoolean("isLoaded", false);
        if (isLoaded)
        {
            System.out.println(preferences.get("filePath", null));
            //loadHeroDataFromFile(new File("C:\\Users\\刘嘉尧\\Desktop\\a.xml"));
            loadHeroDataFromFile(new File(preferences.get("filePath", null)));
        }
        else
        {
            HeroList = ConnetMongo.getList();
            System.out.println(HeroList.size());
            System.out.println(new File("C:\\Users\\刘嘉尧\\Desktop").canWrite());
            saveHeroDataToFile(new File("C:\\Users\\刘嘉尧\\Desktop\\a.xml"));

            preferences.putBoolean("isLoaded", true);
        }

        System.out.println(HeroList.size());

    }


    @FXML
    private void initialize()
    {
        TableView.setItems(HeroList);
        ReflushTreeView();

        TableName.setCellValueFactory(new PropertyValueFactory<Heros, String>("Name"));
        TableSkinName.setCellValueFactory(new PropertyValueFactory<Heros, String>("SkinName"));

        TableName.setCellFactory(TextFieldTableCell.<Heros>forTableColumn());
        TableName.setOnEditCommit(
                (TableColumn.CellEditEvent<Heros, String> t) ->
                {
                    ((Heros) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setSkinName(t.getNewValue());
                });

        TableSkinName.setCellFactory(TextFieldTableCell.<Heros>forTableColumn());
        TableSkinName.setOnEditCommit(
                (TableColumn.CellEditEvent<Heros, String> t) ->
                {
                    ((Heros) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setName(t.getNewValue());
                });

        showHeroDetail(null);
        TableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Heros>()
        {
            @Override
            public void changed(ObservableValue<? extends Heros> observable, Heros oldValue, Heros newValue)
            {
                BtnDelete.setDisable(false);
                showHeroDetail(newValue);
                Image image = new Image(HeroMap.get(newValue.getSkinName()), false);
                ImageShow.setImage(image);
            }
        });

        TableView.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                BtnDelete.setDisable(true);
            }
        });


        System.out.println("initialize  started");
        rootNode.setExpanded(true);



        TreeView.setRoot(rootNode);
        TreeView.setEditable(false);
        TreeView.setCellFactory((TreeView<String> p) ->
                new TableViewController.TextFieldTreeCellImpl());

        TreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue)
            {
                if (newValue.isLeaf())
                {
                    imageshow = new Image(HeroMap.get(newValue.getValue()), false);
                    //imageshow = new Image("file:E:\\pr\\图像\\Player\\image1.jpg");
                    ImageTreeView.setImage(imageshow);
                    System.out.println(newValue.getValue() + "is pushed");
                    //BtnTest.setText(String.valueOf(ImageTreeView.isVisible()));
                    //ImageShow.setImage(imageshow);

                }
            }
        });


    }

    public void setMain(Main main)
    {
        this.main = main;
        //HeroList = main.getHerosList();
        //TableView.setItems(HeroList);
    }


    @FXML
    void OnAdd(ActionEvent event)
    {
        HeroList.add(new Heros(NickNameField.getText(), NameField.getText(), null));
        NickNameField.clear();
        NameField.clear();
        ReflushTreeView();
    }

    @FXML
    void OnDelete(ActionEvent event)
    {
        HeroList.remove(TableView.getSelectionModel().getSelectedItem());
        HeroMap.remove(TableView.getSelectionModel().getSelectedItem());
        BtnDelete.setDisable(true);
    }

    private void showHeroDetail(Heros heros)
    {
        if (heros == null)
        {
            LbeShow.setText(null);
        }
        else
        {
            LbeShow.setText(heros.getSkinName() + heros.getName());

        }

    }

    private void showDialog()
    {
        //这里，Jfoenix开发团队没有考虑到对话框的创建是要点击之后生成的，demo直接是在Main.java文件里面写的
//我是在issue那里找到了一位外国开发者，直接在controller里面创建对话框显示
//我稍微折腾一下，准备弄个想Android那样，对话框可以由一行代码生成，这里就暂时使用外国开发者代码凑合凑合
//使用的话，如果Stage的宽度不长，对话框显示会不完全，得在Main.java中设置一下
        final JFXAlert<String> alert = new JFXAlert<>((Stage) BtnDelete.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

// Create the content of the JFXAlert with JFXDialogLayout
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Enter Username"));
        layout.setBody(new VBox(new Label("Please enter the username of the person you would like to add.")));

// Buttons get added into the actions section of the layout.
        JFXButton addButton = new JFXButton("ADD");
        addButton.setButtonType(JFXButton.ButtonType.FLAT);
        addButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent addEvent)
            {
                // When the button is clicked, we set the result accordingly
                alert.setResult("hello");
                alert.hideWithAnimation();
            }
        });

        JFXButton cancelButton = new JFXButton("CANCEL");
        cancelButton.setCancelButton(true);
        cancelButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent closeEvent)
            {
                alert.hideWithAnimation();
            }
        });

        layout.setActions(addButton, cancelButton);
        alert.setContent(layout);

        alert.showAndWait();
    }

    @FXML
    void OnCatalog(ActionEvent event)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(BtnAdd.getScene().getWindow());
        if (file != null)
        {
            System.out.println(file.getAbsolutePath());
        }
    }

    @FXML
    void OnOpenDocument(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("E:\\pr\\图像\\wlop作品"));
        File file = fileChooser.showOpenDialog(BtnDelete.getScene().getWindow());
        if (file != null)
        {
            openFile(file);
            ImageShow.setImage(new Image("file:" + file.getAbsolutePath()));
        }

//        List<File> list = fileChooser.showOpenMultipleDialog(BtnAdd2.getScene().getWindow());
//        if(list != null)
//        {
//            list.stream().forEach((c) ->
//            {
//                openFile(c);
//            });
//        }

    }

    private void openFile(File file)
    {
        EventQueue.invokeLater(() ->
        {
            try
            {
                desktop.open(file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void OnOpenPicture(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("E:\\pr\\图像\\wlop作品"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File file = fileChooser.showOpenDialog(BtnDelete.getScene().getWindow());
        if (file != null)
        {
            openFile(file);
            ImageShow.setImage(new Image("file:" + file.getAbsolutePath()));
        }

    }

    @FXML
    void OnSave(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(BtnDelete.getScene().getWindow());
        if (file != null)
        {
            try
            {
                ImageIO.write(SwingFXUtils.fromFXImage(ImageShow.getImage(),
                        null), "png", file);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    private final class TextFieldTreeCellImpl extends TreeCell<String>
    {

        private TextField textField;
        private final ContextMenu addMenu = new ContextMenu();

        public TextFieldTreeCellImpl()
        {
            MenuItem addMenuItem = new MenuItem("Add Employee");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction((ActionEvent t) ->
            {
                TreeItem newEmployee =
                        new TreeItem<>("New Employee");
                getTreeItem().getChildren().add(newEmployee);
            });
        }

        @Override
        public void startEdit()
        {
            super.startEdit();

            if (textField == null)
            {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit()
        {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty)
        {
            super.updateItem(item, empty);

            if (empty)
            {
                setText(null);
                setGraphic(null);
            }
            else
            {
                if (isEditing())
                {
                    if (textField != null)
                    {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                }
                else
                {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (
                            !getTreeItem().isLeaf() && getTreeItem().getParent() != null
                    )
                    {
                        setContextMenu(addMenu);
                    }
                }
            }
        }

        private void createTextField()
        {
            textField = new TextField(getString());
            textField.setOnKeyReleased((KeyEvent t) ->
            {
                if (t.getCode() == KeyCode.ENTER)
                {
                    commitEdit(textField.getText());
                }
                else if (t.getCode() == KeyCode.ESCAPE)
                {
                    cancelEdit();
                }
            });
        }

        private String getString()
        {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static class Employee
    {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department)
        {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName()
        {
            return name.get();
        }

        public void setName(String fName)
        {
            name.set(fName);
        }

        public String getDepartment()
        {
            return department.get();
        }

        public void setDepartment(String fName)
        {
            department.set(fName);
        }
    }


    public  void loadHeroDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ObservedListWrapped.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            ObservedListWrapped wrapper = (ObservedListWrapped) um.unmarshal(file);
            HeroList.clear();
            HeroList.addAll(wrapper.getList());
            System.out.println(HeroList.size()+ ":load");

            // Save the file path to the registry.
            setPersonFilePath(file);

        } catch (Exception e) { // catches ANY exception
            System.out.println("wrong");
        }

    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public  void saveHeroDataToFile(File file)
    {
        try
        {
            JAXBContext context = JAXBContext
                    .newInstance(ObservedListWrapped.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            ObservedListWrapped wrapper = new ObservedListWrapped();
            wrapper.setList(HeroList);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setPersonFilePath(file);
        }
        catch (Exception e)
        { // catches ANY exception
//          Alert.title("Error")
//                    .masthead("Could not save data to file:\n" + file.getPath())
//                    .showException(e);
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.showAndWait()
//                    .filter(response -> response == ButtonType.OK)
//            ;
            System.out.println("wrong!" + e.getMessage()+file.getAbsolutePath());
        }
    }

    public File getPersonFilePath()
    {

        String filePath = preferences.get("filePath", null);
        if (filePath != null)
        {
            return new File(filePath);
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file)
    {

        if (file != null)
        {
            preferences.put("filePath", file.getPath());

            // Update the stage title.
            //BtnAdd.getScene().getWindow().setTitle("AddressApp - " + file.getName());
        }
        else
        {
            preferences.remove("filePath");

            // Update the stage title.
            // primaryStage.setTitle("AddressApp");
        }


    }

    @FXML
    void OnNew(ActionEvent event) {
        HeroList.clear();
        setPersonFilePath(null);
    }

    @FXML
    void OnOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(main.getStage());

        if (file != null) {
            loadHeroDataFromFile(file);
        }
    }

    @FXML
    void OnSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(main.getStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            saveHeroDataToFile(file);
        }
    }

    @FXML
    void OnSaveFile(ActionEvent event) {
        File personFile = getPersonFilePath();
        if (personFile != null) {
            saveHeroDataToFile(personFile);
        } else {
            OnSaveAs(new ActionEvent());
        }
    }

    @FXML
    void OnExit(ActionEvent event) {
        System.exit(0);
    }

    void ReflushTreeView()
    {
        for (Heros hero : HeroList)
        {
            TreeItem<String> empLeaf = new TreeItem<>(hero.getSkinName());

            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren())
            {
                System.out.println(depNode.getValue() + hero.getName() + "is" + depNode.getValue().contentEquals(hero.getName()));
                if (depNode.getValue().contentEquals(hero.getName()))
                {
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                TreeItem<String> depNode = new TreeItem<>(
                        hero.getName()
                        , new ImageView(depIcon)
                );
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
            HeroMap.put(hero.getSkinName(), hero.getSkinUrl());
        }
    }
}


