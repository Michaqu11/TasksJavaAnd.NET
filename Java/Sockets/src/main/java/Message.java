import java.io.Serializable;

public class Message implements Serializable {
    private int number;
    private String content;

    public Message(int number, String content){
        this.number = number;
        this.content = content;
    }
    public void setNumber(int number){
        this.number = number;
    }
    public void setContent(String content){
        this.content = content;
    }
    public int getNumber(){
        return this.number;
    }
    public String getContent(){
        return this.content;
    }
//setters and getters
}