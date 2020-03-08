package sample.Fxml;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;
import java.util.List;

public class TreeViewController
{


    private final Node rootIcon =
            new ImageView(new Image(String.valueOf(getClass().getResource("/RootTreeItem.png")),
                    16, 16, true, true));
    private final Image depIcon =
            new Image(getClass().getClassLoader().getResourceAsStream("RootTreeItem.png"),16, 16, true, true);
    List<TreeViewController.Employee> employees = Arrays.<TreeViewController.Employee>asList(
            new TreeViewController.Employee("Jacob Smith", "Accounts Department"),
            new TreeViewController.Employee("Isabella Johnson", "Accounts Department"),
            new TreeViewController.Employee("Ethan Williams", "Sales Department"),
            new TreeViewController.Employee("Emma Jones", "Sales Department"),
            new TreeViewController.Employee("Michael Brown", "Sales Department"),
            new TreeViewController.Employee("Anna Black", "Sales Department"),
            new TreeViewController.Employee("Rodger York", "Sales Department"),
            new TreeViewController.Employee("Susan Collins", "Sales Department"),
            new TreeViewController.Employee("Mike Graham", "IT Support"),
            new TreeViewController.Employee("Judy Mayer", "IT Support"),
            new TreeViewController.Employee("Gregory Smith", "IT Support"));
    TreeItem<String> rootNode;

    public TreeViewController() {

        System.out.println("TreeViewController started");
        System.out.println(getClass().getResource("") );
        System.out.println(getClass().getResourceAsStream(""));
        this.rootNode = new TreeItem<>("MyCompany Human Resources",rootIcon);
    }

    @FXML
    private javafx.scene.control.TreeView<String> TreeView;

    @FXML
    private ImageView ImageTreeView;

    @FXML
    private void initialize()
    {
        System.out.println("initialize  started");
        rootNode.setExpanded(true);
        for (TreeViewController.Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> depNode = new TreeItem<>(
                        employee.getDepartment()
                        , new ImageView(depIcon)
                );
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }


        TreeView.setRoot(rootNode);
        TreeView.setEditable(true);
        TreeView.setCellFactory((TreeView<String> p) ->
                new TreeViewController.TextFieldTreeCellImpl());
    }

    public void OnCatalog(ActionEvent actionEvent)
    {
    }

    public void OnOpenDocument(ActionEvent actionEvent)
    {
    }

    public void OnOpenPicture(ActionEvent actionEvent)
    {
    }

    public void OnSave(ActionEvent actionEvent)
    {
    }

    public void OnAdd(ActionEvent actionEvent)
    {
    }

    public void OnAdd2(ActionEvent actionEvent)
    {
    }

    private final class TextFieldTreeCellImpl extends TreeCell<String>
    {

        private TextField textField;
        private final ContextMenu addMenu = new ContextMenu();

        public TextFieldTreeCellImpl() {
            MenuItem addMenuItem = new MenuItem("Add Employee");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction((ActionEvent t) -> {
                TreeItem newEmployee =
                        new TreeItem<>("New Employee");
                getTreeItem().getChildren().add(newEmployee);
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (
                            !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                    ){
                        setContextMenu(addMenu);
                    }
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static class Employee {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(String name, String department) {
            this.name = new SimpleStringProperty(name);
            this.department = new SimpleStringProperty(department);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String fName) {
            name.set(fName);
        }

        public String getDepartment() {
            return department.get();
        }

        public void setDepartment(String fName) {
            department.set(fName);
        }
    }


}
