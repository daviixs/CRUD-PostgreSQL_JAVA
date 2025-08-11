//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        db.connect_to_db("associacao_patinhas","postgres","davizin99");
    }
}