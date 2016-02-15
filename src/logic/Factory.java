package logic;

/**
 * Created by Диман on 15.02.2016.
 */
public class Factory {

    public Action getClient(String currentOS){
        if (currentOS.toLowerCase().startsWith("windows"))
            return new ActionWindowApp();
        else if (currentOS.toLowerCase().startsWith("linux"))
            return new ActionLinuxApp();
        return null;
    }
}
