package View.Command;

public class ExitCommand extends Command{
    public ExitCommand(String key, String descr){
        super(key, descr);
    }

    @Override
    public void execute(){
        System.exit(0);
    }
}
