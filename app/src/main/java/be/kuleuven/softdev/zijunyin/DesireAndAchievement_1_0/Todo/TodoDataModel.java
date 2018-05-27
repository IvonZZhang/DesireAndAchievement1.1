package be.kuleuven.softdev.zijunyin.DesireAndAchievement_1_0.Todo;


public class TodoDataModel {
    private String txtTodoName;
    private String txtDeadline;
    private String txtCoinNumber;
    private Boolean isGroup;

    public TodoDataModel() {
    }

    TodoDataModel(String txtTodoName, String txtDeadline, String txtCoinNumber, Boolean isGroup) {
        this.txtTodoName = txtTodoName;
        this.txtDeadline = txtDeadline;
        this.txtCoinNumber = txtCoinNumber;
        this.isGroup = isGroup;
    }

    String getTxtTodoName() {
        return txtTodoName;
    }

    String getTxtDeadline() {
        return txtDeadline;
    }

    String getTxtCoinNumber() {
        return txtCoinNumber;
    }

    Boolean isGroup() {
        return isGroup;
    }
}
