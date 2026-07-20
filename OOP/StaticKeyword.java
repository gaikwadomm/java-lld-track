
class BankAccount{
    public static int cnt = 0;
    public String accountHolder;

    BankAccount(String name) {
        this.accountHolder = name;
        cnt++;
    }

    public int getCount(){
        return BankAccount.cnt;
    }
}

class demoBlock{
    static {
        System.out.println("Block1");
    }
    static {
        System.out.println("Block2");
    }
}

public class StaticKeyword {
    public static void main(String[] args){
        BankAccount user1 = new BankAccount("User1");
        BankAccount user2 = new BankAccount("User2");

        System.out.println("Count : "+user1.getCount());
    }
}
